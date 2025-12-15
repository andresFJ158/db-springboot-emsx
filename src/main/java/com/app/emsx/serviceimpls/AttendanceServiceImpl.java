package com.app.emsx.serviceimpls;

import com.app.emsx.dtos.AttendanceDTO;
import com.app.emsx.entities.Attendance;
import com.app.emsx.entities.Participant;
import com.app.emsx.entities.Session;
import com.app.emsx.mappers.AttendanceMapper;
import com.app.emsx.repositories.AttendanceRepository;
import com.app.emsx.repositories.ParticipantRepository;
import com.app.emsx.repositories.SessionRepository;
import com.app.emsx.services.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceServiceImpl implements AttendanceService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private ParticipantRepository participantRepository;

    private String getCurrentUserEmail() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        String email = auth != null ? auth.getName() : null;
        if (email == null || email.equals("anonymousUser")) {
            return "anonymous@emsx.com";
        }
        return email;
    }

    @Override
    public AttendanceDTO create(AttendanceDTO dto) {
        Session session = sessionRepository.findById(dto.getSessionId())
                .orElseThrow(() -> new RuntimeException("Session no encontrado con id: " + dto.getSessionId()));
        Participant participant = participantRepository.findById(dto.getParticipantId())
                .orElseThrow(() -> new RuntimeException("Participant no encontrado con id: " + dto.getParticipantId()));
        Attendance toSave = AttendanceMapper.toEntity(dto, session, participant);
        toSave.setOwnerEmail(getCurrentUserEmail());
        Attendance saved = attendanceRepository.save(toSave);
        return AttendanceMapper.toDTO(saved);
    }

    @Override
    public AttendanceDTO update(Long id, AttendanceDTO dto) {
        String ownerEmail = getCurrentUserEmail();
        Attendance attendance = attendanceRepository.findByIdAndOwnerEmail(id, ownerEmail)
                .orElseThrow(() -> new RuntimeException("Attendance no encontrado o no pertenece al usuario actual con id: " + id));
        Session session = sessionRepository.findById(dto.getSessionId())
                .orElseThrow(() -> new RuntimeException("Session no encontrado con id: " + dto.getSessionId()));
        Participant participant = participantRepository.findById(dto.getParticipantId())
                .orElseThrow(() -> new RuntimeException("Participant no encontrado con id: " + dto.getParticipantId()));
        attendance.setSession(session);
        attendance.setParticipant(participant);
        attendance.setAttendedAt(dto.getAttendedAt());
        Attendance updated = attendanceRepository.save(attendance);
        return AttendanceMapper.toDTO(updated);
    }

    @Override
    public void delete(Long id) {
        String ownerEmail = getCurrentUserEmail();
        if (!attendanceRepository.existsByIdAndOwnerEmail(id, ownerEmail)) {
            throw new RuntimeException("Attendance no encontrado o no pertenece al usuario actual con id: " + id);
        }
        attendanceRepository.deleteById(id);
    }

    @Override
    public AttendanceDTO findById(Long id) {
        String ownerEmail = getCurrentUserEmail();
        Attendance attendance = attendanceRepository.findByIdAndOwnerEmail(id, ownerEmail)
                .orElseThrow(() -> new RuntimeException("Attendance no encontrado o no pertenece al usuario actual con id: " + id));
        return AttendanceMapper.toDTO(attendance);
    }

    @Override
    public List<AttendanceDTO> findAll() {
        String ownerEmail = getCurrentUserEmail();
        return attendanceRepository.findAllByOwnerEmail(ownerEmail)
                .stream()
                .map(AttendanceMapper::toDTO)
                .collect(Collectors.toList());
    }
}
