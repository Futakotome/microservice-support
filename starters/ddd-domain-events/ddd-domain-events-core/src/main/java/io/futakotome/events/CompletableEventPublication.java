package io.futakotome.events;

import java.time.Instant;
import java.util.Optional;

public interface CompletableEventPublication extends EventPublication {
    Optional<Instant> getCompletionDate();

    default boolean isPublicationCompleted() {
        return getCompletionDate().isPresent();
    }

    CompletableEventPublication markCompleted();

    static CompletableEventPublication of(Object event, PublicationTargetIdentifier id) {
        return DefaultEventPublication.of(event, id);
    }
}
