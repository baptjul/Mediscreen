package com.api.patients.controller;

import com.api.patients.entity.PatientEntity;
import com.api.patients.service.PatientServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RestController class that handles API endpoints.
 */
@RestController
public class PatientControllers {

    @Autowired
    private PatientServices patientServices;

    /**
     * Retrieves a list of all patients
     *
     * @return a list of Patient objects representing all patients
     */
    @GetMapping("/patient/list")
    public List<PatientEntity> getAllPatients() {
        return patientServices.getAllPatients();
    }

    /**
     * Retrieves a specific patient by ID
     *
     * @param id the ID of the patient to retrieve
     * @return the Patient found
     */
    @GetMapping("/patient/{id}")
    public PatientEntity getPatient(@PathVariable("id") Integer id) {
        return patientServices.getPatientById(id);
    }

    /**
     * Adds a new patient.
     *
     * @param patient The patient to be added
     * @return the newly added patient
     */
    @PostMapping(value = "/patient/add")
    public PatientEntity addPatient(@RequestBody PatientEntity patient) {
        return patientServices.addPatient(patient);
    }

    /**
     * Updates an existing patient
     *
     * @param id      the ID of the patient to update
     * @param patient rhe updated patient information
     * @return he updated patient
     */
    @PutMapping("/patient/update/{id}")
    public PatientEntity updatePatient(@PathVariable("id") Integer id, @RequestBody PatientEntity patient) {
        return patientServices.updatePatient(id, patient);
    }

    /**
     * Deletes a patient
     *
     * @param id the ID of the patient to delete
     */
    @DeleteMapping("/patient/delete/{id}")
    public List<PatientEntity> deletePatient(@PathVariable("id") Integer id) {
        return patientServices.deletePatient(id);
    }
}
