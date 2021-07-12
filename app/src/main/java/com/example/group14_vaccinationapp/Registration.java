package com.example.group14_vaccinationapp;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.text.TextUtils.split;

public class Registration extends AppCompatActivity implements LocationListener {

    TextInputLayout textInputLayout_name, textInputLayout_phone, textInputLayout_nric,
            textInputLayout_nric_confirm, textInputLayout_addressLine, textInputLayout_city,
            textInputLayout_postcode, textInputLayout_state, textInputLayout_password,
            textInputLayout_password_confirm;
    TextInputEditText textInputEditText_name, textInputEditText_phone, textInputEditText_nric,
            textInputEditText_nric_confirm, textInputEditText_addressLine,
            textInputEditText_city, textInputEditText_postcode, textInputEditText_state,
            textInputEditText_password, textInputEditText_password_confirm;
    LocationManager locationManager;
    TextView confirmationInfo;
    ImageButton imageButtonGetLocation;
    Button buttonNext, buttonCancel, buttonConfirm,buttonGoLogin;
    RadioButton radioButton_pfizer, radioButton_sinovac, radioButton_AZ;
    LinearLayout linearLayout_confirm, linearLayout_form;
    ProgressBar progressBar,progressBar_Top;

    private String vaccinePrefer, vaccineID;
    private String age;
    private String notes;
    Boolean valid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //set back button
        Toolbar toolbar = findViewById(R.id.toolbarRegistration);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //for validation
        textInputEditText_name = findViewById(R.id.et_register_name);
        textInputEditText_phone = findViewById(R.id.et_register_phone);
        textInputEditText_nric = findViewById(R.id.et_register_NRIC);
        textInputEditText_nric_confirm = findViewById(R.id.et_register_NRIC_confirm);
        textInputEditText_addressLine = findViewById(R.id.et_register_address);
        textInputEditText_city = findViewById(R.id.et_register_city);
        textInputEditText_postcode = findViewById(R.id.et_register_postcode);
        textInputEditText_state = findViewById(R.id.et_register_state);
        textInputEditText_password = findViewById(R.id.et_register_password);
        textInputEditText_password_confirm = findViewById(R.id.et_register_passwordConfirm);

        textInputLayout_name = findViewById(R.id.textInputLayout_register_name);
        textInputLayout_phone = findViewById(R.id.textInputLayout_register_phone);
        textInputLayout_nric = findViewById(R.id.textInputLayout_register_nric);
        textInputLayout_nric_confirm = findViewById(R.id.textInputLayout_register_nric_confirm);
        textInputLayout_addressLine = findViewById(R.id.textInputLayout_register_addressLine);
        textInputLayout_city = findViewById(R.id.textInputLayout_register_city);
        textInputLayout_postcode = findViewById(R.id.textInputLayout_register_postcode);
        textInputLayout_state = findViewById(R.id.textInputLayout_register_state);
        textInputLayout_password = findViewById(R.id.textInputLayout_register_password);
        textInputLayout_password_confirm = findViewById(R.id.textInputLayout_register_passwordConfirm);

        imageButtonGetLocation = findViewById(R.id.imgBtn_register_location);
        buttonNext = findViewById(R.id.btnNext);
        buttonCancel = findViewById(R.id.btnCancel_register);
        buttonConfirm = findViewById(R.id.btnConfirmForm);
        buttonGoLogin =findViewById(R.id.btnGoLogin);
        radioButton_pfizer = findViewById(R.id.radioBtn_register_pfizer);
        radioButton_sinovac = findViewById(R.id.radioBtn_register_sinovac);
        radioButton_AZ = findViewById(R.id.radioBtn_register_AZ);
        linearLayout_confirm = findViewById(R.id.register_confirmationLayout);
        linearLayout_form = findViewById(R.id.register_form_linearLayout);
        confirmationInfo = findViewById(R.id.label_register_confirmationInfo);
        progressBar = findViewById(R.id.progressBar_register_getLocation);
        progressBar_Top=findViewById(R.id.progressBar_Registration);

        Intent intent = getIntent();
        age = intent.getStringExtra("age");
        notes = intent.getStringExtra("notes");

        textInputEditText_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validation validation = new Validation(Registration.this);
                if(validation.requiredFieldValidation(textInputEditText_name,textInputLayout_name)){
                    progressBar_Top.setProgress(30);
                }

            }
        });



        //validate the NRIC
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
                        progressBar_Top.setProgress(50);
                        valid = true;
                    }
                }
            }
        });

    }

    @Override //when back button clicked
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getLocation(View view) { // WHEN USER CLICK ON 'location' ICON
        progressBar.setVisibility(View.VISIBLE);//enable the progressbar
        grantPermission();//check if user have permission
        checkLocationIsEnabled(); // redirect user to location setting
        getLocation();//get the exact location
    }

    private void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    500, 5, (LocationListener) this);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void checkLocationIsEnabled() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = false; //initialization
        boolean networkEnabled = false; //initialization
        //check if location is enabled
        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //check if network is enabled
        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!gpsEnabled && !networkEnabled) { //if GPS is disabled
            new AlertDialog.Builder(Registration.this) //create Dialog box
                    .setTitle("Enable GPS Service")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Intent is used to redirect to GPS setting
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }).setNegativeButton("Cancel", null).show();
        }
    }

    private void grantPermission() {
        if (ActivityCompat.checkSelfPermission(Registration.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(Registration.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            //grant permission
            ActivityCompat.requestPermissions(Registration.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        try {
            displayToast("GPS is locating your current position");
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            //get location by using latitude and longitude
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);
            // split the full address by ,
            String[] addressSplitList = split(addressList.get(0).getAddressLine(0), ",");
            textInputEditText_addressLine.setText(addressSplitList[0] + addressSplitList[1] + addressSplitList[2]);
            textInputEditText_city.setText(addressList.get(0).getLocality());
            textInputEditText_state.setText(addressList.get(0).getAdminArea());
            textInputEditText_postcode.setText(addressList.get(0).getPostalCode());
            progressBar.setVisibility(View.GONE);
            progressBar_Top.setProgress(70);

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Unable to detect your location.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
    }


    public void next(View view) {
        //call validation object
        Validation validation = new Validation(this);

        if (validation.requiredFieldValidation(textInputEditText_name, textInputLayout_name) &&
                validation.requiredFieldValidation(textInputEditText_phone, textInputLayout_phone) &&
                validation.phoneRegexValidate(textInputEditText_phone, textInputLayout_phone) &&
                validation.requiredFieldValidation(textInputEditText_nric, textInputLayout_nric) &&
                validation.requiredFieldValidation(textInputEditText_nric_confirm, textInputLayout_nric_confirm) &&
                validation.lengthValidate(textInputEditText_nric, textInputLayout_nric) &&
                validation.lengthValidate(textInputEditText_nric_confirm, textInputLayout_nric_confirm) &&
                validation.matchValidate(textInputEditText_nric, textInputEditText_nric_confirm, textInputLayout_nric_confirm) &&
                validation.requiredFieldValidation(textInputEditText_addressLine, textInputLayout_addressLine) &&
                validation.requiredFieldValidation(textInputEditText_city, textInputLayout_city) &&
                validation.requiredFieldValidation(textInputEditText_postcode, textInputLayout_postcode) &&
                validation.requiredFieldValidation(textInputEditText_state, textInputLayout_state)&&
                validation.requiredFieldValidation(textInputEditText_password,textInputLayout_password)&&
                validation.requiredFieldValidation(textInputEditText_password_confirm,textInputLayout_password_confirm)&&
                validation.matchValidate(textInputEditText_password,textInputEditText_password_confirm,textInputLayout_password_confirm)){
            valid = true;
            if (!radioButton_pfizer.isChecked() && !radioButton_sinovac.isChecked() && !radioButton_AZ.isChecked()) {
                //if the NONE of the radio button is checked
                displayToast("Please select a preferred vaccine!");
                valid = false;
            }
        } else
            valid = false;

        if (valid) {
            progressBar_Top.setProgress(90);
            intoConfirmationState();//if validation passed
        }else
            displayToast("Please make sure all the fields are correct.");
    }

    public void intoConfirmationState() {
        //when passed validation
        //prompt user to confirm information is all correct.
        //set all the input field not editable
        textInputEditText_name.setEnabled(false);
        textInputEditText_phone.setEnabled(false);
        textInputEditText_nric.setEnabled(false);
        textInputEditText_nric_confirm.setEnabled(false);
        textInputEditText_addressLine.setEnabled(false);
        textInputEditText_state.setEnabled(false);
        textInputEditText_postcode.setEnabled(false);
        textInputEditText_city.setEnabled(false);
        radioButton_pfizer.setEnabled(false);
        radioButton_sinovac.setEnabled(false);
        radioButton_AZ.setEnabled(false);
        textInputLayout_password.setEnabled(false);        textInputLayout_password_confirm.setEnabled(false);
        // unable onClickEvent
        imageButtonGetLocation.setOnClickListener(null);
        //set Next button not visible
        buttonNext.setVisibility(View.GONE);
        buttonGoLogin.setVisibility(View.GONE);
        //set Confirmation buttons visible
        linearLayout_confirm.setVisibility(View.VISIBLE);
        confirmationInfo.setVisibility(View.VISIBLE);
    }

    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.radioBtn_register_pfizer:
                if (checked)
                    //display chosen vaccine
                    displayToast(getString(R.string.text_pfizer_biontech));
                vaccinePrefer = getString(R.string.text_pfizer_biontech);
                vaccineID = "1";
                break;
            case R.id.radioBtn_register_sinovac:
                if (checked)
                    //display chosen vaccine
                    displayToast(getString(R.string.text_sinovac_coronavac));
                vaccinePrefer = getString(R.string.text_sinovac_coronavac);
                vaccineID = "2";
                break;
            case R.id.radioBtn_register_AZ:
                if (checked)
                    //display chosen vaccine
                    displayToast(getString(R.string.text_astra_zeneca));
                vaccinePrefer = getString(R.string.text_astra_zeneca);
                vaccineID = "3";
                break;
            default:
                displayToast("Please select a preferred vaccine!");
                break;
        }
    }

    //make Toast message
    public void displayToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    /**
     * When user clicked on 'cancel' button on confirmation state
     * make all the input field available to edit - enabled
     */
    public void confirmation_cancel(View view) {
        textInputEditText_name.setEnabled(true);
        textInputEditText_phone.setEnabled(true);
        textInputEditText_nric.setEnabled(true);
        textInputEditText_nric_confirm.setEnabled(true);
        textInputEditText_addressLine.setEnabled(true);
        textInputEditText_state.setEnabled(true);
        textInputEditText_postcode.setEnabled(true);
        textInputEditText_city.setEnabled(true);
        textInputLayout_password.setEnabled(true);
        textInputLayout_password_confirm.setEnabled(true);
        // enable the onclick listener with the local getLocation() function
        imageButtonGetLocation.setOnClickListener(this::getLocation);
        radioButton_pfizer.setEnabled(true);
        radioButton_sinovac.setEnabled(true);
        radioButton_AZ.setEnabled(true);
        buttonNext.setVisibility(View.VISIBLE);
        buttonGoLogin.setVisibility(View.VISIBLE);
        //set Confirmation buttons visible
        linearLayout_confirm.setVisibility(View.GONE);
        confirmationInfo.setVisibility(View.GONE);
        progressBar_Top.setProgress(70);
    }

    /**
     * When user clicked on 'confirm'button on confirmation state
     */
    public void confirm(View view) {
        try {
            DatabaseHelper dbHelper = new DatabaseHelper(this);
            String name = textInputEditText_name.getText().toString(),

                    address = textInputEditText_addressLine.getText().toString() + ", " +
                            textInputEditText_city.getText().toString() + ", " +
                            textInputEditText_postcode.getText().toString() + ", " +
                            textInputEditText_state.getText().toString(),

                    phone = textInputEditText_phone.getText().toString(),
                    IC = textInputEditText_nric_confirm.getText().toString(),
                    password = textInputEditText_password.getText().toString();
            //int ageParse = Integer.parseInt(age);

            if (dbHelper.addUser(IC, name, password, age, phone, address, notes, vaccineID)) {
                Intent intent = new Intent(this, getOTP.class);
                Bundle confirmInfo = new Bundle();
                confirmInfo.putString("name", name);
                confirmInfo.putString("phone", textInputEditText_phone.getText().toString());
                confirmInfo.putString("NRIC", textInputEditText_nric.getText().toString());
                confirmInfo.putString("address", address);
                confirmInfo.putString("vaccine", vaccinePrefer);
                confirmInfo.putString("password",password);
                intent.putExtras(confirmInfo);
                startActivity(intent);
                finish();
            } else {
                displayToast("Something went wrong, please try again later");
            }
        } catch (Exception e) {
            displayToast(e.toString());
        }
    }

    public void goLoginPage(View view) {
        Intent intent = new Intent(this,Login.class);
        startActivity(intent);
    }
}