package com.medilabo.gatewayservice.config;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.function.Predicate;

@Component
public class RouteValidator {

    // List of open route without authentication
    public static final List<String> openApiEndpoints = List.of(
            "/authentication",
            "/authentication/validate"
    );

    // Verification method if the request URI is included on the oneApiEndpoints
    public Predicate<ServerHttpRequest> isSecured =
            request -> openApiEndpoints.stream()
                    .noneMatch(uri -> request.getURI().getPath().startsWith(uri));
}
