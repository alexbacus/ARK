package com.example.animalrecordkeeper.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.animalrecordkeeper.Database.AnimalManagementRepository;
import com.example.animalrecordkeeper.Entities.AnimalEntity;

import java.util.List;

public class AnimalViewModel extends AndroidViewModel {
    private AnimalManagementRepository mRepository;
    private LiveData<List<AnimalEntity>> mAllAnimals;
    public AnimalViewModel(Application application){
        super(application);
        mRepository=new AnimalManagementRepository(application);
        mAllAnimals=mRepository.getAllAnimals();
    }
    public LiveData<List<AnimalEntity>> getAllAnimals(){
        return mAllAnimals;
    }
    public void insert(AnimalEntity animalEntity){
        mRepository.insert(animalEntity);
    }
    public void delete(int id) { mRepository.deleteAnimal(id); }
    public int lastID(){
        return mAllAnimals.getValue().size();
    }
    public LiveData<List<AnimalEntity>> searchAnimals(String name) { return mRepository.searchAnimals(name); }
    public LiveData<List<AnimalEntity>> getAnimalsByGroupId(int id) { return mRepository.getAnimalsByGroupId(id); }
    public void updateRecentFeeding(String recentFeeding, int animalId) { mRepository.updateRecentFeeding(recentFeeding, animalId); }
}
