package com.medilabo.riskassessmentservice.configuration;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class GatewayProperties {
    @Value("${patient-service-uri}")
    private String patientUri;

    @Value("${note-service-uri}")
    private String notesUri;
}
