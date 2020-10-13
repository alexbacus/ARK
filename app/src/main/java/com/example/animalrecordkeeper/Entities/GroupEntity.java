package com.example.animalrecordkeeper.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "group_table")
public class GroupEntity {
    @PrimaryKey()
    private int groupId;

    private String name;
    private boolean onFeedingList;
    private int basicStatus;

    public GroupEntity(int groupId, String name, boolean onFeedingList, int basicStatus) {
        this.groupId = groupId;
        this.name = name;
        this.onFeedingList = onFeedingList;
        this.basicStatus = basicStatus;
    }

    public int getGroupId() { return groupId; }

    public void setGroupId(int groupId) { this.groupId = groupId; }

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public boolean getOnFeedingList() { return onFeedingList; }

    public void setOnFeedingList(boolean onFeedingList) { this.onFeedingList = onFeedingList; }

    public int getBasicStatus() { return basicStatus; }

    public void setBasicStatus(int basicStatus) { this.basicStatus = basicStatus; }
}
