package com.api.assessment.service;

import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

/**
 * Represents the ApiService
 */
@Service
public class ApiService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${jwt.signingKey}")
    private String signingKey;

    private Key secretKey;

    @PostConstruct
    public void init() {
        secretKey = Keys.hmacShaKeyFor(signingKey.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken() {
        return Jwts.builder()
                .setSubject("assessment-api")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 10)))
                .signWith(secretKey)
                .compact();
    }

    /**
     * Gets method to retrieve data from an API
     *
     * @param url the url of the API
     * @param uri the uri of the API
     * @param typeReference the type of the data to be retrieved
     */
    public <T> Mono<T> GetMethods(String url, String uri, ParameterizedTypeReference<T> typeReference) {
        String token = generateToken();
        return webClientBuilder.build()
                .get()
                .uri(url + uri)
                .header("Authorization", "Bearer " + token)
                .retrieve()
                .bodyToMono(typeReference);
    }
}
