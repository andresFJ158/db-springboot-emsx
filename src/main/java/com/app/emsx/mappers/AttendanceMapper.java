package com.app.emsx.mappers;

import com.app.emsx.dtos.AttendanceDTO;
import com.app.emsx.entities.Attendance;
import com.app.emsx.entities.Participant;
import com.app.emsx.entities.Session;

public class AttendanceMapper {

    public static AttendanceDTO toDTO(Attendance a) {
        return new AttendanceDTO(
                a.getId(),
                a.getSession() != null ? a.getSession().getId() : null,
                a.getParticipant() != null ? a.getParticipant().getId() : null,
                a.getAttendedAt(),
                a.getSession() != null ? a.getSession().getTitle() : null,
                a.getParticipant() != null ? a.getParticipant().getNombre() : null
        );
    }

    public static Attendance toEntity(AttendanceDTO dto, Session session, Participant participant) {
        Attendance a = new Attendance();
        a.setSession(session);
        a.setParticipant(participant);
        a.setAttendedAt(dto.getAttendedAt());
        return a;
    }
}
