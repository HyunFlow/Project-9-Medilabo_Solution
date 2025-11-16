package com.medilabo.gatewayservice.config;

import com.medilabo.gatewayservice.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    @Value("${patient-service-uri}")
    private String patientUri;

    @Value("${note-service-uri}")
    private String noteUri;

    @Value("${risk-assessment-service-uri}")
    private String riskUri;

    @Value("${authentication-service-uri}")
    private String authenticationUri;

    @Value("${frontend-service-uri}")
    private String frontendUri;

    private final AuthenticationFilter authenticationFilter;

    public GatewayConfig(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("authentication-login", r -> r
                        .path("/authentication")
                        .uri(authenticationUri)
                )
                .route("patient-service-route", r -> r
                        .path("/patients/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri(patientUri)
                )
                .route("note-service-route", r -> r
                        .path("/notes/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri(noteUri)
                )
                .route("risk-assessment-service-route", r -> r
                        .path("/risk-assessment/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri(riskUri)
                )
                .route("frontend-service-route", r -> r
                        .path("/home")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri(frontendUri)
                )
                .build();
    }
}
