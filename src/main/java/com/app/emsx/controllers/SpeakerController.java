package com.app.emsx.controllers;

import com.app.emsx.dtos.SpeakerDTO;
import com.app.emsx.services.SpeakerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/speakers")
@CrossOrigin("*")
public class SpeakerController {

    @Autowired
    private SpeakerService speakerService;

    // Crear un speaker
    @PostMapping
    public ResponseEntity<SpeakerDTO> createSpeaker(@Valid @RequestBody SpeakerDTO speakerDTO) {
        SpeakerDTO savedSpeaker = speakerService.createSpeaker(speakerDTO);
        return new ResponseEntity<>(savedSpeaker, HttpStatus.CREATED);
    }

    // Actualizar un speaker
    @PutMapping("{id}")
    public ResponseEntity<SpeakerDTO> updateSpeaker(@PathVariable Long id, @Valid @RequestBody SpeakerDTO speakerDTO) {
        SpeakerDTO updatedSpeaker = speakerService.updateSpeaker(id, speakerDTO);
        return ResponseEntity.ok(updatedSpeaker);
    }

    // Eliminar un speaker
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteSpeaker(@PathVariable Long id) {
        speakerService.deleteSpeaker(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Obtener todos los speakers
    @GetMapping
    public ResponseEntity<List<SpeakerDTO>> getAllSpeakers() {
        List<SpeakerDTO> speakers = speakerService.findAllSpeakers();
        return ResponseEntity.ok(speakers);
    }

    // Obtener un speaker por ID
    @GetMapping("{id}")
    public ResponseEntity<SpeakerDTO> getSpeakerById(@PathVariable Long id) {
        SpeakerDTO speakerDTO = speakerService.findSpeakerById(id);
        return new ResponseEntity<>(speakerDTO, HttpStatus.OK);
    }
}
