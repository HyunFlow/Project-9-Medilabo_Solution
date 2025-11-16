package com.medilabo.frontendservice.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class GatewayProperties {

    // patient service URI
    @Value("${patient-service-uri}")
    private String patientUri;

    // authentication service URI
    @Value("${authentication-service-uri}")
    private String authenticationUri;

    @Value("${note-service-uri}")
    private String noteUri;

    @Value("${risk-assessment-service-uri}")
    private String riskUri;
}
