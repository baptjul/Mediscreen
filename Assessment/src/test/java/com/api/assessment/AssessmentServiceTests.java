package com.api.assessment;

import com.api.assessment.entities.HistoryEntity;
import com.api.assessment.entities.PatientEntity;
import com.api.assessment.service.ApiService;
import com.api.assessment.service.AssessmentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.Period;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

import static com.api.assessment.constants.ApiUrl.PATIENTURL;
import static com.api.assessment.constants.ApiUrl.HISTORYURL;

public class AssessmentServiceTests {
//    @InjectMocks
//    private AssessmentService assessmentService;
//
//    @Mock
//    private ApiService apiService;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void testAssessRisks() {
//        PatientEntity patient = new PatientEntity();
//        patient.setFamily("Smith");
//        patient.setGiven("John");
//        patient.setSex("M");
//        patient.setDob(LocalDate.of(2000, 1, 1));
//
//        HistoryEntity history = new HistoryEntity(1, "Patient has a history of heart disease");
//        List<HistoryEntity> histories = Collections.singletonList(history);
//
//        when(apiService.GetMethods(eq(PATIENTURL), anyString(), any()))
//                .thenReturn(Mono.just(patient));
//        when(apiService.GetMethods(eq(HISTORYURL), anyString(), any()))
//                .thenReturn(Mono.just(histories));
//
//        Mono<String> resultMono = assessmentService.assessRisks(1);
//
//        String result = resultMono.block();
//
//        int age = Period.between(patient.getDob(), LocalDate.now()).getYears();
//        String expectedRisk = assessmentService.assessRisks(age, patient.getSex(), histories.size());
//        String expected = "Patient: Smith John (age " + age + ") diabetes assessment is: " + expectedRisk;
//        assertEquals(expected, result);
//    }
}
