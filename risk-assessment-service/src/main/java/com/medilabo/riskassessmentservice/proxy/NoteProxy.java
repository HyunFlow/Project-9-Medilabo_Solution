package com.medilabo.riskassessmentservice.proxy;

import com.medilabo.riskassessmentservice.configuration.GatewayProperties;
import com.medilabo.riskassessmentservice.model.NoteDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
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

    public List<NoteDTO> getNotesByPatId(Integer patId) {
        ResponseEntity<List<NoteDTO>> response = restTemplate.exchange(
                routes.getNotesUri() + "/patient/" + patId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<NoteDTO>>() {});
        return response.getBody();
    }
}