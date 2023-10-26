package com.api.assessment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Represents the ApiService
 */
@Service
public class ApiService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    /**
     * Gets method to retrieve data from an API
     *
     * @param url the url of the API
     * @param uri the uri of the API
     * @param typeReference the type of the data to be retrieved
     */
    public <T> Mono<T> GetMethods(String url, String uri, ParameterizedTypeReference<T> typeReference) {
        return webClientBuilder.build()
                .get()
                .uri(url + uri)
                .retrieve()
                .bodyToMono(typeReference);
    }
}
