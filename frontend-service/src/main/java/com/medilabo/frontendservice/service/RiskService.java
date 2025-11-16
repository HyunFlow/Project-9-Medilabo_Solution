package com.medilabo.frontendservice.service;

import com.medilabo.frontendservice.proxy.RiskProxy;
import org.springframework.stereotype.Service;

@Service
public class RiskService {
    private final RiskProxy riskProxy;

    public RiskService(RiskProxy riskProxy) {
        this.riskProxy = riskProxy;
    }

    public String getRiskLevelForPatientId(Integer patId) {
        return riskProxy.getRiskForPatId(patId);
    }
}
