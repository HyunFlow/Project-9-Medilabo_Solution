package com.medilabo.frontendservice.proxy;

import com.medilabo.frontendservice.configuration.GatewayProperties;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class RiskProxy {
    private final RestTemplate restTemplate;
    private final GatewayProperties routes;

    public RiskProxy(@Qualifier("authRestTemplate") RestTemplate restTemplate, GatewayProperties routes) {
        this.restTemplate = restTemplate;
        this.routes = routes;
    }

    public String getRiskForPatId(Integer patId) {
        ResponseEntity<String> response = restTemplate.exchange(
                routes.getRiskUri() + "/" + patId,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<String>() {});
        return response.getBody();
    }
}