package com.medilabo.riskassessmentservice.proxy;

import com.medilabo.riskassessmentservice.configuration.GatewayProperties;
import com.medilabo.riskassessmentservice.model.PatientDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PatientProxy {
    private final RestTemplate restTemplate;
    private final GatewayProperties routes;

    public PatientProxy(@Qualifier("authRestTemplate") RestTemplate restTemplate, GatewayProperties routes) {
        this.restTemplate = restTemplate;
        this.routes = routes;
    }

    public PatientDTO getPatientById(Integer id) {
        ResponseEntity<PatientDTO> response = restTemplate.exchange(
                routes.getPatientUri()+"/"+id,
                HttpMethod.GET,
                null,
                PatientDTO.class
        );
        return response.getBody();
    }
}
