package com.app.emsx.serviceimpls;
import com.app.emsx.dtos.SpeakerDTO;
import com.app.emsx.entities.Speaker;
import com.app.emsx.mappers.SpeakerMapper;
import com.app.emsx.repositories.SpeakerRepository;
import com.app.emsx.services.SpeakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpeakerServiceImpl implements SpeakerService {

    @Autowired
    private SpeakerRepository speakerRepository;
    
    @Autowired
    private com.app.emsx.services.EmailValidationService emailValidationService;

    private String getCurrentUserEmail() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth != null ? auth.getName() : null;
        System.out.println("👤 Usuario actual: " + email);
        System.out.println("🔐 Autenticación: " + auth);
        
        // En desarrollo, permitir usuario anónimo
        if (email == null || email.equals("anonymousUser")) {
            System.out.println("⚠️ Usuario no autenticado, usando email por defecto");
            return "anonymous@emsx.com"; // Email por defecto para desarrollo
        }
        return email;
    }

    @Override
    public SpeakerDTO createSpeaker(SpeakerDTO speakerDTO) {
        System.out.println("📥 Recibiendo speaker: " + speakerDTO.getNombre() + " - " + speakerDTO.getEmail());
        
        // ✅ Validar que el email no esté registrado en NINGUNA entidad
        emailValidationService.validateEmailUnique(speakerDTO.getEmail());
        
        try {
            Speaker speaker = SpeakerMapper.mapSpeakerDTOToSpeaker(speakerDTO);
            speaker.setOwnerEmail(getCurrentUserEmail());
            System.out.println("✅ Mapeado correctamente, guardando...");
            Speaker saved = speakerRepository.save(speaker);
            System.out.println("✅ Speaker guardado con ID: " + saved.getId());
            return SpeakerMapper.mapSpeakerToSpeakerDTO(saved);
        } catch (Exception e) {
            System.err.println("❌ Error al crear speaker: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al crear speaker: " + e.getMessage(), e);
        }
    }

    @Override
    public SpeakerDTO updateSpeaker(Long id, SpeakerDTO speakerDTO) {
        String ownerEmail = getCurrentUserEmail();
        Speaker speaker = speakerRepository.findByIdAndOwnerEmail(id, ownerEmail)
                .orElseThrow(() -> new RuntimeException("Speaker no encontrado o no pertenece al usuario actual con id: " + id));
        
        // ✅ Validar que el email no esté usado por otra entidad
        emailValidationService.validateEmailUniqueForUpdate(speakerDTO.getEmail(), id, "SPEAKER");
        
        speaker.setFullName(speakerDTO.getNombre());
        speaker.setBio(speakerDTO.getBio());
        speaker.setEmail(speakerDTO.getEmail());
        speaker.setCompany(speakerDTO.getCompany());
        speaker.setDateNac(speakerDTO.getDate_nac());

        Speaker updated = speakerRepository.save(speaker);
        return SpeakerMapper.mapSpeakerToSpeakerDTO(updated);
    }

    @Override
    public void deleteSpeaker(Long id) {
        String ownerEmail = getCurrentUserEmail();
        if(!speakerRepository.existsByIdAndOwnerEmail(id, ownerEmail)) {
            throw new RuntimeException("Speaker no encontrado o no pertenece al usuario actual con id: " + id);
        }
        speakerRepository.deleteById(id);
    }

    @Override
    public List<SpeakerDTO> findAllSpeakers() {
        String ownerEmail = getCurrentUserEmail();
        System.out.println("🔍 Buscando speakers para: " + ownerEmail);
        List<Speaker> speakers = speakerRepository.findAllByOwnerEmail(ownerEmail);
        System.out.println("📊 Speakers encontrados: " + speakers.size());
        return speakers.stream()
                .map(SpeakerMapper::mapSpeakerToSpeakerDTO)
                .collect(Collectors.toList());
    }

    @Override
    public SpeakerDTO findSpeakerById(Long id) {
        String ownerEmail = getCurrentUserEmail();
        Speaker speaker = speakerRepository.findByIdAndOwnerEmail(id, ownerEmail)
                .orElseThrow(() -> new RuntimeException("Speaker no encontrado o no pertenece al usuario actual con id: " + id));
        return SpeakerMapper.mapSpeakerToSpeakerDTO(speaker);
    }
}
