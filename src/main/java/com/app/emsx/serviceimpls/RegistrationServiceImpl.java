package com.app.emsx.serviceimpls;

import com.app.emsx.dtos.RegistrationDTO;
import com.app.emsx.entities.Event;
import com.app.emsx.entities.Participant;
import com.app.emsx.entities.Registration;
import com.app.emsx.mappers.RegistrationMapper;
import com.app.emsx.repositories.EventRepository;
import com.app.emsx.repositories.ParticipantRepository;
import com.app.emsx.repositories.RegistrationRepository;
import com.app.emsx.services.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegistrationRepository registrationRepository;

    @Autowired
    private ParticipantRepository participantRepository;

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
    public RegistrationDTO create(RegistrationDTO dto) {
        Participant participant = participantRepository.findById(dto.getParticipantId())
                .orElseThrow(() -> new RuntimeException("Participant no encontrado con id: " + dto.getParticipantId()));
        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("Event no encontrado con id: " + dto.getEventId()));
        Registration toSave = RegistrationMapper.toEntity(dto, participant, event);
        toSave.setOwnerEmail(getCurrentUserEmail());
        Registration saved = registrationRepository.save(toSave);
        return RegistrationMapper.toDTO(saved);
    }

    @Override
    public RegistrationDTO update(Long id, RegistrationDTO dto) {
        String ownerEmail = getCurrentUserEmail();
        Registration registration = registrationRepository.findByIdAndOwnerEmail(id, ownerEmail)
                .orElseThrow(() -> new RuntimeException("Registration no encontrado o no pertenece al usuario actual con id: " + id));
        Participant participant = participantRepository.findById(dto.getParticipantId())
                .orElseThrow(() -> new RuntimeException("Participant no encontrado con id: " + dto.getParticipantId()));
        Event event = eventRepository.findById(dto.getEventId())
                .orElseThrow(() -> new RuntimeException("Event no encontrado con id: " + dto.getEventId()));
        registration.setParticipant(participant);
        registration.setEvent(event);
        registration.setRegisteredAt(dto.getRegisteredAt());
        Registration updated = registrationRepository.save(registration);
        return RegistrationMapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        String ownerEmail = getCurrentUserEmail();
        if (!registrationRepository.existsByIdAndOwnerEmail(id, ownerEmail)) {
            throw new RuntimeException("Registration no encontrado o no pertenece al usuario actual con id: " + id);
        }
        registrationRepository.deleteById(id);
    }

    @Override
    public RegistrationDTO findById(Long id) {
        String ownerEmail = getCurrentUserEmail();
        Registration registration = registrationRepository.findByIdAndOwnerEmail(id, ownerEmail)
                .orElseThrow(() -> new RuntimeException("Registration no encontrado o no pertenece al usuario actual con id: " + id));
        return RegistrationMapper.toDTO(registration);
    }

    @Override
    public List<RegistrationDTO> findAll() {
        String ownerEmail = getCurrentUserEmail();
        return registrationRepository.findAllByOwnerEmail(ownerEmail)
                .stream()
                .map(RegistrationMapper::toDTO)
                .collect(Collectors.toList());
    }
}
