package com.example.animalrecordkeeper.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.animalrecordkeeper.Database.AnimalManagementRepository;
import com.example.animalrecordkeeper.Entities.GroupEntity;

import java.util.List;

public class GroupViewModel extends AndroidViewModel {
    private AnimalManagementRepository mRepository;
    private LiveData<List<GroupEntity>> mAllGroups;
    public GroupViewModel(Application application){
        super(application);
        mRepository=new AnimalManagementRepository(application);
        mAllGroups=mRepository.getAllGroups();
    }
    public LiveData<List<GroupEntity>> getAllGroups(){
        return mAllGroups;
    }
    public void insert(GroupEntity groupEntity){
        mRepository.insert(groupEntity);
    }
    public void delete(int id) { mRepository.deleteGroup(id); }
    public int lastID(){
        return mAllGroups.getValue().size();
    }
}
