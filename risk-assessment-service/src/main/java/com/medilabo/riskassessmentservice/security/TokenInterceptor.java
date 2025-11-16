package com.medilabo.riskassessmentservice.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class TokenInterceptor implements HandlerInterceptor {

    /*
    Verify that all incoming clients request have a Token
    At the exemption of the "login page" route which is public
    */
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull Object handler) {

        String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            request.getSession().setAttribute("token", authorizationHeader);
        } else {
            log.warn("Authorization header is missing or invalid");
        }

        return true;
    }
}
