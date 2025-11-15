package com.medilabo.frontendservice.proxy;

import com.medilabo.frontendservice.configuration.GatewayProperties;
import com.medilabo.frontendservice.dto.NoteDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class NoteProxy {
    private final RestTemplate restTemplate;
    private final GatewayProperties routes;

    public NoteProxy(@Qualifier("authRestTemplate") RestTemplate restTemplate, GatewayProperties routes) {
        this.restTemplate = restTemplate;
        this.routes = routes;
    }

    public List<NoteDTO> getNotesFromPatId(Integer patId) {
        ResponseEntity<List<NoteDTO>> response = restTemplate.exchange(
                routes.getNoteUri()+"/patient/"+patId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<NoteDTO>>() {}
        );
        return response.getBody();
    }

    public NoteDTO getNoteById(String id) {
        ResponseEntity<NoteDTO> response = restTemplate.exchange(
                routes.getNoteUri()+"/"+id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<NoteDTO>() {}
        );
        return response.getBody();
    }

    public NoteDTO createNote(NoteDTO noteDTO) {
        HttpEntity<NoteDTO> requestEntity = new HttpEntity<>(noteDTO);

        ResponseEntity<NoteDTO> response = restTemplate.exchange(
                routes.getNoteUri(),
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<NoteDTO>() {}
        );
        return response.getBody();
    }

    public NoteDTO updateNote(NoteDTO noteDTO, String id) {
        HttpEntity<NoteDTO> requestEntity = new HttpEntity<>(noteDTO);

        ResponseEntity<NoteDTO> response = restTemplate.exchange(
                routes.getNoteUri()+"/"+id,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<NoteDTO>() {}
        );
        return response.getBody();
    }

    public Boolean deleteNote(String id) {
        ResponseEntity<Boolean> response = restTemplate.exchange(
                routes.getNoteUri()+"/"+id,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }
}