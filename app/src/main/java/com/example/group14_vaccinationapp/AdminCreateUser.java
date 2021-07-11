package com.example.group14_vaccinationapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AdminCreateUser extends AppCompatActivity {
    TextInputLayout textInputLayout_name, textInputLayout_phone, textInputLayout_nric,
            textInputLayout_nric_confirm, textInputLayout_addressLine, textInputLayout_city,
            textInputLayout_postcode, textInputLayout_state, textInputLayout_password,
            textInputLayout_age,textInputLayout_notes;

    TextInputEditText textInputEditText_name, textInputEditText_phone, textInputEditText_nric,
            textInputEditText_nric_confirm, textInputEditText_addressLine,
            textInputEditText_city, textInputEditText_postcode, textInputEditText_state,
            textInputEditText_password, textInputEditText_age,textInputEditText_notes;
    ProgressBar progressBar;
    Button btnCreate;
    Spinner spinner;
    Boolean valid = false;
    String vaccineID,vaccinePrefer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_create_user);

        //set back button
        Toolbar toolbar = findViewById(R.id.toolbar_adminCreate);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressBar = findViewById(R.id.progressBar_Registration);
        //for validation
        textInputEditText_name = findViewById(R.id.et_adminCreate_name);
        textInputEditText_phone = findViewById(R.id.et_adminCreate_phone);
        textInputEditText_nric = findViewById(R.id.et_adminCreate_nric);
        textInputEditText_nric_confirm = findViewById(R.id.et_adminCreate_nric_confirm);
        textInputEditText_addressLine = findViewById(R.id.et_adminCreate_addressLine);
        textInputEditText_city = findViewById(R.id.et_adminCreate_city);
        textInputEditText_postcode = findViewById(R.id.et_adminCreate_postcode);
        textInputEditText_state = findViewById(R.id.et_adminCreate_state);
        textInputEditText_password = findViewById(R.id.et_adminCreate_password);
        textInputEditText_age = findViewById(R.id.et_adminCreate_age);
        textInputEditText_notes=findViewById(R.id.et_adminCreate_notes);

        textInputLayout_name = findViewById(R.id.Layout_adminCreate_name);
        textInputLayout_phone = findViewById(R.id.Layout_adminCreate_phone);
        textInputLayout_nric = findViewById(R.id.Layout_adminCreate_nric);
        textInputLayout_nric_confirm = findViewById(R.id.Layout_adminCreate_nric_confirm);
        textInputLayout_addressLine = findViewById(R.id.Layout_adminCreate_addressLine);
        textInputLayout_city = findViewById(R.id.Layout_adminCreate_city);
        textInputLayout_postcode = findViewById(R.id.Layout_adminCreate_postcode);
        textInputLayout_state = findViewById(R.id.Layout_adminCreate_state);
        textInputLayout_password = findViewById(R.id.Layout_adminCreate_password);
        textInputLayout_age = findViewById(R.id.Layout_adminCreate_age);
        textInputLayout_notes=findViewById(R.id.Layout_adminCreate_notes);

        btnCreate = findViewById(R.id.btnCreate_adminCreate);
        spinner = findViewById(R.id.spinnerVaccineType_adminCreate);

        textInputEditText_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                progressBar.setProgress(25);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        textInputEditText_nric.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                progressBar.setProgress(50);
            }

            @Override
            public void afterTextChanged(Editable s) {
                Validation validation = new Validation(getApplicationContext());
                if (validation.lengthValidate(textInputEditText_nric, textInputLayout_nric)) {
                    if (validation.checkUserExist(textInputEditText_nric, textInputLayout_nric)) {
                        // if user exist -> error
                        displayToast("Please proceed to login with your NRIC!");
                        valid = false;
                    } else {
                        displayToast("Valid NRIC!");
                        textInputEditText_password.setText(textInputEditText_nric.getText().toString());
                        valid = true;
                    }
                }
            }
        });

        textInputEditText_age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validation validation = new Validation(getApplicationContext());
                if (validation.requiredFieldValidation(textInputEditText_age,textInputLayout_age)&&
                        validation.ageValidate(textInputEditText_age, textInputLayout_age)) {

                    int age = Integer.parseInt(textInputEditText_age.getText().toString());

                    if (age < 12) {
                        new AlertDialog.Builder(AdminCreateUser.this)
                                .setTitle("Warning: \nCOVID-19 Vaccine Age Restriction")
                                .setMessage("Children under 12 years old are not permitted to take COVID-19 vaccination.")
                                .setCancelable(false)
                                .setPositiveButton("OK", null).show();
                    }
                }
            }
        });
    } //end of OnCreate

    @Override //when back button clicked
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void addNewUser(View view) {
        //call validation object
        Validation validation = new Validation(this);

        if (validation.requiredFieldValidation(textInputEditText_name, textInputLayout_name) &&
                validation.requiredFieldValidation(textInputEditText_phone, textInputLayout_phone) &&
                validation.phoneRegexValidate(textInputEditText_phone, textInputLayout_phone) &&
                validation.requiredFieldValidation(textInputEditText_nric, textInputLayout_nric) &&
                validation.requiredFieldValidation(textInputEditText_nric_confirm, textInputLayout_nric_confirm) &&
                validation.lengthValidate(textInputEditText_nric_confirm, textInputLayout_nric_confirm) &&
                validation.matchValidate(textInputEditText_nric, textInputEditText_nric_confirm, textInputLayout_nric_confirm) &&
                validation.requiredFieldValidation(textInputEditText_addressLine, textInputLayout_addressLine) &&
                validation.requiredFieldValidation(textInputEditText_city, textInputLayout_city) &&
                validation.requiredFieldValidation(textInputEditText_postcode, textInputLayout_postcode) &&
                validation.requiredFieldValidation(textInputEditText_state, textInputLayout_state) &&
                validation.requiredFieldValidation(textInputEditText_password, textInputLayout_password)) {

            //validate selected spinner
            vaccinePrefer = (String) spinner.getSelectedItem();
            if (vaccinePrefer.contentEquals("Please select a vaccine")) {
                displayToast("Please select user's vaccine preference!");
                valid = false;
            }else{
                vaccineID= String.valueOf(spinner.getSelectedItemPosition());
            }

            if (valid) {
                displayToast("pass validation");
                intoConfirmationState();//if validation passed
            }
        } // end Validation

    }

    private void intoConfirmationState() {
        try {
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            String name = textInputEditText_name.getText().toString(),

                    address = textInputEditText_addressLine.getText().toString() + ", " +
                            textInputEditText_city.getText().toString() + ", " +
                            textInputEditText_postcode.getText().toString() + ", " +
                            textInputEditText_state.getText().toString(),

                    phone = textInputEditText_phone.getText().toString(),
                    IC = textInputEditText_nric_confirm.getText().toString(),
                    password = textInputEditText_password.getText().toString(),
                    age=textInputEditText_age.getText().toString(),
                    notes=textInputEditText_notes.getText().toString();


            if (dbHelper.addUser(IC, name, password, age, phone, address, notes, vaccineID)) {

                new AlertDialog.Builder(AdminCreateUser.this)
                        .setTitle("New User Created Successfully")
                        .setMessage("Registered user information:\n\nFull Name: "+name+"\nNRIC: "+IC
                        +"\nDefault Password:refer to user's NRIC.")
                        .setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(AdminCreateUser.this,AdminUpdateDelete.class);
                                startActivity(intent);
                            }
                        }).show();

            } else {
                displayToast("Something went wrong, please try again later");
            }
        } catch (Exception e) {
            displayToast(e.toString());
        }
    }

    //make Toast message
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }
}