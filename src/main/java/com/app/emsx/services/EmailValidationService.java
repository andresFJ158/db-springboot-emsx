package com.app.emsx.services;

import com.app.emsx.exceptions.EmailAlreadyExistsException;
import com.app.emsx.repositories.ParticipantRepository;
import com.app.emsx.repositories.SpeakerRepository;
import com.app.emsx.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * EmailValidationService
 * Valida que un email sea único en todo el sistema
 * (no puede existir en User, Speaker ni Participant)
 */
@Service
public class EmailValidationService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SpeakerRepository speakerRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    /**
     * Verifica si un email ya está registrado en cualquier entidad del sistema
     * @param email Email a verificar
     * @return true si el email ya existe, false si está disponible
     */
    public boolean emailExists(String email) {
        if (email == null) {
            return false;
        }
        
        String normalizedEmail = email.toLowerCase().trim();
        
        // Verificar en todas las entidades
        return userRepository.findByEmail(normalizedEmail).isPresent()
            || speakerRepository.existsByEmail(normalizedEmail)
            || participantRepository.existsByEmail(normalizedEmail);
    }

    /**
     * Verifica si un email está disponible para usar
     * @param email Email a verificar
     * @return true si está disponible, false si ya existe
     */
    public boolean isEmailAvailable(String email) {
        return !emailExists(email);
    }

    /**
     * Valida y lanza excepción si el email ya existe
     * @param email Email a validar
     * @throws EmailAlreadyExistsException si el email ya existe
     */
    public void validateEmailUnique(String email) {
        if (emailExists(email)) {
            throw new EmailAlreadyExistsException("El correo " + email + " ya está registrado en el sistema");
        }
    }

    /**
     * Valida email para actualización (permite que el mismo registro mantenga su email)
     * @param email Email a validar
     * @param currentEntityId ID de la entidad actual
     * @param entityType Tipo de entidad ("USER", "SPEAKER", "PARTICIPANT")
     * @throws EmailAlreadyExistsException si el email ya existe en otra entidad
     */
    public void validateEmailUniqueForUpdate(String email, Long currentEntityId, String entityType) {
        if (email == null) {
            return;
        }
        
        String normalizedEmail = email.toLowerCase().trim();
        
        // Verificar en User (solo si no es la entidad actual)
        if (!"USER".equals(entityType)) {
            if (userRepository.findByEmail(normalizedEmail).isPresent()) {
                throw new EmailAlreadyExistsException("El correo " + email + " ya está registrado en el sistema");
            }
        }
        
        // Verificar en Speaker (solo si no es la entidad actual)
        if (!"SPEAKER".equals(entityType)) {
            if (speakerRepository.existsByEmail(normalizedEmail)) {
                throw new EmailAlreadyExistsException("El correo " + email + " ya está registrado en el sistema");
            }
        } else {
            // Si es un speaker, verificar que no sea usado por OTRO speaker
            speakerRepository.findByEmail(normalizedEmail).ifPresent(speaker -> {
                if (!speaker.getId().equals(currentEntityId)) {
                    throw new EmailAlreadyExistsException("El correo " + email + " ya está registrado en el sistema");
                }
            });
        }
        
        // Verificar en Participant (solo si no es la entidad actual)
        if (!"PARTICIPANT".equals(entityType)) {
            if (participantRepository.existsByEmail(normalizedEmail)) {
                throw new EmailAlreadyExistsException("El correo " + email + " ya está registrado en el sistema");
            }
        } else {
            // Si es un participant, verificar que no sea usado por OTRO participant
            participantRepository.findByEmail(normalizedEmail).ifPresent(participant -> {
                if (!participant.getId().equals(currentEntityId)) {
                    throw new EmailAlreadyExistsException("El correo " + email + " ya está registrado en el sistema");
                }
            });
        }
    }
}
