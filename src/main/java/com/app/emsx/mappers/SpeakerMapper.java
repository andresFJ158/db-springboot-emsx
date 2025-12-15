package com.app.emsx.mappers;

import com.app.emsx.dtos.SpeakerDTO;
import com.app.emsx.entities.Speaker;

public class SpeakerMapper {

    public static SpeakerDTO mapSpeakerToSpeakerDTO(Speaker speaker) {
        SpeakerDTO speakerDTO = new SpeakerDTO(
                speaker.getId(),
                speaker.getFullName(),
                speaker.getBio(),
                speaker.getEmail() != null ? speaker.getEmail().toLowerCase() : null,
                speaker.getCompany(),
                speaker.getDateNac()
        );
        return speakerDTO;
    }

    public static Speaker mapSpeakerDTOToSpeaker(SpeakerDTO speakerDTO) {
        Speaker speaker = new Speaker();
        speaker.setFullName(speakerDTO.getNombre());
        speaker.setBio(speakerDTO.getBio());
        speaker.setEmail(speakerDTO.getEmail() != null ? speakerDTO.getEmail().toLowerCase() : null);
        speaker.setCompany(speakerDTO.getCompany());
        speaker.setDateNac(speakerDTO.getDate_nac());
        return speaker;
    }
}
