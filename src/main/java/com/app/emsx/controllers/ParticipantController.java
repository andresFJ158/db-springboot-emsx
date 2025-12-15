package com.app.emsx.controllers;

import com.app.emsx.dtos.ParticipantDTO;
import com.app.emsx.services.ParticipantService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/participants")
@CrossOrigin("*")
public class ParticipantController {

    @Autowired
    private ParticipantService participantService;

    @PostMapping
    public ResponseEntity<ParticipantDTO> create(@Valid @RequestBody ParticipantDTO dto) {
        ParticipantDTO saved = participantService.createParticipant(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<ParticipantDTO> update(@PathVariable Long id, @Valid @RequestBody ParticipantDTO dto) {
        ParticipantDTO updated = participantService.updateParticipant(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        participantService.deleteParticipant(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}")
    public ResponseEntity<ParticipantDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(participantService.findParticipantById(id));
    }

    @GetMapping
    public ResponseEntity<List<ParticipantDTO>> getAll() {
        return ResponseEntity.ok(participantService.findAllParticipants());
    }
}
