package com.api.patients.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

/**
 * Represents the PatientEntity
 */
@Entity
public class PatientEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String family;
    private String given;
    private LocalDate dob;
    private String sex;
    private String address;
    private String phone;

    /**
     * Constructor for PatientEntity
     */
    public PatientEntity() {}

    /**
     * Constructs a PatientEntity with all information
     * @param family the last name of the patient
     * @param given the first name of the patient
     * @param dob the date of birth of the patient
     * @param sex the gender of the patient
     * @param address the address of the patient
     * @param phone the phone number of the patient
     */
    public PatientEntity(String family, String given, LocalDate dob, String sex, String address, String phone) {
        this.family = family;
        this.given = given;
        this.dob = dob;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String lastName) {
        this.family = lastName;
    }

    public String getGiven() {
        return given;
    }

    public void setGiven(String firstName) {
        this.given = firstName;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate birthDate) {
        this.dob = birthDate;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String gender) {
        this.sex = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
