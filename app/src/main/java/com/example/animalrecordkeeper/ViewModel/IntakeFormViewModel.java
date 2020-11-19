package com.example.animalrecordkeeper.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.animalrecordkeeper.Database.AnimalManagementRepository;
import com.example.animalrecordkeeper.Entities.IntakeFormEntity;

import java.util.List;

public class IntakeFormViewModel extends AndroidViewModel {
    private AnimalManagementRepository mRepository;
    private LiveData<List<IntakeFormEntity>> mAllIntakeForms;
    public IntakeFormViewModel(Application application){
        super(application);
        mRepository=new AnimalManagementRepository(application);
        mAllIntakeForms=mRepository.getAllIntakeForms();
    }
    public LiveData<List<IntakeFormEntity>> getAllIntakeForms(){
        return mAllIntakeForms;
    }
    public void insert(IntakeFormEntity intakeFormEntity){
        mRepository.insert(intakeFormEntity);
    }
    public void delete(int id) { mRepository.deleteIntakeForm(id); }
    public int lastID(){
        return mAllIntakeForms.getValue().size();
    }
}
