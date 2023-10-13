package com.api.gateway.service;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

/**
 * This service is used to validate JWT tokens
 */
@Service
public class GatewayService {

    @Value("${jwt.signingKey}")
    private String signingKeyString;

    private Key signingKey;

    /**
     * Initializes the signing key.
     */
    @PostConstruct
    public void init() {
        signingKey = Keys.hmacShaKeyFor(signingKeyString.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Checks if the given JWT token is valid
     *
     * @param token The JWT token to check
     * @return True if the token is valid, false otherwise
     */
    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(signingKey).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}


