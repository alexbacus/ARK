package com.example.animalrecordkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animalrecordkeeper.Entities.IntakeFormEntity;
import com.example.animalrecordkeeper.ViewModel.IntakeFormViewModel;
import com.example.animalrecordkeeper.ui.IntakeFormAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ManageIntakeForms extends AppCompatActivity {
    public static final int NEW_GROUP_ACTIVITY_REQUEST_CODE = 1;
    private IntakeFormViewModel mIntakeFormViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_intake_forms);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //initialize recycler view
        initList();

        //initialize add button
        initFab();
    }

    public void initList() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final IntakeFormAdapter adapter = new IntakeFormAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mIntakeFormViewModel = new ViewModelProvider(this).get(IntakeFormViewModel.class);
        mIntakeFormViewModel.getAllIntakeForms().observe(this, new Observer<List<IntakeFormEntity>>() {
            @Override
            public void onChanged(@Nullable final List<IntakeFormEntity> forms) {
                // Update the cached copy of the words in the adapter.
                List<IntakeFormEntity> temp = new ArrayList<IntakeFormEntity>();
                for(IntakeFormEntity f: forms) {
                    temp.add(f);
                }
                adapter.setIntakeForms(temp);
            }
        });
    }

    public void initFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageIntakeForms.this, IntakeFormDetail.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivityForResult(intent, NEW_GROUP_ACTIVITY_REQUEST_CODE);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
