package com.app.emsx.services;

import com.app.emsx.dtos.EventDTO;
import java.util.List;

public interface EventService {
    EventDTO createEvent(EventDTO dto);
    EventDTO updateEvent(Long id, EventDTO dto);
    void deleteEvent(Long id);
    EventDTO findEventById(Long id);
    List<EventDTO> findAllEvents();
}
