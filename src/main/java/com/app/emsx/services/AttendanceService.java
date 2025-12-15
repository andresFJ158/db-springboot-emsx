package com.app.emsx.services;

import com.app.emsx.dtos.AttendanceDTO;
import java.util.List;

public interface AttendanceService {
    AttendanceDTO create(AttendanceDTO dto);
    AttendanceDTO update(Long id, AttendanceDTO dto);
    void delete(Long id);
    AttendanceDTO findById(Long id);
    List<AttendanceDTO> findAll();
}
