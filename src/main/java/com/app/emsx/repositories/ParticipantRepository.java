package com.app.emsx.repositories;

import com.app.emsx.entities.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    boolean existsByEmail(String email);
    
    Optional<Participant> findByEmail(String email);

    List<Participant> findAllByOwnerEmail(String ownerEmail);

    Optional<Participant> findByIdAndOwnerEmail(Long id, String ownerEmail);

    boolean existsByIdAndOwnerEmail(Long id, String ownerEmail);
}
