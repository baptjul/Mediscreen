package com.api.patients;

import static com.mysql.cj.conf.PropertyKey.logger;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.api.patients.entity.PatientEntity;
import com.api.patients.exception.ServerErrorException;
import com.api.patients.repository.PatientRepository;
import com.api.patients.service.PatientServices;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import com.api.patients.exception.DataNotFoundException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

@SpringBootTest
public class PatientServicesTest {

    @Autowired
    private PatientServices patientServices;

    @MockBean
    private PatientRepository patientRepository;

    private PatientEntity patient1;
    private PatientEntity patient2;

    @BeforeEach
    public void setUp() {
        patient1 = new PatientEntity("Doe", "John", LocalDate.of(2000, 1, 1), "M", "123 Main St", "0123456789");
        patient1.setId(1);

        patient2 = new PatientEntity("Doe", "Jane", LocalDate.of(2000, 1, 1), "F", "124 Main St", "0123456789");
        patient2.setId(2);

        when(patientRepository.findAll()).thenReturn(Arrays.asList(patient1, patient2));
        when(patientRepository.findById(1)).thenReturn(Optional.of(patient1));
        when(patientRepository.findById(2)).thenReturn(Optional.of(patient2));
        when(patientRepository.findById(3)).thenReturn(Optional.empty());
    }

    @Test
    public void testGetAllPatients() {
        assertEquals(2, patientServices.getAllPatients().size());
    }

    @Test
    public void testGetPatientByIdFound() {
        PatientEntity patient = patientServices.getPatientById(1);
        assertEquals("John", patient.getGiven());
    }

    @Test
    public void testGetPatientByIdNotFound() {
        assertThrows(DataNotFoundException.class, () -> patientServices.getPatientById(3));
    }

    @Test
    public void testAddPatient() {
        when(patientRepository.save(any(PatientEntity.class))).thenReturn(patient1);
        PatientEntity addedPatient = patientServices.addPatient(patient1);
        assertEquals("John", addedPatient.getGiven());
    }

    @Test
    public void testUpdatePatient() {
        PatientEntity updatedPatient = new PatientEntity("Doe", "Janet", LocalDate.of(2000, 1, 1), "F", "124 Main St", "0123456789");
        when(patientRepository.save(any(PatientEntity.class))).thenReturn(updatedPatient);

        PatientEntity result = patientServices.updatePatient(1, updatedPatient);
        assertEquals("Janet", result.getGiven());
    }

    @Test
    public void testDeletePatient() {
        doNothing().when(patientRepository).delete(any(PatientEntity.class));
        patientServices.deletePatient(1);
        verify(patientRepository, times(1)).delete(patient1);
    }

    @Test
    public void testGetAllPatientsServerError() {
        doThrow(new RuntimeException("Database error")).when(patientRepository).findAll();
        assertThrows(ServerErrorException.class, () -> patientServices.getAllPatients());
    }

    @Test
    public void testAddPatientServerError() {
        doThrow(new RuntimeException("Database error")).when(patientRepository).save(any(PatientEntity.class));
        assertThrows(ServerErrorException.class, () -> patientServices.addPatient(patient1));
    }

    @Test
    public void testUpdatePatientServerError() {
        when(patientRepository.findById(1)).thenReturn(Optional.of(patient1));
        doThrow(new RuntimeException("Database error")).when(patientRepository).save(any(PatientEntity.class));
        assertThrows(ServerErrorException.class, () -> patientServices.updatePatient(1, patient2));
    }

    @Test
    public void testDeletePatientServerError() {
        when(patientRepository.findById(1)).thenReturn(Optional.of(patient1));
        doThrow(new RuntimeException("Database error")).when(patientRepository).delete(any(PatientEntity.class));
        assertThrows(ServerErrorException.class, () -> patientServices.deletePatient(1));
    }

    @Test
    public void testUpdatePatientNotFound() {
        when(patientRepository.findById(10)).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> patientServices.updatePatient(3, patient2));
    }

    @Test
    public void testDeletePatientNotFound() {
        when(patientRepository.findById(10)).thenReturn(Optional.empty());
        assertThrows(DataNotFoundException.class, () -> patientServices.deletePatient(3));
    }

}