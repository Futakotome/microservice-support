package io.futakotome.events.support;

import io.futakotome.events.CompletableEventPublication;
import io.futakotome.events.EventPublication;
import io.futakotome.events.EventPublicationRegistry;
import io.futakotome.events.PublicationTargetIdentifier;
import lombok.Value;
import org.springframework.context.ApplicationListener;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * for test
 */
public class MapEventPublicationRegistry implements EventPublicationRegistry {

    private final Map<Key, CompletableEventPublication> events = new HashMap<>();

    @Override
    public void store(Object event, Collection<? extends ApplicationListener<?>> listeners) {
        listeners.forEach(applicationListener -> {
            PublicationTargetIdentifier id = PublicationTargetIdentifier.forListener(applicationListener);
            events.computeIfAbsent(Key.of(event, id), function ->
                    CompletableEventPublication.of(event, id));
        });
    }

    @Override
    public void markCompleted(Object event, PublicationTargetIdentifier id) {
        events.computeIfPresent(Key.of(event, id), (key, value) -> value.markCompleted());
    }

    @Override
    public Iterable<EventPublication> findIncompletePublications() {
        return events.values().stream()
                .filter(completableEventPublication -> !completableEventPublication.isPublicationCompleted())
                .collect(Collectors.toList());
    }

    @Value(staticConstructor = "of")
    private static class Key {
        Object event;
        PublicationTargetIdentifier identifier;
    }
}
