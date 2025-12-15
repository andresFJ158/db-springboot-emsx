package com.app.emsx.repositories;

import com.app.emsx.entities.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findAllByOwnerEmail(String ownerEmail);

    Optional<Event> findByIdAndOwnerEmail(Long id, String ownerEmail);

    boolean existsByIdAndOwnerEmail(Long id, String ownerEmail);
}
