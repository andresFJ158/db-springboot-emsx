package com.app.emsx.repositories;

import com.app.emsx.entities.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Long> {

    List<Session> findByEvent_IdAndOwnerEmail(Long eventId, String ownerEmail);

    List<Session> findBySpeaker_IdAndOwnerEmail(Long speakerId, String ownerEmail);

    List<Session> findAllByOwnerEmail(String ownerEmail);

    Optional<Session> findByIdAndOwnerEmail(Long id, String ownerEmail);

    boolean existsByIdAndOwnerEmail(Long id, String ownerEmail);
}
