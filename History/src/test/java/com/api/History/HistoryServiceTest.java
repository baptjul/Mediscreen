package com.api.History;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.api.History.entity.HistoryEntity;
import com.api.History.exception.DataNotFoundException;
import com.api.History.repository.HistoryRepository;
import com.api.History.service.HistoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@SpringBootTest
public class HistoryServiceTest {

    @Autowired
    private HistoryService historyService;

    @MockBean
    private HistoryRepository historyRepository;

    private HistoryEntity history1;

    @BeforeEach
    public void setUp() {
        history1 = new HistoryEntity(1, "Patient has a history of heart disease");
        history1.setId("1");

        when(historyRepository.findAll()).thenReturn(Collections.singletonList(history1));
        when(historyRepository.findById("1")).thenReturn(Optional.of(history1));
        when(historyRepository.findById("2")).thenReturn(Optional.empty());
    }

    @Test
    public void testGetPatientHistories() {
        Integer patientId = 1;
        when(historyRepository.findByPatientId(patientId)).thenReturn(Collections.singletonList(history1));

        List<HistoryEntity> histories = historyService.getPatientHistories(patientId);
        assertEquals(1, histories.size());
        assertEquals(history1, histories.get(0));
    }

    @Test
    public void testGetHistoryById() {
        String historyId = "1";
        HistoryEntity foundHistory = historyService.getPatientHistoryById(historyId);
        assertEquals(history1, foundHistory);
    }

    @Test
    public void testGetHistoryByIdNotFound() {
        String historyId = "2";
        assertThrows(DataNotFoundException.class, () -> {
            historyService.getPatientHistoryById(historyId);
        });
    }

    @Test
    public void testAddPatientHistory() {
        when(historyRepository.save(any(HistoryEntity.class))).thenReturn(history1);

        HistoryEntity newHistory = new HistoryEntity(1, "New patient history");
        HistoryEntity addedHistory = historyService.addPatientHistory(newHistory);
        assertEquals(history1, addedHistory);
    }

    @Test
    public void testUpdatePatientHistory() {
        when(historyRepository.save(any(HistoryEntity.class))).thenReturn(history1);

        String historyId = "1";
        HistoryEntity updatedHistory = historyService.updatePatientHistory(historyId, history1);
        assertEquals(history1, updatedHistory);
    }

    @Test
    public void testDeletePatientHistory() {
        String historyId = "1";
        List<HistoryEntity> deletedHistory = historyService.deletePatientHistory(historyId);
        assertEquals(0, deletedHistory.size());
    }
}

