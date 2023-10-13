package com.api.patients.service;

import com.api.patients.entity.PatientEntity;
import com.api.patients.exception.DataNotFoundException;
import com.api.patients.exception.ServerErrorException;
import com.api.patients.repository.PatientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents the PatientServices
 */
@Service
public class PatientServices {

    @Autowired
    private PatientRepository patientRepository;

    private final Logger logger = LoggerFactory.getLogger(PatientServices.class);

    /**
     * Gets all patients
     *
     * @return a list of patients
     */
    public List<PatientEntity> getAllPatients() {
        try {
            logger.info("found all patients");
            return patientRepository.findAll();
        } catch (Exception ex) {
            logger.error("error while trying to retrieve patients: {}", ex.getMessage());
            throw new ServerErrorException("Server error while trying to retrieve patients");
        }
    }

    /**
     * Gets a patient by ID
     *
     * @param id the ID of the patient to retrieve
     * @return the patient found
     */
    public PatientEntity getPatientById(Integer id) {
        logger.info("finding patient with ID: {}", id);
        return patientRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("Patient :" + id + "not found"));
    }

    /**
     * Adds a new patient
     *
     * @param patient the patient to be added
     * @return the newly added patient
     */
    public PatientEntity addPatient(PatientEntity patient) {
        try {
            logger.info("adding new patient: {}", patient);
            return patientRepository.save(patient);
        } catch (Exception ex) {
            logger.error("error while trying to save patient: {}", ex.getMessage());
            throw new ServerErrorException("Server error while trying to save patient");
        }
    }

    /**
     * Updates an existing patient
     *
     * @param id      the ID of the patient to update
     * @param patient the updated patient information
     * @return the updated patient
     */
    public PatientEntity updatePatient(Integer id, PatientEntity patient) {
        logger.info("updating patient with ID: {}", id);
        PatientEntity existingPatient = getPatientById(id);

        if (existingPatient != null) {
            existingPatient.setFamily(patient.getFamily());
            existingPatient.setGiven(patient.getGiven());
            existingPatient.setDob(patient.getDob());
            existingPatient.setSex(patient.getSex());
            existingPatient.setAddress(patient.getAddress());
            existingPatient.setPhone(patient.getPhone());

            try {
                return patientRepository.save(existingPatient);
            } catch (Exception ex) {
                logger.error("error while trying to update patient: {}", ex.getMessage());
                throw new ServerErrorException("Server error while trying to update patient");
            }
        } else {
            throw new DataNotFoundException("Patient with id " + id + " not found");
        }
    }

    /**
     * Deletes a patient
     *
     * @param id the ID of the patient to delete
     */
    public List<PatientEntity> deletePatient(Integer id) {
        logger.info("deleting patient with ID: {}", id);
        PatientEntity existingPatient = getPatientById(id);
        if (existingPatient != null) {
            try {
                patientRepository.delete(existingPatient);
                return getAllPatients();
            } catch (Exception ex) {
                logger.error("error while trying to delete patient: {}", ex.getMessage());
                throw new ServerErrorException("Server error while trying to delete patient");
            }
        } else {
            throw new DataNotFoundException("Patient :" + id + " not found");
        }
    }
}
