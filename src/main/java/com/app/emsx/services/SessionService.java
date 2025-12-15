package com.app.emsx.services;

import com.app.emsx.dtos.SessionDTO;

import java.util.List;

public interface SessionService {
    SessionDTO createSession(SessionDTO dto);
    SessionDTO updateSession(Long id, SessionDTO dto);
    void deleteSession(Long id);
    SessionDTO findSessionById(Long id);
    List<SessionDTO> findAllSessions();

    List<SessionDTO> findByEvent(Long eventId);
    List<SessionDTO> findBySpeaker(Long speakerId);
}
