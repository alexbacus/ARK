package com.example.animalrecordkeeper;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.animalrecordkeeper.Entities.AnimalEntity;
import com.example.animalrecordkeeper.Entities.FeedingEntity;
import com.example.animalrecordkeeper.ViewModel.AnimalViewModel;
import com.example.animalrecordkeeper.ViewModel.FeedingViewModel;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SpecificAnimalReport extends AppCompatActivity {
    private FeedingViewModel mFeedingViewModel;
    private TextView titleView;
    private TextView speciesView;
    private TextView genderView;
    private TextView statusView;
    private TextView dateView;
    private TextView firstWeightView;
    private TextView lastWeightView;
    private TextView weightDiffView;

    private int firstWeight;
    private int lastWeight;
    private int weightDiff;

    private TextView timestampView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_specific_animal_report);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mFeedingViewModel = new ViewModelProvider(this).get(FeedingViewModel.class);

        //initialize recycler view
        initReport();
    }

    public void initReport() {
        titleView = findViewById(R.id.ReportTitle);
        speciesView = findViewById(R.id.SpeciesLabel);
        genderView = findViewById(R.id.GenderLabel);
        statusView = findViewById(R.id.StatusLabel);
        dateView = findViewById(R.id.DateReceivedLabel);
        firstWeightView = findViewById(R.id.FirstWeightLabel);
        lastWeightView = findViewById(R.id.LastWeightLabel);
        weightDiffView = findViewById(R.id.WeightDiffLabel);
        timestampView = findViewById(R.id.Timestamp);

        titleView.setText(titleView.getText().toString() + getIntent().getStringExtra("name"));
        speciesView.setText(speciesView.getText().toString() + getIntent().getStringExtra("species"));
        genderView.setText(genderView.getText().toString() + getIntent().getStringExtra("gender"));
        statusView.setText(statusView.getText().toString() + getIntent().getStringExtra("healthStatus"));
        dateView.setText(dateView.getText().toString() + getIntent().getStringExtra("dateReceived"));

        timestampView.setText(timestampView.getText().toString() + ZonedDateTime
                .now( ZoneId.systemDefault() )
                .format( DateTimeFormatter.ofPattern( "uuuu-MM-dd HH:mm:ss" ) ));

        mFeedingViewModel.getFeedingsByAnimalId(getIntent().getIntExtra("animalId", -1)).observe(this, new Observer<List<FeedingEntity>>() {
            @Override
            public void onChanged(@Nullable final List<FeedingEntity> feedings) {
                List<FeedingEntity> temp = new ArrayList<FeedingEntity>();
                    for(FeedingEntity f: feedings) {
                        temp.add(f);
                    }
                    if (!temp.isEmpty()) {
                        firstWeight = temp.get(0).getWeight();
                        firstWeightView.setText(firstWeightView.getText().toString() + firstWeight + "g");
                        lastWeight = temp.get(temp.size() - 1).getWeight();
                        lastWeightView.setText(lastWeightView.getText().toString() + lastWeight + "g");
                        String diff;
                        weightDiff = lastWeight - firstWeight;
                        if (weightDiff > 0) {
                            diff = "+";
                        }
                        else {
                            diff = "-";
                        }
                        weightDiffView.setText(weightDiffView.getText().toString() + diff + weightDiff + "g");
                    }
                    else {
                        firstWeightView.setText(firstWeightView.getText().toString() + "No data");
                        lastWeightView.setText(lastWeightView.getText().toString() + "No data");
                        weightDiffView.setText(weightDiffView.getText().toString() + "No data");
                    }
            }
        });
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
