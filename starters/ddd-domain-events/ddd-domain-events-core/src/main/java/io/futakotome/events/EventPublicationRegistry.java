package io.futakotome.events;

import org.springframework.context.ApplicationListener;
import org.springframework.util.Assert;

import java.util.Collection;

public interface EventPublicationRegistry {

    void store(Object event, Collection<? extends ApplicationListener<?>> listeners);

    void markCompleted(Object event, PublicationTargetIdentifier listener);

    default void markCompleted(EventPublication publication) {

        Assert.notNull(publication, "Publication must not be null");

        markCompleted(publication.getEvent(), publication.getTargetIdentifier());
    }

    Iterable<EventPublication> findIncompletePublications();
}
