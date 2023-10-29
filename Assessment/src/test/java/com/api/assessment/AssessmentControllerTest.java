package com.api.assessment;

import com.api.assessment.service.AssessmentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureWebTestClient
public class AssessmentControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AssessmentService assessmentService;

    @Test
    public void assessRiskTest() {
        // Mock the behavior of assessmentService to return a risk assessment
        when(assessmentService.assessRisks(anyInt()))
                .thenReturn(Mono.just("Patient: Doe John (age 33) diabetes assessment is: In Danger"));

        // Issue a GET request to the /assess/1 endpoint
        webTestClient.get().uri("/assess/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(String.class).isEqualTo("Patient: Doe John (age 33) diabetes assessment is: In Danger");
    }
}
