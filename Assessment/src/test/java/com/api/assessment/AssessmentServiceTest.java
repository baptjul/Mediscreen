package com.api.assessment;

import com.api.assessment.entities.HistoryEntity;
import com.api.assessment.entities.PatientEntity;
import com.api.assessment.service.ApiService;
import com.api.assessment.service.AssessmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Import({ApiService.class, AssessmentService.class})
public class AssessmentServiceTest {

    @Autowired
    private AssessmentService assessmentService;

    @MockBean
    private ApiService apiService;

    @Test
    public void assessRisksTest() {
        PatientEntity mockPatient = new PatientEntity("Doe", "John", LocalDate.of(1990, 1, 1), "M", "123 Street", "1234567890");
        HistoryEntity mockHistory = new HistoryEntity(1, "Hémoglobine A1C high");
        List<HistoryEntity> mockHistories = Collections.singletonList(mockHistory);

        when(apiService.GetMethods(any(String.class), any(String.class), any()))
                .thenReturn(Mono.just(mockPatient), Mono.just(mockHistories));

        Mono<String> resultMono = assessmentService.assessRisks(1);

        StepVerifier.create(resultMono)
                .expectNext("Patient: Doe John (age 33) diabetes assessment is: No Risk Level")
                .verifyComplete();
    }

    @Test
    public void testCalculateRiskLevel() {
        String risk1 = assessmentService.calculateRiskLevel(25, "M", 5);
        assertEquals("Early onset", risk1);

        String risk2 = assessmentService.calculateRiskLevel(25, "F", 4);
        assertEquals("In Danger", risk2);
    }
}
