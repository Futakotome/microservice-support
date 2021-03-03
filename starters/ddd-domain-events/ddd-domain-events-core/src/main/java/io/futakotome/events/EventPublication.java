package io.futakotome.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.util.Assert;

import java.time.Instant;

public interface EventPublication extends Comparable<EventPublication> {
    Object getEvent();

    default ApplicationEvent getApplicationEvent() {
        Object event = getEvent();
        return PayloadApplicationEvent.class.isInstance(event)
                ? PayloadApplicationEvent.class.cast(event)
                : new PayloadApplicationEvent<>(this, event);
    }

    Instant getPublicationTime();

    PublicationTargetIdentifier getTargetIdentifier();

    default boolean isIdentifierBy(PublicationTargetIdentifier identifier) {
        Assert.notNull(identifier, "Identifier must not be null");
        return this.getTargetIdentifier().equals(identifier);
    }

    @Override
    default int compareTo(EventPublication o) {
        return this.getPublicationTime().compareTo(o.getPublicationTime());
    }
}
