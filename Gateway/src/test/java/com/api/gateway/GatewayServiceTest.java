package com.api.gateway;

import com.api.gateway.service.GatewayService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class GatewayServiceTest {

    @Value("${jwt.signingKey}")
    private String signingKeyString;

    @Autowired
    private GatewayService gatewayService;

    private String validToken;
    private String invalidToken;

    @BeforeEach
    public void setUp() {
        Key signingKey = Keys.hmacShaKeyFor(signingKeyString.getBytes(StandardCharsets.UTF_8));

        validToken = Jwts.builder()
                .setSubject("test")
                .signWith(signingKey)
                .compact();
        invalidToken = Jwts.builder()
                .setSubject("test")
                .signWith(Keys.secretKeyFor(SignatureAlgorithm.HS256))
                .compact();
    }

    @Test
    public void testIsTokenValidValidToken() {
        assertTrue(gatewayService.isTokenValid(validToken));
    }

    @Test
    public void testIsTokenValidInvalidToken() {
        assertFalse(gatewayService.isTokenValid(invalidToken));
    }
}
