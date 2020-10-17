package com.example.animalrecordkeeper;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animalrecordkeeper.Entities.AnimalEntity;
import com.example.animalrecordkeeper.Entities.FeedingEntity;
import com.example.animalrecordkeeper.Entities.GroupEntity;
import com.example.animalrecordkeeper.ViewModel.AnimalViewModel;
import com.example.animalrecordkeeper.ViewModel.FeedingViewModel;
import com.example.animalrecordkeeper.ViewModel.GroupViewModel;
import com.example.animalrecordkeeper.ui.AnimalAdapter;
import com.example.animalrecordkeeper.ui.FeedingAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AnimalDetail extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {
    private AnimalViewModel mAnimalViewModel;
    private FeedingViewModel mFeedingViewModel;
    private GroupViewModel mGroupViewModel;
    private EditText animalName;
    private EditText dateReceived;
    private Button dateReceivedBtn;
    private TextView editDate;
    private EditText notes;
    private String species;
    private String gender;
    private String status;
    private String group;

    private Spinner speciesSpinner;
    private Spinner genderSpinner;
    private Spinner statusSpinner;
    private Spinner groupSpinner;

    private List<String> speciesList = new ArrayList<>();
    private List<String> genderList = new ArrayList<>();
    private List<String> statusList = new ArrayList<>();
    private List<String> groupList = new ArrayList<>();
    private List<FeedingEntity> associatedFeedings = new ArrayList<FeedingEntity>();
    private List<GroupEntity> groupEntities = new ArrayList<GroupEntity>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animal_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        animalName = findViewById(R.id.EditName);
        dateReceived = findViewById(R.id.EditDateReceived);
        notes = findViewById(R.id.InputNotes);
        dateReceivedBtn = findViewById(R.id.SelectDateReceived);
        speciesSpinner = (Spinner) findViewById(R.id.SpeciesSpinner);
        genderSpinner = (Spinner) findViewById(R.id.GenderSpinner);
        groupSpinner = (Spinner) findViewById(R.id.GroupSpinner);
        statusSpinner = (Spinner) findViewById(R.id.StatusSpinner);

        final AnimalAdapter animalAdapter = new AnimalAdapter(this);
        mAnimalViewModel = new ViewModelProvider(this).get(AnimalViewModel.class);
        mAnimalViewModel.getAllAnimals().observe(this, animals -> animalAdapter.setAnimals(animals));

        Spinner groupSpinner = (Spinner) findViewById(R.id.GroupSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, groupList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        groupSpinner.setAdapter(adapter);
        mGroupViewModel = new ViewModelProvider(this).get(GroupViewModel.class);
        mGroupViewModel.getAllGroups().observe(this, new Observer<List<GroupEntity>>() {
            @Override
            public void onChanged(@Nullable final List<GroupEntity> groups) {
                // Update the cached copy of the words in the adapter.
//                List<GroupEntity> temp = new ArrayList<GroupEntity>();
                groupEntities.clear();
                groupList.clear();
                groupList.add("None");
                for(GroupEntity g: groups) {
                    if (g.getBasicStatus() != BasicStatus.TRASHED.getValue()) {
                        groupEntities.add(g);
                    }
                }
                for (GroupEntity g: groupEntities) {
                    //set up species spinner
                    groupList.add(g.getName());
                }
                //set group spinner
                int groupId = getIntent().getIntExtra("groupId", -1);
                if (groupId != -1) {
                    for(GroupEntity g: groupEntities) {
                        if (g.getGroupId() == groupId) {
                            groupSpinner.setSelection(groupList.indexOf(g.getName()));
                        }
                    }
                }
                else {
                    groupSpinner.setSelection(groupList.indexOf("None"));
                }
                adapter.notifyDataSetChanged();
            }
        });

        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                group = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        setUpSpinners();
        setUpDatePicker();

        initAnimal();
        initFeedingList();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAnimal();
            }
        });

        FloatingActionButton delete = findViewById(R.id.delete);
        if (getIntent().getIntExtra("animalId", -1) == -1) {
            delete.setVisibility(View.INVISIBLE);
        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAnimalViewModel.delete(getIntent().getIntExtra("animalId", -1));
                finish();
            }
        });
    }

    public void initFeedingList() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final FeedingAdapter feedingAdapter = new FeedingAdapter(this);
        mFeedingViewModel = new ViewModelProvider(this).get(FeedingViewModel.class);
        recyclerView.setAdapter(feedingAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mFeedingViewModel.getAllFeedings().observe(this, new Observer<List<FeedingEntity>>() {
            @Override
            public void onChanged(@Nullable final List<FeedingEntity> feedings) {
                associatedFeedings.clear();
                int animalId = getIntent().getIntExtra("animalId", -1);
                if (animalId != -1) {
                    for(FeedingEntity f: feedings) {
                        if (f.getAnimalId() == animalId && !(associatedFeedings.contains(f))) {
                            associatedFeedings.add(f);
                        }
                    }
                }
                if (!(associatedFeedings.isEmpty())) {
                    feedingAdapter.setFeedings(associatedFeedings);
                }
            }
        });
    }

    public void saveAnimal() {
        Intent intent = new Intent();
        int id=getIntent().getIntExtra("animalId",-1);
        int basicStatus = BasicStatus.ACTIVE.getValue();
        if (id == -1) {
            id = (mAnimalViewModel.lastID()) + 1;
        }
        String name = animalName.getText().toString();
        if (name.equals("")) {
            name = species + " #" + id;
        }
        String received = dateReceived.getText().toString();
        String note = notes.getText().toString();

        intent.putExtra("name", name);
        intent.putExtra("dateReceived", received);
        intent.putExtra("species", species);
        intent.putExtra("gender", gender);
        intent.putExtra("healthStatus", status);
        intent.putExtra("notes", note);
        intent.putExtra("recentFeeding", getIntent().getStringExtra("recentFeeding"));
        intent.putExtra("animalId", id);
        int groupId = 0;
        if (!group.isEmpty() && group != "None") {
            if (!groupEntities.isEmpty()) {
                for(GroupEntity g: groupEntities) {
                    if (g.getName() == group) {
                        groupId = g.getGroupId();
                    }
                }
            }
        }
        else if (!group.isEmpty() && group == "None") {
            groupId = 0;
        }
        AnimalEntity animal = new AnimalEntity(id, name, received, species, gender, status, groupId, note, getIntent().getStringExtra("recentFeeding"), basicStatus);
        mAnimalViewModel.insert(animal);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void setUpSpinners() {
        //set up species spinner
        Spinner spinner = (Spinner) findViewById(R.id.SpeciesSpinner);
        spinner.setOnItemSelectedListener(this);
        speciesList.add("Squirrel");
        speciesList.add("Bunny");
        speciesList.add("Opossum");
        speciesList.add("Chipmunk");
        speciesList.add("Other");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, speciesList);
        dataAdapter.setDropDownViewResource(R.layout.spinner_list);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                species = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        //set up gender spinner
        Spinner genderSpinner = (Spinner) findViewById(R.id.GenderSpinner);
        genderSpinner.setOnItemSelectedListener(this);
        genderList.add("Female");
        genderList.add("Male");
        genderList.add("Unknown");
        ArrayAdapter<String> genderDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genderList);
        genderDataAdapter.setDropDownViewResource(R.layout.spinner_list);
        genderSpinner.setAdapter(genderDataAdapter);

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                gender = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        Spinner statusSpinner = (Spinner) findViewById(R.id.StatusSpinner);
        statusSpinner.setOnItemSelectedListener(this);

        statusList.add("Healthy");
        statusList.add("Injured");
        statusList.add("Ill");
        statusList.add("Aspirated");
        statusList.add("Unknown");

        ArrayAdapter<String> statusDataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, statusList);

        statusDataAdapter.setDropDownViewResource(R.layout.spinner_list);

        // attaching data adapter to spinner
        statusSpinner.setAdapter(statusDataAdapter);

        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                status = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        if (getIntent().getIntExtra("animalId", -1) == -1) {
            spinner.setSelection(speciesList.indexOf("Other"));
            genderSpinner.setSelection(genderList.indexOf("Unknown"));
            statusSpinner.setSelection(statusList.indexOf("Unknown"));
        }
    }

    public void initAnimal() {
        if (getIntent().getIntExtra("animalId", -1) != -1) {
            animalName.setText(getIntent().getStringExtra("name"));
            dateReceived.setText(getIntent().getStringExtra("dateReceived"));
            notes.setText(getIntent().getStringExtra("notes"));
            speciesSpinner.setSelection(speciesList.indexOf(getIntent().getStringExtra("species")));
            genderSpinner.setSelection(genderList.indexOf(getIntent().getStringExtra("gender")));
            statusSpinner.setSelection(statusList.indexOf(getIntent().getStringExtra("healthStatus")));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { }

    public void onNothingSelected(AdapterView<?> arg0) { }

    private void setUpDatePicker(){
        dateReceivedBtn = findViewById(R.id.SelectDateReceived);

        dateReceivedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDate = findViewById(R.id.EditDateReceived);
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month = month +1);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString;
        String monthStr;
        String dayStr;
        if (month < 10) {
            monthStr = "0" + month;
        }
        else {
            monthStr = String.valueOf(month);
        }

        if (dayOfMonth < 10) {
            dayStr = "0" + dayOfMonth;
        }
        else {
            dayStr = String.valueOf(dayOfMonth);
        }

        currentDateString = year + "-" + monthStr + "-" + dayStr;
        editDate.setText(currentDateString);
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
