package com.medilabo.noteservice.service;

import com.medilabo.noteservice.model.Note;
import com.medilabo.noteservice.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteService {
    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public List<Note> getByPatientId(Integer patId) {
        return noteRepository.findAllByPatId(patId);
    }
    public Note getById(String id) {
        Optional<Note> note = noteRepository.findById(id);
        return note.orElse(null);
    }

    public Note createNote(Note note) {
        return noteRepository.save(note);
    }

    public Note updateNote(String id, Note note) {
        if(!noteRepository.existsById(id)) {
            return null;
        }
        note.setId(id);
        return noteRepository.save(note);
    }

    public Boolean deleteNote(String id) {
        if(!noteRepository.existsById(id)) {
            return false;
        }
        noteRepository.deleteById(id);
        return true;
    }
}
