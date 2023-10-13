package com.api.History.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Represents the HistoryEntity
 */
@Document(collection = "patHistory")
public class HistoryEntity {

    @Id
    private String id;
    private Integer patientId;
    private String note;

    /**
     * Constructor for HistoryEntity
     */
    public HistoryEntity() {}

    /**
     * Constructs a HistoryEntity with all information
     * @param patientId the ID of the patient
     * @param note the notes concerning the patient
     */
    public HistoryEntity(Integer patientId, String note) {
        this.patientId = patientId;
        this.note = note;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getPatId() {
        return patientId;
    }

    public void setPatId(Integer patId) {
        this.patientId = patId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
