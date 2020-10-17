package com.example.animalrecordkeeper;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.animalrecordkeeper.Entities.AnimalEntity;
import com.example.animalrecordkeeper.Entities.FeedingEntity;
import com.example.animalrecordkeeper.Entities.GroupEntity;
import com.example.animalrecordkeeper.ViewModel.AnimalViewModel;
import com.example.animalrecordkeeper.ViewModel.FeedingViewModel;
import com.example.animalrecordkeeper.ui.AnimalAdapter;
import com.example.animalrecordkeeper.ui.FeedingAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FeedingActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    public static final int NEW_GROUP_ACTIVITY_REQUEST_CODE = 1;
    private FeedingViewModel mFeedingViewModel;
    private AnimalViewModel mAnimalViewModel;
    private FeedingAdapter adapter;
    private EditText date;
    private Button dateBtn;
    private TextView editDate;
    private EditText time;
    private Button timeBtn;
    private TextView editTime;
    private EditText editWeight;
    private EditText editNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeding);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        adapter = new FeedingAdapter(this);
        mFeedingViewModel = new ViewModelProvider(this).get(FeedingViewModel.class);
        mAnimalViewModel = new ViewModelProvider(this).get(AnimalViewModel.class);
        editWeight = findViewById(R.id.EditWeight);
        editNotes = findViewById(R.id.InputNotes);

        mFeedingViewModel.getAllFeedings().observe(this, feedings -> adapter.setFeedings(feedings));

        setUpDatePicker();
        setUpTimePicker();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFeeding();
            }
        });
    }

    public void saveFeeding() {
        Intent intent = new Intent();
        String date = editDate.getText().toString();
        String time = editTime.getText().toString();
        int weight = Integer.parseInt(editWeight.getText().toString());
        int animalId = getIntent().getIntExtra("animalId", -1);
        Log.i("animalId", String.valueOf(animalId));
        String notes = editNotes.getText().toString();
        intent.putExtra("date", date);
        intent.putExtra("weight", weight);
        intent.putExtra("time", time);
        intent.putExtra("animalId", animalId);

        int id=getIntent().getIntExtra("feedingId",-1);
        if (id == -1) {
            id = (mFeedingViewModel.lastID()) + 1;
        }
        intent.putExtra("feedingId", id);
        FeedingEntity feeding = new FeedingEntity(id, date, time, weight, notes, animalId);
        String recentFeeding = date + " " + time;
        mFeedingViewModel.insert(feeding);
        mAnimalViewModel.updateRecentFeeding(recentFeeding, getIntent().getIntExtra("animalId", -1));
        setResult(RESULT_OK, intent);
        finish();
    }

    private void setUpDatePicker(){
        dateBtn = findViewById(R.id.SelectDate);

        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editDate = findViewById(R.id.EditDate);
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

    private void setUpTimePicker(){
        timeBtn = findViewById(R.id.SelectTime);

        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTime = findViewById(R.id.EditTime);
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hour, int minute) {
        String strHour=String.valueOf(hour), strMin=String.valueOf(minute), strAM_PM;
        // if(strHour.length()==1) strHour="0" + strHour;
        if(strMin.length()==1) strMin="0" + strMin;

        if(hour==0){
            hour+=12;   strAM_PM="AM";
        }else if(hour==12){
            strAM_PM="PM";
        }else if(hour>12){
            hour=hour-12;   strAM_PM="PM";
        }else{
            strAM_PM="AM";
        }

        editTime.setText( hour + ":" + strMin + " " + strAM_PM);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
