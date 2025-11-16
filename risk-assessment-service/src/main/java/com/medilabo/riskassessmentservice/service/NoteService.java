package com.medilabo.riskassessmentservice.service;

import com.medilabo.riskassessmentservice.model.NoteDTO;
import com.medilabo.riskassessmentservice.proxy.NoteProxy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class NoteService {
    private final NoteProxy noteProxy;
    public NoteService(NoteProxy noteProxy) {
        this.noteProxy = noteProxy;
    }
    public List<NoteDTO> getNotesByPatId(Integer patId) {
        return noteProxy.getNotesByPatId(patId);
    }
}
