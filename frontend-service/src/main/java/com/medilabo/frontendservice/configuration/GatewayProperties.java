package com.medilabo.frontendservice.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class GatewayProperties {

    // patient service URIs
    @Value("${patient-service-uri}/patient")
    private String PatientUri;

    @Value("${patient-service-uri}/patients")
    private String allPatientsUri;

    @Value("${patient-service-uri}/new")
    private String patientCreationUri;

    @Value("${patient-service-uri}/update")
    private String patientUpdateUri;

    @Value("${patient-service-uri}/delete")
    private String patientDeleteUri;

    // authentication service URI
    @Value("${authentication-service-uri}")
    private String getAuthenticationUri;
}
