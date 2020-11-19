package com.example.animalrecordkeeper;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.animalrecordkeeper.Entities.FeedingEntity;
import com.example.animalrecordkeeper.ViewModel.AnimalViewModel;
import com.example.animalrecordkeeper.ViewModel.FeedingViewModel;
import com.example.animalrecordkeeper.ui.FeedingAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;

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
    private Button calculateBtn;
    private int weight;
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
        calculateBtn = findViewById(R.id.CalculateBtn);

        mFeedingViewModel.getAllFeedings().observe(this, feedings -> adapter.setFeedings(feedings));

        setUpDatePicker();
        setUpTimePicker();
        initFeeding();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveFeeding();
            }
        });

        FloatingActionButton delete = findViewById(R.id.delete);
        if (getIntent().getIntExtra("feedingId", -1) == -1) {
            delete.setVisibility(View.INVISIBLE);
        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFeedingViewModel.delete(getIntent().getIntExtra("feedingId", -1));
                mAnimalViewModel.updateRecentFeeding(null, getIntent().getIntExtra("animalId", -1));
                finish();
            }
        });

        calculateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editWeight = findViewById(R.id.EditWeight);
                AlertDialog.Builder builder = new AlertDialog.Builder(FeedingActivity.this);
                if (editWeight.getText().toString().equals("")) {
                    weight = -1;
                    builder.setTitle("Error - Weight cannot be left empty");
                    builder.setMessage("Please enter a weight and try again.");
                }
                else {
                    weight = Integer.parseInt(editWeight.getText().toString());
                    float amount = calculateAmount(weight);
                    if (amount == -1) {
                        builder.setMessage("This squirrel can be free-fed formula. (Feed until it is full)");
                    }
                    else {
                        builder.setMessage("This squirrel should be fed: " + amount + "ml.");
                    }
                }
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        return;
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        editWeight.requestFocus();
    }

    public static float calculateAmount(int weight) {
        float amount = 0;
        if (weight <= 40) {
            amount = weight*(5.0f/100.0f);
        }
        if (weight > 40 && weight <= 90) {
            amount = weight*(6.0f/100.0f);
        }
        if (weight > 90 && weight <= 150) {
            amount = weight*(7.0f/100.0f);
        }
        if (weight > 250) {
            amount = -1;
        }
        return amount;
    }

    public void initFeeding() {
        int id=getIntent().getIntExtra("feedingId",-1);
        editDate = findViewById(R.id.EditDate);
        editTime = findViewById(R.id.EditTime);
        editWeight = findViewById(R.id.EditWeight);
        editNotes = findViewById(R.id.InputNotes);
        if (id != -1) {
            editDate.setText(getIntent().getStringExtra("date"));
            editTime.setText(getIntent().getStringExtra("time"));
            editWeight.setText(String.valueOf(getIntent().getIntExtra("weight", 1)));
            editNotes.setText(getIntent().getStringExtra("notes"));
        }
        else {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month = month + 1);
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
            String amPm;
            if (calendar.get(Calendar.AM_PM) == 0) {
                amPm = "AM";
            }
            else {
                amPm = "PM";
            }
            editTime.setText( calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE) + " " + amPm);
        }
    }


    public void saveFeeding() {
        if (!validate()) {
            Toast error = Toast.makeText(getApplicationContext(), "Date, Time, and Weight are required.", Toast.LENGTH_LONG);
            error.show();
            return;
        }
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
        FeedingEntity feeding = new FeedingEntity(id, date, time, weight, notes, animalId, BasicStatus.ACTIVE.getValue());
        String recentFeeding = weight + "g, " + date + " " + time;
        mFeedingViewModel.insert(feeding);
        mAnimalViewModel.updateRecentFeeding(recentFeeding, getIntent().getIntExtra("animalId", -1));
        setResult(RESULT_OK, intent);
        finish();
    }

    public boolean validate() {
        boolean isValid = true;
        editDate = findViewById(R.id.EditDate);
        editTime = findViewById(R.id.EditTime);
        String date = editDate.getText().toString();
        String time = editTime.getText().toString();
        if (date.equals("") || time.equals("") || editWeight.getText().toString().equals("")) {
            isValid = false;
        }
        return isValid;
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
