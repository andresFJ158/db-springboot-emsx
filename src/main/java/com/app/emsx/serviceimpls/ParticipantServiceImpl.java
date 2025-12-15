package com.app.emsx.serviceimpls;

import com.app.emsx.dtos.ParticipantDTO;
import com.app.emsx.entities.Participant;
import com.app.emsx.mappers.ParticipantMapper;
import com.app.emsx.repositories.ParticipantRepository;
import com.app.emsx.services.ParticipantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParticipantServiceImpl implements ParticipantService {

    @Autowired
    private ParticipantRepository participantRepository;
    
    @Autowired
    private com.app.emsx.services.EmailValidationService emailValidationService;

    private String getCurrentUserEmail() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth != null ? auth.getName() : null;
        if (email == null || email.equals("anonymousUser")) {
            return "anonymous@emsx.com";
        }
        return email;
    }

    @Override
    public ParticipantDTO createParticipant(ParticipantDTO dto) {
        // ✅ Validar que el email no esté registrado en NINGUNA entidad
        emailValidationService.validateEmailUnique(dto.getEmail());
        
        Participant p = ParticipantMapper.toEntity(dto);
        p.setOwnerEmail(getCurrentUserEmail());
        Participant saved = participantRepository.save(p);
        return ParticipantMapper.toDTO(saved);
    }

    @Override
    public ParticipantDTO updateParticipant(Long id, ParticipantDTO dto) {
        String ownerEmail = getCurrentUserEmail();
        Participant p = participantRepository.findByIdAndOwnerEmail(id, ownerEmail)
                .orElseThrow(() -> new RuntimeException("Participant no encontrado o no pertenece al usuario actual con id: " + id));

        // ✅ Validar que el email no esté usado por otra entidad
        emailValidationService.validateEmailUniqueForUpdate(dto.getEmail(), id, "PARTICIPANT");

        ParticipantMapper.updateEntity(p, dto);
        Participant updated = participantRepository.save(p);
        return ParticipantMapper.toDTO(updated);
    }

    @Override
    public void deleteParticipant(Long id) {
        String ownerEmail = getCurrentUserEmail();
        if (!participantRepository.existsByIdAndOwnerEmail(id, ownerEmail)) {
            throw new RuntimeException("Participant no encontrado o no pertenece al usuario actual con id: " + id);
        }
        participantRepository.deleteById(id);
    }

    @Override
    public ParticipantDTO findParticipantById(Long id) {
        String ownerEmail = getCurrentUserEmail();
        Participant p = participantRepository.findByIdAndOwnerEmail(id, ownerEmail)
                .orElseThrow(() -> new RuntimeException("Participant no encontrado o no pertenece al usuario actual con id: " + id));
        return ParticipantMapper.toDTO(p);
    }

    @Override
    public List<ParticipantDTO> findAllParticipants() {
        String ownerEmail = getCurrentUserEmail();
        return participantRepository.findAllByOwnerEmail(ownerEmail)
                .stream()
                .map(ParticipantMapper::toDTO)
                .collect(Collectors.toList());
    }
}
