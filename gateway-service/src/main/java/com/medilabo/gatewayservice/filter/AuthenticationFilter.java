package com.medilabo.gatewayservice.filter;

import com.medilabo.gatewayservice.config.RouteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GatewayFilter {

    @Autowired
    private RouteValidator routeValidator;
    @Autowired
    private WebClient.Builder webClientBuilder;

    private final String AUTH_VALIDATE_URL = "http://authentication-service-container:8085/authentication/validate";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain){
        ServerHttpRequest request = exchange.getRequest();

        // 1. Check if the requested endpoint is a public (unsecured) one (defined in RouteValidator)
        if (!routeValidator.isSecured.test(request)) {
            return chain.filter(exchange);
        }

        // 2. Check if the Authorization header is present
        if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
            return this.onError(exchange, "Authorization header is missing", HttpStatus.UNAUTHORIZED);
        }

        // 3. Get the Authorization header value
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if(authHeader == null || !authHeader.startsWith("Bearer ")) {
            return this.onError(exchange, "Authorization header is invalid", HttpStatus.UNAUTHORIZED);
        }
        // 4. Extract the JWT token by removing the "Bearer " prefix (7 characters)
        String token = authHeader.substring(7);

        // 5. Asynchronously call the authentication-service to validate the token
        return webClientBuilder.build().get()
                .uri(AUTH_VALIDATE_URL, uriBuilder ->
                        uriBuilder.queryParam("token", token).build()
                )
                .retrieve()
                // 6. Handle error responses (like 403) from the auth-service
                .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> Mono.error(new RuntimeException("Token validation failed"))
                )
                .bodyToMono(Boolean.class)
                .flatMap(isValid -> {
                    // 7. Check the Boolean response from the auth-service
                    if(isValid) {
                        // 8A. (Success) Token is valid. Pass the request to the target service
                        return chain.filter(exchange);
                    } else {
                        // 8B. (Failure) Token is invalid (auth-service returned false). Return FORBIDDEN.
                        return this.onError(exchange, "Invalid Token", HttpStatus.FORBIDDEN);
                    }
                }).onErrorResume(e -> {
                    // 9. (Exception) Catch any errors from step 6 (e.g., auth-service is down)
                    return this.onError(exchange, "Token validation failed or authentication service is down", HttpStatus.FORBIDDEN);
                });
    }

    // Helper method to create a standardized error response
    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        return response.setComplete();
    }
}
