package io.futakotome.events.jpa;

import lombok.*;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@NoArgsConstructor(force = true)
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class JpaEventPublication implements Persistable<UUID> {
    @Id
    private final UUID id;
    private final Instant publicationDate;
    private final String listenerId;
    private final String serializedEvent;
    private final Class<?> eventType;

    private Instant completionDate;

    @Transient
    private boolean isNew = true;

    @Builder
    static JpaEventPublication of(Instant publicationDate, String listenerId, Object serializedEvent,
                                  Class<?> eventType) {
        return new JpaEventPublication(UUID.randomUUID(), publicationDate, listenerId, serializedEvent.toString(), eventType);
    }

    JpaEventPublication markCompleted() {
        this.completionDate = Instant.now();
        return this;
    }

    @Override
    public boolean isNew() {
        return isNew;
    }

    @PostLoad
    @PrePersist
    public void markNotNew() {
        this.isNew = false;
    }
}
