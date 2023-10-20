package com.api.Users.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

/**
 * Utility class for generating JWT tokens.
 */
    @Component
    public class JwtUtil {

        @Value("${jwt.signingKey}")
        private String signingKey;

        private Key secretKey;

        /**
         * Initializes the secret key used for signing JWT tokens.
         */
        @PostConstruct
        public void init() {
            secretKey = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
        }

        /**
         * Generates a JWT token for the given username valid for 10 hours.
         *
         * @param username The username to generate a token for.
         * @return The JWT token.
         */
        public String generateToken(String username) {
            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 10)))
                    .signWith(secretKey)
                    .compact();
        }
    }