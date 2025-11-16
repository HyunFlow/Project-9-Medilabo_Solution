package com.medilabo.riskassessmentservice.controller;

import com.medilabo.riskassessmentservice.model.RiskResultDTO;
import com.medilabo.riskassessmentservice.service.RiskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/risk-assessment")
@RequiredArgsConstructor
public class RiskController {
    private final RiskService riskService;

    @GetMapping("/{patId}")
    public ResponseEntity<RiskResultDTO> getRiskByPatientId(@PathVariable("patId") Integer patId) {
        log.info("Request received for risk assessment by patId {}", patId);
        RiskResultDTO result = riskService.assess(patId);
        return ResponseEntity.ok(result);
    }

}
