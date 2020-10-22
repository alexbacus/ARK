package com.example.animalrecordkeeper.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.animalrecordkeeper.Database.AnimalManagementRepository;
import com.example.animalrecordkeeper.Entities.FeedingEntity;

import java.util.List;

public class FeedingViewModel extends AndroidViewModel {
    private AnimalManagementRepository mRepository;
    private LiveData<List<FeedingEntity>> mAllFeedings;
    public FeedingViewModel(Application application){
        super(application);
        mRepository=new AnimalManagementRepository(application);
        mAllFeedings=mRepository.getAllFeedings();
    }
    public LiveData<List<FeedingEntity>> getAllFeedings(){
        return mAllFeedings;
    }
    public void insert(FeedingEntity feedingEntity){
        mRepository.insert(feedingEntity);
    }
    public void delete(int id) { mRepository.deleteFeeding(id); }
    public int lastID(){
        return mAllFeedings.getValue().size();
    }
}
