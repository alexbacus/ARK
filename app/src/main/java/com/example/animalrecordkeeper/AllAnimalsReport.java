package com.example.animalrecordkeeper;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.animalrecordkeeper.Entities.AnimalEntity;
import com.example.animalrecordkeeper.ViewModel.AnimalViewModel;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AllAnimalsReport extends AppCompatActivity {
    private AnimalViewModel mAnimalViewModel;
    private TextView numAnimalsView;
    private TextView numSquirrelsView;
    private TextView numBunniesView;
    private TextView numOpossumsView;
    private TextView numChipmunksView;
    private TextView numOtherView;
    private TextView numHealthyView;
    private TextView numInjuredView;
    private TextView numSickView;
    private TextView numAspiratedView;
    private TextView numRIPView;
    private int numAnimals;
    private int numSquirrels = 0;
    private int numBunnies = 0;
    private int numOpossums = 0;
    private int numChipmunks = 0;
    private int numOther = 0;
    private int numHealthy = 0;
    private int numInjured = 0;
    private int numSick = 0;
    private int numAspirated = 0;
    private int numRIP = 0;

    private TextView timestampView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_animals_report);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mAnimalViewModel = new ViewModelProvider(this).get(AnimalViewModel.class);

        //initialize recycler view
        initReport();
    }

    public void initReport() {
        numAnimalsView = findViewById(R.id.TotalNumberLabel);
        numSquirrelsView = findViewById(R.id.SquirrelsLabel);
        numBunniesView = findViewById(R.id.BunniesLabel);
        numOpossumsView = findViewById(R.id.OpossumsLabel);
        numChipmunksView = findViewById(R.id.ChipmunksLabel);
        numOtherView = findViewById(R.id.OtherLabel);
        numHealthyView = findViewById(R.id.HealthyLabel);
        numInjuredView = findViewById(R.id.InjuredLabel);
        numSickView = findViewById(R.id.SickLabel);
        numAspiratedView = findViewById(R.id.AspiratedLabel);
        numRIPView = findViewById(R.id.RIPLabel);
        timestampView = findViewById(R.id.Timestamp);
        mAnimalViewModel.getAllAnimals().observe(this, new Observer<List<AnimalEntity>>() {
            @Override
            public void onChanged(@Nullable final List<AnimalEntity> animals) {
                // Update the cached copy of the words in the adapter.
                List<AnimalEntity> temp = new ArrayList<AnimalEntity>();
                for(AnimalEntity a: animals) {
                    if (a.getBasicStatus() != BasicStatus.TRASHED.getValue()) {
                        switch(a.getSpecies()) {
                            case ("Squirrel"):
                                numSquirrels++;
                                break;
                            case("Bunny"):
                                numBunnies++;
                                break;
                            case("Opossum"):
                                numOpossums++;
                                break;
                            case("Chipmunk"):
                                numChipmunks++;
                                break;
                            case("Other"):
                                numOther++;
                                break;
                        }
                        switch(a.getHealthStatus()) {
                            case("Healthy"):
                                numHealthy++;
                                break;
                            case("Injured"):
                                numInjured++;
                                break;
                            case("Sick"):
                                numSick++;
                                break;
                            case("Aspirated"):
                                numAspirated++;
                                break;
                            case("RIP"):
                                numRIP++;
                                break;
                        }
                        temp.add(a);
                    }
                }
                numAnimals = temp.size();
                numAnimalsView.setText(numAnimalsView.getText().toString() + numAnimals);
                numSquirrelsView.setText(numSquirrelsView.getText().toString() + numSquirrels);
                numBunniesView.setText(numBunniesView.getText().toString() + numBunnies);
                numOpossumsView.setText(numOpossumsView.getText().toString() + numOpossums);
                numChipmunksView.setText(numChipmunksView.getText().toString() + numChipmunks);
                numOtherView.setText(numOtherView.getText().toString() + numOther);
                numHealthyView.setText(numHealthyView.getText().toString() + numHealthy);
                numInjuredView.setText(numInjuredView.getText().toString() + numInjured);
                numSickView.setText(numSickView.getText().toString() + numSick);
                numAspiratedView.setText(numAspiratedView.getText().toString() + numAspirated);
                numRIPView.setText(numRIPView.getText().toString() + numRIP);
            }
        });
        timestampView.setText(timestampView.getText().toString() + ZonedDateTime
                .now( ZoneId.systemDefault() )
                .format( DateTimeFormatter.ofPattern( "uuuu-MM-dd HH:mm:ss" ) ));
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
