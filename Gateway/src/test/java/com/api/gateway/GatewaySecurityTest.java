package com.api.gateway;

import com.api.gateway.service.GatewayService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.mockito.Mockito.*;
@SpringBootTest
@AutoConfigureWebTestClient
public class GatewaySecurityTest {
    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GatewayService gatewayService;

    @Test
    public void testFilter_withInvalidToken() {
        String invalidToken = "someInvalidToken";
        when(gatewayService.isTokenValid(invalidToken)).thenReturn(false);

        webTestClient.get()
                .uri("/patientService/patient/list")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + invalidToken)
                .exchange()
                .expectStatus().isUnauthorized();
    }

    @Test
    public void testFilter_withNoToken() {
        webTestClient.get()
                .uri("/patientService/patient/list")
                .exchange()
                .expectStatus().isUnauthorized();
    }
}
