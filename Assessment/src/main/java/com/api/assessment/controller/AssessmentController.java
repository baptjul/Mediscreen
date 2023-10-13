package com.api.assessment.controller;

import com.api.assessment.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

/**
 * RestController class that handles API endpoints.
 */
@RestController
public class AssessmentController {

    @Autowired
    private AssessmentService assessmentService;

    /**
     * Retrieves the assessment of the risk level of a patient
     *
     * @return a string representing the risk level of the patient
     */
    @GetMapping("/assess/{patientId}")
    public Mono<String> assessRisk(@PathVariable Integer patientId) {
        return assessmentService.assessRisks(patientId);
    }
}
