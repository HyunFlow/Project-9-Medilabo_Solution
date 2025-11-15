package com.medilabo.frontendservice.proxy;

import com.medilabo.frontendservice.configuration.GatewayProperties;
import com.medilabo.frontendservice.dto.PatientDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class PatientProxy {

    private final GatewayProperties routes;
    private final RestTemplate restTemplate;

    public PatientProxy(@Qualifier("authRestTemplate")RestTemplate restTemplate, GatewayProperties routes) {
        this.restTemplate = restTemplate;
        this.routes = routes;
    }

    public List<PatientDTO> getAllPatients() {
        ResponseEntity<List<PatientDTO>> response = restTemplate.exchange(
                routes.getPatientUri(),
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<PatientDTO>>() {}
        );
        return response.getBody();
    }

    public PatientDTO getPatientById(Integer id) {
        ResponseEntity<PatientDTO> response = restTemplate.exchange(
                routes.getPatientUri()+"/"+id,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<PatientDTO>() {}
        );
        return response.getBody();
    }

    public PatientDTO createPatient(PatientDTO patientDTO) {
        HttpEntity<PatientDTO> requestEntity = new HttpEntity<>(patientDTO);

        ResponseEntity<PatientDTO> response = restTemplate.exchange(
                routes.getPatientUri(),
                HttpMethod.POST,
                requestEntity,
                new ParameterizedTypeReference<PatientDTO>() {}
        );
        return response.getBody();
    }

    public PatientDTO updatePatient(PatientDTO patientDTO, Integer id) {
        HttpEntity<PatientDTO> requestEntity = new HttpEntity<>(patientDTO);

        ResponseEntity<PatientDTO> response = restTemplate.exchange(
                routes.getPatientUri()+"/"+id,
                HttpMethod.PUT,
                requestEntity,
                new ParameterizedTypeReference<PatientDTO>() {}
        );
        return response.getBody();
    }

    public Boolean deletePatient(Integer id) {
        ResponseEntity<Boolean> response = restTemplate.exchange(
                routes.getPatientUri()+"/"+id,
                HttpMethod.DELETE,
                null,
                new ParameterizedTypeReference<>() {}
        );
        return response.getBody();
    }

}
