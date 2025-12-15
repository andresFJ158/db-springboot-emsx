package com.app.emsx.services;

import com.app.emsx.dtos.SpeakerDTO;

import java.util.List;

public interface SpeakerService {
    SpeakerDTO createSpeaker(SpeakerDTO speakerDTO);
    SpeakerDTO updateSpeaker(Long id, SpeakerDTO speakerDTO);
    void deleteSpeaker(Long id);
    List<SpeakerDTO> findAllSpeakers();
    SpeakerDTO findSpeakerById(Long id);
}
