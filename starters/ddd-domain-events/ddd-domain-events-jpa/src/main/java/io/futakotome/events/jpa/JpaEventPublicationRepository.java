package io.futakotome.events.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JpaEventPublicationRepository extends JpaRepository<JpaEventPublication, UUID> {
    List<JpaEventPublication> findByCompletionDateIsNull();

    @Query("SELECT p from JpaEventPublication p where p.serializedEvent=?1 and p.listenerId=?2")
    Optional<JpaEventPublication> findBySerializedEventAndListenerId(Object event, String listenerId);
}
