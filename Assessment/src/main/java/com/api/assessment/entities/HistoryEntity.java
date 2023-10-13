package com.api.assessment.entities;


/**
 * Represents the HistoryEntity
 */
public class HistoryEntity {

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

    public Integer getPatientId() {
        return patientId;
    }

    public void setPatientId(Integer patientId) {
        this.patientId = patientId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
