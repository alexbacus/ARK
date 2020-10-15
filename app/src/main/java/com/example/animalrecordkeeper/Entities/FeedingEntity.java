package com.example.animalrecordkeeper.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "feeding_table")
public class FeedingEntity {
    @PrimaryKey()
    private int feedingId;

    private String date;
    private String time;
    private int weight;
    private String notes;
    private int animalId;

    public FeedingEntity(int feedingId, String date, String time, int weight, String notes, int animalId) {
        this.feedingId = feedingId;
        this.date = date;
        this.time = time;
        this.weight = weight;
        this.notes = notes;
        this.animalId = animalId;
    }

    public int getFeedingId() {
        return feedingId;
    }

    public void setFeedingId(int feedingId) {
        this.feedingId = feedingId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }
}
