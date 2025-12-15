package com.app.emsx.serviceimpls;

import com.app.emsx.dtos.EventDTO;
import com.app.emsx.entities.Event;
import com.app.emsx.mappers.EventMapper;
import com.app.emsx.repositories.EventRepository;
import com.app.emsx.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    private String getCurrentUserEmail() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth != null ? auth.getName() : null;
        if (email == null || email.equals("anonymousUser")) {
            return "anonymous@emsx.com";
        }
        return email;
    }

    @Override
    public EventDTO createEvent(EventDTO dto) {
        Event event = EventMapper.toEntity(dto);
        event.setOwnerEmail(getCurrentUserEmail());
        Event saved = eventRepository.save(event);
        return EventMapper.toDTO(saved);
    }

    @Override
    public EventDTO updateEvent(Long id, EventDTO dto) {
        String ownerEmail = getCurrentUserEmail();
        Event event = eventRepository.findByIdAndOwnerEmail(id, ownerEmail)
                .orElseThrow(() -> new RuntimeException("Event no encontrado o no pertenece al usuario actual con id: " + id));
        EventMapper.updateEntity(event, dto);
        Event updated = eventRepository.save(event);
        return EventMapper.toDTO(updated);
    }

    @Override
    public void deleteEvent(Long id) {
        String ownerEmail = getCurrentUserEmail();
        if (!eventRepository.existsByIdAndOwnerEmail(id, ownerEmail)) {
            throw new RuntimeException("Event no encontrado o no pertenece al usuario actual con id: " + id);
        }
        eventRepository.deleteById(id);
    }

    @Override
    public EventDTO findEventById(Long id) {
        String ownerEmail = getCurrentUserEmail();
        Event event = eventRepository.findByIdAndOwnerEmail(id, ownerEmail)
                .orElseThrow(() -> new RuntimeException("Event no encontrado o no pertenece al usuario actual con id: " + id));
        return EventMapper.toDTO(event);
    }

    @Override
    public List<EventDTO> findAllEvents() {
        String ownerEmail = getCurrentUserEmail();
        return eventRepository.findAllByOwnerEmail(ownerEmail)
                .stream()
                .map(EventMapper::toDTO)
                .collect(Collectors.toList());
    }
}
