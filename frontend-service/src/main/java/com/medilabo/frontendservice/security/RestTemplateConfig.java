package com.medilabo.frontendservice.security;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.annotation.RequestScope;

@Slf4j
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /*
   Catch the Token in order to forward it to subsequent calls
   This enables the front application to send requests through the gateway,
   allowing it to access the back-end microservices securely
    */
    @RequestScope
    @Bean("authRestTemplate")
    public RestTemplate authRestTemplate(HttpSession session) {
        String token = (String) session.getAttribute("token");
        log.debug("Bearer token to send to downstream service: {}", token);
        return new RestTemplateBuilder(rt -> rt.getInterceptors().add((request, body, execution) -> {
            request.getHeaders().add("Authorization", "Bearer " + token);
            return execution.execute(request, body);
        })
        ).build();
    }
}
