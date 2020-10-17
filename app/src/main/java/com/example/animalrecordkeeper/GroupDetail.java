package com.example.animalrecordkeeper;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animalrecordkeeper.Entities.AnimalEntity;
import com.example.animalrecordkeeper.Entities.GroupEntity;
import com.example.animalrecordkeeper.ViewModel.AnimalViewModel;
import com.example.animalrecordkeeper.ViewModel.GroupViewModel;
import com.example.animalrecordkeeper.ui.AnimalAdapter;
import com.example.animalrecordkeeper.ui.GroupAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class GroupDetail extends AppCompatActivity {
    private GroupViewModel mGroupViewModel;
    private AnimalViewModel mAnimalViewModel;
    private EditText groupName;
    private boolean showOnFeeding;
    private CheckBox showOnFeedingCheck;
    private List<AnimalEntity> associatedAnimals = new ArrayList<AnimalEntity>();

    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        groupName = findViewById(R.id.EditNameEditor);
        showOnFeedingCheck = findViewById(R.id.ShowFeedingCheck);
        if (getIntent().getIntExtra("groupId", -1) != -1) {
            groupName.setText(getIntent().getStringExtra("name"));
            showOnFeedingCheck.setChecked(getIntent().getBooleanExtra("onFeedingList", true));
        }

        final GroupAdapter adapter = new GroupAdapter(this);
        mGroupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        mGroupViewModel.getAllGroups().observe(this, groups -> adapter.setGroups(groups));

        initAnimalList();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveGroup();
            }
        });

        FloatingActionButton delete = findViewById(R.id.delete);
        if (getIntent().getIntExtra("groupId", -1) == -1) {
            delete.setVisibility(View.INVISIBLE);
        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (associatedAnimals.isEmpty()) {
                    mGroupViewModel.delete(getIntent().getIntExtra("groupId", -1));
                    finish();
                }
                else {
                    Toast.makeText(getApplicationContext(), "You cannot delete a group with associated animals.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void initAnimalList() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final AnimalAdapter animalAdapter = new AnimalAdapter(this);
        mAnimalViewModel = new ViewModelProvider(this).get(AnimalViewModel.class);
        recyclerView.setAdapter(animalAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAnimalViewModel.getAllAnimals().observe(this, new Observer<List<AnimalEntity>>() {
            @Override
            public void onChanged(@Nullable final List<AnimalEntity> animals) {
                associatedAnimals.clear();
                int groupId = getIntent().getIntExtra("groupId", -1);
                if (groupId != -1) {
                    for(AnimalEntity a: animals) {
                        if (a.getGroupId() == groupId && !(associatedAnimals.contains(a))) {
                            if (a.getBasicStatus() != BasicStatus.TRASHED.getValue()){
                                associatedAnimals.add(a);
                            }
                        }
                    }
                }
                if (!(associatedAnimals.isEmpty())) {
                    animalAdapter.setAnimals(associatedAnimals);
                }
            }
        });
    }

    public void setShowOnFeeding(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.ShowFeedingCheck:
                if (checked) {
                    showOnFeeding = true;
                }
                else {
                    showOnFeeding = false;
                }
        }
    }

    public void saveGroup() {
        Intent intent = new Intent();
        String name = groupName.getText().toString();
        intent.putExtra("name", name);
        intent.putExtra("onFeedingList", showOnFeeding);

        int id=getIntent().getIntExtra("groupId",-1);
        int basicStatus = BasicStatus.ACTIVE.getValue();
        if (id == -1) {
            id = (mGroupViewModel.lastID()) + 1;
        }
        intent.putExtra("groupId", id);
        GroupEntity group = new GroupEntity(id, name, showOnFeeding, basicStatus);
        mGroupViewModel.insert(group);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
