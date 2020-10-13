package com.example.animalrecordkeeper.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.animalrecordkeeper.Entities.GroupEntity;

import java.util.List;

@Dao
public interface GroupDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GroupEntity group);

    @Query("DELETE FROM group_table")
    void deleteAllGroups();

    @Query("SELECT * FROM group_table ORDER BY groupId ASC")
    LiveData<List<GroupEntity>> getAllGroups();

    @Query("UPDATE group_table SET basicStatus = 2 WHERE groupId == :id")
    void deleteById(int id);
}
