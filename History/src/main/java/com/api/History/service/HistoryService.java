package com.api.History.service;

import com.api.History.entity.HistoryEntity;
import com.api.History.exception.DataNotFoundException;
import com.api.History.exception.ServerErrorException;
import com.api.History.repository.HistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Represents the HistoryService
 */
@Service
public class HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    private final Logger logger = LoggerFactory.getLogger(HistoryService.class);

    /**
     * Gets all histories for a patient
     *
     * @return a list of patient history
     */
    public List<HistoryEntity> getPatientHistories(Integer patientId) {
        try {
            logger.info("found all patient history");
            return historyRepository.findByPatientId(patientId);
        } catch (Exception ex) {
            logger.error("error while trying to retrieve patient histor: {}", ex.getMessage());
            throw new ServerErrorException("Server error while trying to retrieve patient history");
        }
    }

    /**
     * Gets a patient history by ID
     *
     * @param id the ID of the patient history to retrieve
     * @return the patient history found
     */
    public HistoryEntity getPatientHistoryById(String id) {
        logger.info("finding patient history with ID: {}", id);
        return historyRepository.findById(id)
                .orElseThrow(() -> new DataNotFoundException("patient history :" + id + "not found"));
    }

    /**
     * Adds a new patient history
     *
     * @param history the patient history to be added
     * @return the newly added patient history
     */
    public HistoryEntity addPatientHistory(HistoryEntity history) {
        try {
            logger.info("adding new patient history: {}", history);
            return historyRepository.save(history);
        } catch (Exception ex) {
            logger.error("error while trying to save patient history: {}", ex.getMessage());
            throw new ServerErrorException("Server error while trying to save patient history");
        }
    }

    /**
     * Updates an existing patient history
     *
     * @param id      the ID of the patient history to update
     * @param history the updated patient history information
     * @return the updated patient history
     */
    public HistoryEntity updatePatientHistory(String id, HistoryEntity history) {
        logger.info("updating patient history with ID: {}", id);
        HistoryEntity existingHistory = getPatientHistoryById(id);

        if (existingHistory != null) {
            existingHistory.setPatId(history.getPatId());
            existingHistory.setNote(history.getNote());

            try {
                return historyRepository.save(existingHistory);
            } catch (Exception ex) {
                logger.error("error while trying to update patient history: {}", ex.getMessage());
                throw new ServerErrorException("Server error while trying to update patient history");
            }
        } else {
            throw new DataNotFoundException("patient history with id " + id + " not found");
        }
    }

    /**
     * Deletes a patient history
     *
     * @param id the ID of the patient history to delete
     */
    public List<HistoryEntity> deletePatientHistory(String id) {
        logger.info("deleting patient history with ID: {}", id);
        HistoryEntity existingHistory = getPatientHistoryById(id);
        if (existingHistory != null) {
            try {
                Integer patientId = existingHistory.getPatId();
                historyRepository.delete(existingHistory);
                return getPatientHistories(patientId);
            } catch (Exception ex) {
                logger.error("error while trying to delete patient history: {}", ex.getMessage());
                throw new ServerErrorException("Server error while trying to delete patient history");
            }
        } else {
            throw new DataNotFoundException("patient history :" + id + " not found");
        }
    }
}
