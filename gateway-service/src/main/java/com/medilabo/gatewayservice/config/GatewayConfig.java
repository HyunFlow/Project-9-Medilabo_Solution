package com.medilabo.gatewayservice.config;

import com.medilabo.gatewayservice.filter.AuthenticationFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GatewayConfig {

    @Value("${patient-service-uri}")
    private String patientUri;

    @Value("${note-service-uri}")
    private String noteUri;

//    @Value("${risk-service-uri")
//    private String riskUri;

    @Value("${authentication-service-uri}")
    private String authenticationUri;

    private final AuthenticationFilter authenticationFilter;

    public GatewayConfig(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("authentication-login", r -> r
                        .path("/authentication")
                        .filters(f -> f.filter(authenticationFilter))
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
                .build();
    }
}
