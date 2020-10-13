package com.example.animalrecordkeeper;

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.animalrecordkeeper.Entities.AnimalEntity;
import com.example.animalrecordkeeper.Entities.FeedingEntity;
import com.example.animalrecordkeeper.ViewModel.AnimalViewModel;
import com.example.animalrecordkeeper.ViewModel.FeedingViewModel;
import com.example.animalrecordkeeper.ViewModel.GroupViewModel;

import java.util.ArrayList;
import java.util.List;

public class AnimalDetail extends AppCompatActivity {
    private AnimalViewModel mAnimalViewModel;
    private FeedingViewModel mFeedingViewModel;
    private GroupViewModel mGroupViewModel;
    private EditText groupName;
    private boolean showOnFeeding;
    private CheckBox showOnFeedingCheck;
    private List<FeedingEntity> associatedFeedings = new ArrayList<FeedingEntity>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
