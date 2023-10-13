package com.api.patients.repository;

import com.api.patients.entity.PatientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Represents the PatientRepository
 */
@Repository
public interface PatientRepository extends JpaRepository<PatientEntity, Integer> {
}
