package com.example.animalrecordkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animalrecordkeeper.Entities.AnimalEntity;
import com.example.animalrecordkeeper.ViewModel.AnimalViewModel;
import com.example.animalrecordkeeper.ui.AnimalAdapter;

import java.util.ArrayList;
import java.util.List;

public class FeedingAnimals extends AppCompatActivity {
    public static final int NEW_GROUP_ACTIVITY_REQUEST_CODE = 1;
    private AnimalViewModel mAnimalViewModel;
    private AnimalAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeding_animals);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        adapter = new AnimalAdapter(this);
        mAnimalViewModel = new ViewModelProvider(this).get(AnimalViewModel.class);

        //initialize recycler view
        initList();
    }

    public void initList() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAnimalViewModel.getAnimalsByGroupId(getIntent().getIntExtra("groupId", -1)).observe(this, new Observer<List<AnimalEntity>>() {
            @Override
            public void onChanged(@Nullable final List<AnimalEntity> animals) {
                // Update the cached copy of the words in the adapter.
                List<AnimalEntity> temp = new ArrayList<AnimalEntity>();
                for(AnimalEntity a: animals) {
                    if (a.getBasicStatus() != BasicStatus.TRASHED.getValue()) {
                        temp.add(a);
                    }
                }
                adapter.setAnimals(temp);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
