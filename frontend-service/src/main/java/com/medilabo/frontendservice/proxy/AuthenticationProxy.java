package com.medilabo.frontendservice.proxy;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthenticationProxy {
    private final RestTemplate restTemplate =  new RestTemplate();
}
