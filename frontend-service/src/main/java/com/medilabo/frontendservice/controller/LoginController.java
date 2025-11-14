package com.medilabo.frontendservice.controller;

import com.medilabo.frontendservice.configuration.GatewayProperties;
import com.medilabo.frontendservice.dto.LoginFormDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Controller
public class LoginController {
    private final RestTemplate restTemplate;
    private final GatewayProperties gatewayProperties;

    public LoginController(RestTemplate restTemplate, GatewayProperties gatewayProperties) {
        this.restTemplate = restTemplate;
        this.gatewayProperties = gatewayProperties;
    }

    @GetMapping({"/", "/login", "/loginPage"})
    public String loginPage(Model model) {
        model.addAttribute("loginForm", new LoginFormDTO());
        return "loginPage";
    }

    @PostMapping("/loginPage")
    public String doLogin(@ModelAttribute LoginFormDTO loginForm, HttpServletRequest request) {
        log.info("Processing login for username: {}", loginForm.getUsername());
        String accessToken = restTemplate.postForObject(gatewayProperties.getGetAuthenticationUri(), loginForm, String.class);
        log.info("Access Token Generated: {}", accessToken);
        HttpSession session = request.getSession();
        assert accessToken != null;
        session.setAttribute("token", accessToken);
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/loginPage";
    }
}
