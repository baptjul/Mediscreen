package com.api.History.controller;

import com.api.History.entity.HistoryEntity;
import com.api.History.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * RestController class that handles API endpoints.
 */
@RestController
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    /**
     * Retrieves a list of all histories
     *
     * @return a list of History objects representing all histories
     */
    @GetMapping("/patHistory/patient/{patientId}")
    public List<HistoryEntity> getPatientHistories(@PathVariable Integer patientId) {
        return historyService.getPatientHistories(patientId);
    }

    /**
     * Retrieves a specific history by ID
     *
     * @param id the ID of the history to retrieve
     * @return the History found
     */
    @GetMapping("/patHistory/{id}")
    public HistoryEntity getHistory(@PathVariable("id") String id) {
        return historyService.getPatientHistoryById(id);
    }

    /**
     * Adds a new history.
     *
     * @param history The history to be added
     * @return the newly added history
     */
    @PostMapping("/patHistory/add")
    public HistoryEntity addHistory(@RequestBody HistoryEntity history) {
        return historyService.addPatientHistory(history);
    }

    /**
     * Updates an existing history
     *
     * @param id      the ID of the history to update
     * @param history rhe updated history information
     * @return he updated history
     */
    @PutMapping("/patHistory/update/{id}")
    public HistoryEntity updateHistory(@PathVariable("id") String id, @RequestBody HistoryEntity history) {
        return historyService.updatePatientHistory(id, history);
    }

    /**
     * Deletes an existing history
     *
     * @param id the ID of the history to delete
     */
    @DeleteMapping("/patHistory/delete/{id}")
    public List<HistoryEntity> deleteHistory(@PathVariable("id") String id) {
        return historyService.deletePatientHistory(id);
    }
}
