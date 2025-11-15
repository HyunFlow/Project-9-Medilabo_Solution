package com.medilabo.noteservice.controller;

import com.medilabo.noteservice.model.Note;
import com.medilabo.noteservice.service.NoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notes")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Note> getNoteById(@PathVariable("id") String id) {
        Note note = noteService.getById(id);
        if (note == null) {
            log.error("Note not found with id {}", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(note);
    }

    @GetMapping("/patient/{patId}")
    public ResponseEntity<List<Note>> getNoteByPatientId(@PathVariable("patId") Integer patId) {
        List<Note> notes = noteService.getByPatientId(patId);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(notes);
    }

    @PostMapping
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        Note createdNote = noteService.createNote(note);
        if(createdNote == null) {
            log.error("Note could not be created");
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable("id") String id, @RequestBody Note note) {
        Note updatedNote = noteService.updateNote(id, note);
        if(updatedNote == null) {
            log.error("Note ID: {} could not be updated", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedNote);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteNote(@PathVariable("id") String id) {
        Boolean deleted = noteService.deleteNote(id);
        if(!deleted) {
            log.error("Note ID: {} could not be deleted", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(true);
    }
}
