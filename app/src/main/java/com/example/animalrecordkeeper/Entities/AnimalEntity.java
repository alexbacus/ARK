package com.example.animalrecordkeeper.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "animal_table")
public class AnimalEntity {

    @PrimaryKey()
    private int animalId;

    private String name;
    private String dateReceived;
    private String species;
    private String gender;
    private String healthStatus;
    private int groupId;
    private String notes;
    private int basicStatus;

    public AnimalEntity(int animalId, String name, String dateReceived, String species, String gender, String healthStatus, int groupId, String notes, int basicStatus) {
        this.animalId = animalId;
        this.name = name;
        this.dateReceived = dateReceived;
        this.species = species;
        this.gender = gender;
        this.healthStatus = healthStatus;
        this.groupId = groupId;
        this.notes = notes;
        this.basicStatus = basicStatus;
    }

    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(String dateReceived) {
        this.dateReceived = dateReceived;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHealthStatus() {
        return healthStatus;
    }

    public void setHealthStatus(String healthStatus) {
        this.healthStatus = healthStatus;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getBasicStatus() {
        return basicStatus;
    }

    public void setBasicStatus(int basicStatus) {
        this.basicStatus = basicStatus;
    }
}
