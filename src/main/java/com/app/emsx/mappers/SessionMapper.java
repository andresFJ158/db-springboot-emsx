package com.app.emsx.mappers;

import com.app.emsx.dtos.SessionDTO;
import com.app.emsx.entities.Event;
import com.app.emsx.entities.Session;
import com.app.emsx.entities.Speaker;

public class SessionMapper {

    public static SessionDTO toDTO(Session s) {
        return new SessionDTO(
                s.getId(),
                s.getTitle(),
                s.getDescription(),
                s.getStartTime(),
                s.getEndTime(),
                s.getEvent() != null ? s.getEvent().getId() : null,
                s.getSpeaker() != null ? s.getSpeaker().getId() : null,
                s.getEvent() != null ? s.getEvent().getName() : null,
                s.getSpeaker() != null ? s.getSpeaker().getFullName() : null
        );
    }

    public static Session toEntity(SessionDTO dto, Event event, Speaker speaker) {
        Session s = new Session();
        s.setTitle(dto.getTitle());
        s.setDescription(dto.getDescription());
        s.setStartTime(dto.getStartTime());
        s.setEndTime(dto.getEndTime());
        s.setEvent(event);
        s.setSpeaker(speaker);
        return s;
    }

    public static void updateEntity(Session s, SessionDTO dto, Event event, Speaker speaker) {
        s.setTitle(dto.getTitle());
        s.setDescription(dto.getDescription());
        s.setStartTime(dto.getStartTime());
        s.setEndTime(dto.getEndTime());
        s.setEvent(event);
        s.setSpeaker(speaker);
    }
}
