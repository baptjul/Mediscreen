package com.api.assessment.service;

import com.api.assessment.entities.HistoryEntity;
import com.api.assessment.entities.PatientEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.Period;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.logging.Logger;

import static com.api.assessment.constants.ApiUrl.PATIENTURL;
import static com.api.assessment.constants.ApiUrl.HISTORYURL;

/**
 * Represents the AssessmentService
 */
@Service
public class AssessmentService {

    @Autowired
    private ApiService apiService;

    private static final Logger logger = Logger.getLogger(AssessmentService.class.getName());

    /**
     * List of triggers
     */
    public final List<String> triggers = Arrays.asList(
            "Hémoglobine A1C", "Microalbumine", "Taille", "Poids", "Fumeur",
            "Anormal", "Cholestérol", "Vertige", "Rechute", "Réaction", "Anticorps"
    );

    /**
     * Calculates the risk level of a patient
     * @param age
     * @param sex
     * @param triggerCount
     * @return
     */
    public String calculateRiskLevel(int age, String sex, int triggerCount) {
        if (triggerCount == 0) {
            return "None";
        } else if (age < 30) {
            if (Objects.equals(sex, "M")) {
                if (triggerCount >= 5) {
                    return "Early onset";
                } else if (triggerCount >= 3) {
                    return "In Danger";
                }
            } else if (Objects.equals(sex, "F")) {
                if (triggerCount >= 7) {
                    return "Early onset";
                } else if (triggerCount >= 4) {
                    return "In Danger";
                }
            }
        } else {
            if (triggerCount >= 8) {
                return "Early onset";
            } else if (triggerCount >= 6) {
                return "In Danger";
            } else if (triggerCount >= 2) {
                return "Borderline";
            }
        }

        return "No Risk Level";
    }

    /**
     * Retrieves the assessment of the risk level of a patient
     *
     * @param patientId the ID of the patient
     * @return a string representing the risk level of the patient
     */
    public Mono<String> assessRisks(Integer patientId) {
        logger.info("Assessing risks for patient ID: " + patientId);

        Mono<PatientEntity> patientMono = apiService.GetMethods(PATIENTURL, String.valueOf(patientId), new ParameterizedTypeReference<>() {
        });
        Mono<List<HistoryEntity>> historiesMono = apiService.GetMethods(HISTORYURL,"patient/" + patientId, new ParameterizedTypeReference<>() {
        });

        return Mono.zip(patientMono, historiesMono)
                .flatMap(tuple -> {
                    PatientEntity patient = tuple.getT1();
                    List<HistoryEntity> histories = tuple.getT2();

                    if (histories.isEmpty()) {
                        return Mono.just("No Risk Level");
                    }

                    long triggerCount = histories.stream()
                            .filter(history -> history.getNote() != null)
                            .flatMap(history -> triggers.stream().filter(trigger -> history.getNote().toLowerCase().contains(trigger.toLowerCase())))
                            .count();

                    int age = Period.between(patient.getDob(), LocalDate.now()).getYears();
                    String risk = calculateRiskLevel(age, patient.getSex(), (int) triggerCount);

                    String result = "Patient: " + patient.getFamily() + " " + patient.getGiven() + " (age " + age + ") diabetes assessment is: " + risk;
                    return Mono.just(result);
                });
    }
}
