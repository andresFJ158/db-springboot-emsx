package com.app.emsx.services;

import com.app.emsx.dtos.RegistrationDTO;
import java.util.List;

public interface RegistrationService {
    RegistrationDTO create(RegistrationDTO dto);
    RegistrationDTO update(Long id, RegistrationDTO dto);
    void delete(Long id);
    RegistrationDTO findById(Long id);
    List<RegistrationDTO> findAll();
}
