package com.medilabo.frontendservice.service;

import com.medilabo.frontendservice.dto.NoteDTO;
import com.medilabo.frontendservice.proxy.NoteProxy;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteProxy noteProxy;

    public NoteService(NoteProxy noteProxy) {
        this.noteProxy = noteProxy;
    }

    public List<NoteDTO> getAllNotesByPatId(Integer patId) {
        return noteProxy.getNotesFromPatId(patId);
    }

    public NoteDTO getNoteById(String id) {
        return noteProxy.getNoteById(id);
    }

    public NoteDTO createNote(NoteDTO note) {
        return noteProxy.createNote(note);
    }

    public NoteDTO updateNote(NoteDTO note, String id) {
        return noteProxy.updateNote(note, id);
    }

    public boolean deleteNote(String id) {
        Boolean result = noteProxy.deleteNote(id);
        if (result == null) {
            return false;
        }
        return result;
    }
}
