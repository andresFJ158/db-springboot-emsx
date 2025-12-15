package com.app.emsx.mappers;

import com.app.emsx.dtos.ParticipantDTO;
import com.app.emsx.entities.Participant;

public class ParticipantMapper {

    public static ParticipantDTO toDTO(Participant p) {
        return new ParticipantDTO(
                p.getId(),
                p.getNombre(),
                p.getEmail().toLowerCase(),
                p.getCompany(),
                p.getPhone(),
                p.getDateNac()
        );
    }

    public static Participant toEntity(ParticipantDTO dto) {
        Participant p = new Participant();
        p.setNombre(dto.getNombre());
        p.setEmail(dto.getEmail() != null ? dto.getEmail().trim().toLowerCase() : null);
        p.setCompany(dto.getCompany());
        p.setPhone(dto.getPhone());
        p.setDateNac(dto.getDateNac());
        return p;
    }

    public static void updateEntity(Participant p, ParticipantDTO dto) {
        p.setNombre(dto.getNombre());
        p.setEmail(dto.getEmail() != null ? dto.getEmail().trim().toLowerCase() : null);
        p.setCompany(dto.getCompany());
        p.setPhone(dto.getPhone());
        p.setDateNac(dto.getDateNac());
    }
}
