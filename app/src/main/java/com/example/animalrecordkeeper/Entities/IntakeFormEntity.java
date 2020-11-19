package com.example.animalrecordkeeper.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "intake_form_table")
public class IntakeFormEntity {
    @PrimaryKey()
    private int intakeFormId;

    private int donationAmount;
    private String donationType;
    private String name;
    private String email;
    private String mailingAddress;
    private String city;
    private String state;
    private String zip;
    private String date;
    private String dateFound;
    private String animalType;
    private String animalLocation;
    private String animalFood;
    private String animalMedical;
    private String circumstances;
    private int basicStatus;

    public IntakeFormEntity(int intakeFormId, int donationAmount, String donationType, String name, String email, String mailingAddress, String city,
            String state, String zip, String date, String dateFound, String animalType, String animalLocation, String animalFood, String animalMedical, String circumstances,
            int basicStatus) {
        this.intakeFormId = intakeFormId;
        this.donationAmount = donationAmount;
        this.donationType = donationType;
        this.name = name;
        this.email = email;
        this.mailingAddress = mailingAddress;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.date = date;
        this.dateFound = dateFound;
        this.animalType = animalType;
        this.animalLocation = animalLocation;
        this.animalFood = animalFood;
        this.animalMedical = animalMedical;
        this.circumstances = circumstances;
        this.basicStatus = basicStatus;
    }

    public int getIntakeFormId() {
        return intakeFormId;
    }

    public void setIntakeFormId(int intakeFormId) {
        this.intakeFormId = intakeFormId;
    }

    public int getDonationAmount() {
        return donationAmount;
    }

    public void setDonationAmount(int donationAmount) {
        this.donationAmount = donationAmount;
    }

    public String getDonationType() {
        return donationType;
    }

    public void setDonationType(String donationType) {
        this.donationType = donationType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(String mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateFound() {
        return dateFound;
    }

    public void setDateFound(String dateFound) {
        this.dateFound = dateFound;
    }

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public String getAnimalLocation() {
        return animalLocation;
    }

    public void setAnimalLocation(String animalLocation) {
        this.animalLocation = animalLocation;
    }

    public String getAnimalFood() {
        return animalFood;
    }

    public void setAnimalFood(String animalFood) {
        this.animalFood = animalFood;
    }

    public String getAnimalMedical() {
        return animalMedical;
    }

    public void setAnimalMedical(String animalMedical) {
        this.animalMedical = animalMedical;
    }

    public String getCircumstances() {
        return circumstances;
    }

    public void setCircumstances(String circumstances) {
        this.circumstances = circumstances;
    }

    public int getBasicStatus() {
        return basicStatus;
    }

    public void setBasicStatus(int basicStatus) {
        this.basicStatus = basicStatus;
    }
}
