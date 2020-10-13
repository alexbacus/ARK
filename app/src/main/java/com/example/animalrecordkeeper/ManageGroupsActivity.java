package com.example.animalrecordkeeper;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.animalrecordkeeper.Entities.GroupEntity;
import com.example.animalrecordkeeper.ViewModel.GroupViewModel;
import com.example.animalrecordkeeper.ui.GroupAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class ManageGroupsActivity extends AppCompatActivity {
    public static final int NEW_GROUP_ACTIVITY_REQUEST_CODE = 1;
    private GroupViewModel mGroupViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_groups);
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
        final GroupAdapter adapter = new GroupAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mGroupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        mGroupViewModel.getAllGroups().observe(this, new Observer<List<GroupEntity>>() {
            @Override
            public void onChanged(@Nullable final List<GroupEntity> groups) {
                // Update the cached copy of the words in the adapter.
                List<GroupEntity> temp = new ArrayList<GroupEntity>();
                for(GroupEntity g: groups) {
                    if (g.getBasicStatus() != BasicStatus.TRASHED.getValue()) {
                        temp.add(g);
                    }
                }
                adapter.setGroups(temp);
            }
        });
    }

    public void initFab() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageGroupsActivity.this, GroupDetail.class);
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