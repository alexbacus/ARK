package com.example.animalrecordkeeper.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.animalrecordkeeper.Entities.IntakeFormEntity;

import java.util.List;

@Dao
public interface IntakeFormDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(IntakeFormEntity intakeForm);

    @Query("SELECT * FROM intake_form_table WHERE basicStatus != 2 ORDER BY intakeFormId ASC")
    LiveData<List<IntakeFormEntity>> getAllIntakeForms();

    @Query("UPDATE intake_form_table SET basicStatus = 2 WHERE intakeFormId == :id")
    void deleteById(int id);
}
