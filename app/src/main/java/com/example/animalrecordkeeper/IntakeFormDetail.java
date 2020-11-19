package com.example.animalrecordkeeper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.animalrecordkeeper.Entities.IntakeFormEntity;
import com.example.animalrecordkeeper.ViewModel.IntakeFormViewModel;
import com.example.animalrecordkeeper.ui.IntakeFormAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class IntakeFormDetail extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private IntakeFormViewModel mIntakeFormViewModel;
    private EditText amountOther;
    private EditText name;
    private EditText email;
    private EditText address;
    private EditText city;
    private EditText state;
    private EditText zip;
    private EditText date;
    private EditText dateFound;
    private EditText species;
    private EditText addressFound;
    private EditText food;
    private EditText medications;
    private EditText circumstances;
    private String donationType;

    private boolean amount25;
    private CheckBox amount25Check;
    private boolean amount50;
    private CheckBox amount50Check;
    private boolean amount100;
    private CheckBox amount100Check;

    private Spinner donationTypeSpinner;

    private List<String> donationTypeList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intake_form_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        amountOther = findViewById(R.id.DonationOther);
        name = findViewById(R.id.InputName);
        email = findViewById(R.id.InputEmail);
        address = findViewById(R.id.InputAddress);
        city = findViewById(R.id.InputCity);
        state  = findViewById(R.id.InputState);
        zip = findViewById(R.id.InputZip);
        date = findViewById(R.id.InputDate);;
        dateFound = findViewById(R.id.InputDateFound);
        species = findViewById(R.id.InputSpecies);
        addressFound = findViewById(R.id.InputAnimalAddress);
        food = findViewById(R.id.InputFood);
        medications = findViewById(R.id.InputMedications);
        circumstances = findViewById(R.id.InputCircumstances);
        amount25Check = findViewById(R.id.Donation25);
        amount50Check = findViewById(R.id.Donation50);
        amount100Check = findViewById(R.id.Donation100);
        donationTypeSpinner = (Spinner) findViewById(R.id.TypeSpinner);

        final IntakeFormAdapter intakeFormAdapter = new IntakeFormAdapter(this);
        mIntakeFormViewModel = new ViewModelProvider(this).get(IntakeFormViewModel.class);
        mIntakeFormViewModel.getAllIntakeForms().observe(this, forms -> intakeFormAdapter.setIntakeForms(forms));

        setUpSpinner();

        initIntakeForm();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveAnimal();
            }
        });

        FloatingActionButton delete = findViewById(R.id.delete);
        if (getIntent().getIntExtra("intakeFormId", -1) == -1) {
            delete.setVisibility(View.INVISIBLE);
        }
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(IntakeFormDetail.this);
                builder.setTitle("Confirm deletion");
                builder.setMessage("Are you sure you would like to delete this intake form?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mIntakeFormViewModel.delete(getIntent().getIntExtra("intakeFormId", -1));
                        finish();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        return;
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void setShowOnFeeding(View view) {
        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {
            case R.id.Donation25:
                if (checked) {
                    amount25 = true;
                }
                else {
                    amount25 = false;
                }
            case R.id.Donation50:
                if (checked) {
                    amount50 = true;
                }
                else {
                    amount50 = false;
                }
            case R.id.Donation100:
                if (checked) {
                    amount100 = true;
                }
                else {
                    amount100 = false;
                }
        }
    }

    public void saveAnimal() {
        Intent intent = new Intent();
        int id=getIntent().getIntExtra("intakeFormId",-1);
        int basicStatus = BasicStatus.ACTIVE.getValue();
        if (id == -1) {
            id = (mIntakeFormViewModel.lastID()) + 1;
        }
        int donationAmount = 0;
        if (amount25) {
            donationAmount += 25;
        }
        if (amount50) {
            donationAmount += 50;
        }
        if (amount100) {
            donationAmount += 100;
        }
        if (!amountOther.getText().toString().equals("")) {
            donationAmount += Integer.parseInt(amountOther.getText().toString());
        }
        intent.putExtra("intakeFormId", id);
        intent.putExtra("donationAmount", donationAmount);
        intent.putExtra("donationType", donationType);
        intent.putExtra("name", name.getText().toString());
        intent.putExtra("email", email.getText().toString());
        intent.putExtra("mailingAddress", address.getText().toString());
        intent.putExtra("city", city.getText().toString());
        intent.putExtra("state", state.getText().toString());
        intent.putExtra("zip", zip.getText().toString());
        intent.putExtra("date", date.getText().toString());
        intent.putExtra("dateFound", dateFound.getText().toString());
        intent.putExtra("animalType", species.getText().toString());
        intent.putExtra("animalLocation", addressFound.getText().toString());
        intent.putExtra("animalFood", food.getText().toString());
        intent.putExtra("animalMedical", medications.getText().toString());
        intent.putExtra("circumstances", circumstances.getText().toString());
        intent.putExtra("basicStatus", basicStatus);

        IntakeFormEntity form = new IntakeFormEntity(id, donationAmount, donationType, name.getText().toString(), email.getText().toString(), address.getText().toString(), city.getText().toString(),
                state.getText().toString(), zip.getText().toString(), date.getText().toString(), dateFound.getText().toString(), species.getText().toString(), addressFound.getText().toString(), food.getText().toString(),
                medications.getText().toString(), circumstances.getText().toString(), basicStatus);
        mIntakeFormViewModel.insert(form);
        setResult(RESULT_OK, intent);
        finish();
    }

    private void setUpSpinner() {
        //set up species spinner
        Spinner spinner = (Spinner) findViewById(R.id.TypeSpinner);
        spinner.setOnItemSelectedListener(this);
        donationTypeList.add("Cash");
        donationTypeList.add("Check");
        donationTypeList.add("Credit Card");
        donationTypeList.add("Venmo");
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, donationTypeList);
        dataAdapter.setDropDownViewResource(R.layout.spinner_list);
        spinner.setAdapter(dataAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                donationType = parent.getItemAtPosition(position).toString();
            }
            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });
    }

    public void initIntakeForm() {
        if (getIntent().getIntExtra("intakeFormId", -1) != -1) {
            name.setText(getIntent().getStringExtra("name"));
            email.setText(getIntent().getStringExtra("email"));
            address.setText(getIntent().getStringExtra("mailingAddress"));
            city.setText(getIntent().getStringExtra("city"));
            state.setText(getIntent().getStringExtra("state"));
            zip.setText(getIntent().getStringExtra("zip"));
            date.setText(getIntent().getStringExtra("date"));
            dateFound.setText(getIntent().getStringExtra("dateFound"));
            species.setText(getIntent().getStringExtra("animalType"));
            addressFound.setText(getIntent().getStringExtra("animalLocation"));
            food.setText(getIntent().getStringExtra("animalFood"));
            medications.setText(getIntent().getStringExtra("animalMedical"));
            circumstances.setText(getIntent().getStringExtra("circumstances"));

            donationTypeSpinner.setSelection(donationTypeList.indexOf(getIntent().getStringExtra("donationType")));
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) { }

    public void onNothingSelected(AdapterView<?> arg0) { }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
