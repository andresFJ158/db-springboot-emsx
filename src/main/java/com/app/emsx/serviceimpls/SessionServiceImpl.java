package com.app.emsx.serviceimpls;

import com.app.emsx.dtos.SessionDTO;
import com.app.emsx.entities.Event;
import com.app.emsx.entities.Session;
import com.app.emsx.entities.Speaker;
import com.app.emsx.mappers.SessionMapper;
import com.app.emsx.repositories.EventRepository;
import com.app.emsx.repositories.SessionRepository;
import com.app.emsx.repositories.SpeakerRepository;
import com.app.emsx.services.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SessionServiceImpl implements SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private SpeakerRepository speakerRepository;

    private String getCurrentUserEmail() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth != null ? auth.getName() : null;
        if (email == null || email.equals("anonymousUser")) {
            return "anonymous@emsx.com";
        }
        return email;
    }

    @Override
    public SessionDTO createSession(SessionDTO dto) {
        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("Event no encontrado con id: " + dto.getEventId()));
        Speaker speaker = speakerRepository.findById(dto.getSpeakerId())
                .orElseThrow(() -> new RuntimeException("Speaker no encontrado con id: " + dto.getSpeakerId()));

        Session session = SessionMapper.toEntity(dto, event, speaker);
        session.setOwnerEmail(getCurrentUserEmail());
        Session saved = sessionRepository.save(session);
        return SessionMapper.toDTO(saved);
    }

    @Override
    public SessionDTO updateSession(Long id, SessionDTO dto) {
        String ownerEmail = getCurrentUserEmail();
        Session s = sessionRepository.findByIdAndOwnerEmail(id, ownerEmail)
                .orElseThrow(() -> new RuntimeException("Session no encontrada o no pertenece al usuario actual con id: " + id));

        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("Event no encontrado con id: " + dto.getEventId()));
        Speaker speaker = speakerRepository.findById(dto.getSpeakerId())
                .orElseThrow(() -> new RuntimeException("Speaker no encontrado con id: " + dto.getSpeakerId()));

        SessionMapper.updateEntity(s, dto, event, speaker);
        Session updated = sessionRepository.save(s);
        return SessionMapper.toDTO(updated);
    }

    @Override
    public void deleteSession(Long id) {
        String ownerEmail = getCurrentUserEmail();
        if (!sessionRepository.existsByIdAndOwnerEmail(id, ownerEmail)) {
            throw new RuntimeException("Session no encontrada o no pertenece al usuario actual con id: " + id);
        }
        sessionRepository.deleteById(id);
    }

    @Override
    public SessionDTO findSessionById(Long id) {
        String ownerEmail = getCurrentUserEmail();
        Session s = sessionRepository.findByIdAndOwnerEmail(id, ownerEmail)
                .orElseThrow(() -> new RuntimeException("Session no encontrada o no pertenece al usuario actual con id: " + id));
        return SessionMapper.toDTO(s);
    }

    @Override
    public List<SessionDTO> findAllSessions() {
        String ownerEmail = getCurrentUserEmail();
        return sessionRepository.findAllByOwnerEmail(ownerEmail)
                .stream()
                .map(SessionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SessionDTO> findByEvent(Long eventId) {
        String ownerEmail = getCurrentUserEmail();
        return sessionRepository.findByEvent_IdAndOwnerEmail(eventId, ownerEmail)
                .stream()
                .map(SessionMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<SessionDTO> findBySpeaker(Long speakerId) {
        String ownerEmail = getCurrentUserEmail();
        return sessionRepository.findBySpeaker_IdAndOwnerEmail(speakerId, ownerEmail)
                .stream()
                .map(SessionMapper::toDTO)
                .collect(Collectors.toList());
    }
}
