package io.futakotome.events;

import lombok.*;

import java.time.Instant;
import java.util.Optional;

@RequiredArgsConstructor(staticName = "of")
@EqualsAndHashCode
@ToString
public class DefaultEventPublication implements CompletableEventPublication {
    private final @NonNull Object event;
    private final @NonNull PublicationTargetIdentifier targetIdentifier;
    private final Instant publicationDate = Instant.now();

    private Optional<Instant> completedDate = Optional.empty();


    @Override
    public Optional<Instant> getCompletionDate() {
        return this.completedDate;
    }

    @Override
    public CompletableEventPublication markCompleted() {
        this.completedDate = Optional.of(Instant.now());
        return this;
    }

    @Override
    public Object getEvent() {
        return this.event;
    }

    @Override
    public Instant getPublicationTime() {
        return this.publicationDate;
    }

    @Override
    public PublicationTargetIdentifier getTargetIdentifier() {
        return this.targetIdentifier;
    }
}
