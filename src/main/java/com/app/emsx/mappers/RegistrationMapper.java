package com.app.emsx.mappers;

import com.app.emsx.dtos.RegistrationDTO;
import com.app.emsx.entities.Event;
import com.app.emsx.entities.Participant;
import com.app.emsx.entities.Registration;

public class RegistrationMapper {

    public static RegistrationDTO toDTO(Registration r) {
        return new RegistrationDTO(
                r.getId(),
                r.getParticipant() != null ? r.getParticipant().getId() : null,
                r.getEvent() != null ? r.getEvent().getId() : null,
                r.getRegisteredAt(),
                r.getParticipant() != null ? r.getParticipant().getNombre() : null,
                r.getEvent() != null ? r.getEvent().getName() : null
        );
    }

    public static Registration toEntity(RegistrationDTO dto, Participant participant, Event event) {
        Registration r = new Registration();
        r.setParticipant(participant);
        r.setEvent(event);
        r.setRegisteredAt(dto.getRegisteredAt());
        return r;
    }
}
