package io.futakotome.events.jpa;

import io.futakotome.events.*;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationListener;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class JpaEventPublicationRegistry implements EventPublicationRegistry, DisposableBean {

    private final JpaEventPublicationRepository events;
    private final EventSerializer serializer;

    @Override
    public void store(Object event, Collection<? extends ApplicationListener<?>> listeners) {
        listeners.stream()
                .map(PublicationTargetIdentifier::forListener)
                .map(publicationTargetIdentifier -> CompletableEventPublication.of(event, publicationTargetIdentifier))
                .map(this::map)
                .forEach(events::save);
    }

    private JpaEventPublication map(EventPublication publication) {
        JpaEventPublication result = JpaEventPublication.builder()
                .eventType(publication.getEvent().getClass())
                .publicationDate(publication.getPublicationTime())
                .listenerId(publication.getTargetIdentifier().toString())
                .serializedEvent(serializer.serializer(publication.getEvent()).toString())
                .build();

        log.debug("Registering publication of {} with id {} for {}.",
                result.getEventType(), result.getId(), result.getListenerId());

        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void markCompleted(Object event, PublicationTargetIdentifier listener) {
        Assert.notNull(event, "Domain event must not be null!");
        Assert.notNull(listener, "Listener identifier must not be null!");

        events.findBySerializedEventAndListenerId(serializer.serializer(event), listener.toString())
                .map(JpaEventPublicationRegistry::logCompleted)
                .ifPresent(publication -> events.saveAndFlush(publication.markCompleted()));
    }

    @Override
    public Iterable<EventPublication> findIncompletePublications() {
        return events.findByCompletionDateIsNull()
                .stream()
                .map(jpaEventPublication -> JpaEventPublicationAdapter.of(jpaEventPublication, serializer))
                .collect(Collectors.toList());
    }

    @Override
    public void destroy() throws Exception {
        List<JpaEventPublication> outstandingPublications = events.findByCompletionDateIsNull();
        if (outstandingPublications.isEmpty()) {
            log.info("No publication outstanding!");
            return;
        }
        log.info("Shutting down with the following publications left unfinished:");
        outstandingPublications
                .forEach(it -> log.info("\t{} - {} - {}", it.getId(), it.getEventType().getName(), it.getListenerId()));
    }

    private static JpaEventPublication logCompleted(JpaEventPublication publication) {
        log.debug("Marking publication of event {} with id {} to listener {} completed.",
                publication.getEventType(), publication.getId(), publication.getListenerId());
        return publication;
    }

    @EqualsAndHashCode
    @RequiredArgsConstructor(staticName = "of")
    static class JpaEventPublicationAdapter implements EventPublication {

        private final JpaEventPublication publication;
        private final EventSerializer serializer;

        @Override
        public Object getEvent() {
            return serializer.deserializer(publication.getSerializedEvent(), publication.getEventType());
        }

        @Override
        public Instant getPublicationTime() {
            return publication.getPublicationDate();
        }

        @Override
        public PublicationTargetIdentifier getTargetIdentifier() {
            return PublicationTargetIdentifier.of(publication.getListenerId());
        }
    }
}
