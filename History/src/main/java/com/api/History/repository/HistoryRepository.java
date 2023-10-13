package com.api.History.repository;

import com.api.History.entity.HistoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Represents the HistoryRepository
 */
@Repository
public interface HistoryRepository extends MongoRepository<HistoryEntity, String> {

    List<HistoryEntity> findByPatientId(Integer patientId);
}
