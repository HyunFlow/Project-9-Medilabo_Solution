package com.medilabo.frontendservice.security;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

@Slf4j
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }

    /**
     * Catch the Token in order to forward it to subsequent calls
     * This enables the front application to send requests through the gateway,
     * allowing it to access the back-end microservices securely
     */
    @RequestScope
    @Bean("authRestTemplate")
    public RestTemplate authRestTemplate(HttpSession session, RestTemplateBuilder builder) {
        return builder
                .additionalInterceptors(((request, body, execution) -> {
                    String token = (String) session.getAttribute("token");

                    if (StringUtils.hasText(token)) {
                        request.getHeaders().setBearerAuth(token);
                        log.debug("Forwarding Bear token to downstream service: {}", token);
                    } else {
                        log.warn("No token found in session, calling downstream without Authentication header");
                    }
                    return execution.execute(request, body);
                })).build();
    }
}
