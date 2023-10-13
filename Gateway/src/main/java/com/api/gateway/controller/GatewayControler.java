package com.api.gateway.controller;

import com.api.gateway.entity.TokenEntity;
import com.api.gateway.service.GatewayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * This controller is used to validate JWT tokens
 */
@RestController
public class GatewayControler {

    @Autowired
    private GatewayService gatewayService;

    @PostMapping("/validateToken")
    public boolean validateToken(@RequestBody TokenEntity token) {
        return gatewayService.isTokenValid(token.getToken());
    }
}
