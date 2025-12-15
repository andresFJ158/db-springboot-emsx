package com.app.emsx.repositories;

import com.app.emsx.entities.Speaker;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SpeakerRepository extends JpaRepository<Speaker,Long> {

    List<Speaker> findAllByOwnerEmail(String ownerEmail);

    Optional<Speaker> findByIdAndOwnerEmail(Long id, String ownerEmail);

    boolean existsByIdAndOwnerEmail(Long id, String ownerEmail);
    
    boolean existsByEmail(String email);
    
    Optional<Speaker> findByEmail(String email);
}
