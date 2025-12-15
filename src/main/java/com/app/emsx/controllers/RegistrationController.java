package com.app.emsx.controllers;

import com.app.emsx.dtos.RegistrationDTO;
import com.app.emsx.services.RegistrationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/registrations")
@CrossOrigin("*")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @PostMapping
    public ResponseEntity<RegistrationDTO> create(@Valid @RequestBody RegistrationDTO dto) {
        RegistrationDTO saved = registrationService.create(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<RegistrationDTO> update(@PathVariable Long id, @Valid @RequestBody RegistrationDTO dto) {
        RegistrationDTO updated = registrationService.update(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        registrationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}")
    public ResponseEntity<RegistrationDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(registrationService.findById(id));
    }

    @GetMapping
    public ResponseEntity<List<RegistrationDTO>> getAll() {
        return ResponseEntity.ok(registrationService.findAll());
    }
}
