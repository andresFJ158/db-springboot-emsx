package com.app.emsx.controllers;

import com.app.emsx.dtos.SessionDTO;
import com.app.emsx.services.SessionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
@CrossOrigin("*")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @PostMapping
    public ResponseEntity<SessionDTO> create(@Valid @RequestBody SessionDTO dto) {
        SessionDTO saved = sessionService.createSession(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<SessionDTO> update(@PathVariable Long id, @Valid @RequestBody SessionDTO dto) {
        SessionDTO updated = sessionService.updateSession(id, dto);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        sessionService.deleteSession(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("{id}")
    public ResponseEntity<SessionDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(sessionService.findSessionById(id));
    }

    @GetMapping
    public ResponseEntity<List<SessionDTO>> getAll() {
        return ResponseEntity.ok(sessionService.findAllSessions());
    }

    @GetMapping("/by-event/{eventId}")
    public ResponseEntity<List<SessionDTO>> getByEvent(@PathVariable Long eventId) {
        return ResponseEntity.ok(sessionService.findByEvent(eventId));
    }

    @GetMapping("/by-speaker/{speakerId}")
    public ResponseEntity<List<SessionDTO>> getBySpeaker(@PathVariable Long speakerId) {
        return ResponseEntity.ok(sessionService.findBySpeaker(speakerId));
    }
}
