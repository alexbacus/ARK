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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ManageAnimalsActivity extends AppCompatActivity {
    public static final int NEW_GROUP_ACTIVITY_REQUEST_CODE = 1;
    private AnimalViewModel mAnimalViewModel;
    private EditText searchText;
    private AnimalAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_animals);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        searchText = findViewById(R.id.EditSearch);
        adapter = new AnimalAdapter(this);
        mAnimalViewModel = new ViewModelProvider(this).get(AnimalViewModel.class);

        //initialize recycler view
        initList();

        //initialize add button
        initFab();

        initSearch();
    }

    public void initList() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAnimalViewModel.getAllAnimals().observe(this, new Observer<List<AnimalEntity>>() {
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

    public void initFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageAnimalsActivity.this, AnimalDetail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityForResult(intent, NEW_GROUP_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    public void initSearch() {
        Button search = findViewById(R.id.SearchBtn);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchText.getText().toString().length() > 0) {
                    mAnimalViewModel.searchAnimals(searchText.getText().toString()).observe(ManageAnimalsActivity.this, new Observer<List<AnimalEntity>>() {
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
                            adapter.notifyDataSetChanged();
                        }
                    });
                }
                else {
                    mAnimalViewModel.getAllAnimals().observe(ManageAnimalsActivity.this, new Observer<List<AnimalEntity>>() {
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
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
