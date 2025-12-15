package com.app.emsx.services;

import com.app.emsx.dtos.ParticipantDTO;

import java.util.List;

public interface ParticipantService {
    ParticipantDTO createParticipant(ParticipantDTO dto);
    ParticipantDTO updateParticipant(Long id, ParticipantDTO dto);
    void deleteParticipant(Long id);
    ParticipantDTO findParticipantById(Long id);
    List<ParticipantDTO> findAllParticipants();
}
