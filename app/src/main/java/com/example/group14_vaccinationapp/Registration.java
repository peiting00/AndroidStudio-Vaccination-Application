package com.example.group14_vaccinationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

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
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Registration extends AppCompatActivity implements LocationListener{

    TextInputEditText EditText_address1,EditText_area, EditText_postcode, EditText_state;
    TextInputLayout textInputLayout_name,textInputLayout_phone,textInputLayout_nric,
            textInputLayout_nric_confirm,textInputLayout_addressLine,textInputLayout_city,
            textInputLayout_postcode,textInputLayout_state;
    TextInputEditText textInputEditText_name,textInputEditText_phone,textInputEditText_nric,
            textInputEditText_nric_confirm,textInputEditText_addressLine,
            textInputEditText_city,textInputEditText_postcode,textInputEditText_state;
    ImageButton btnLocation;
    LocationManager locationManager;
    ImageButton imageButtonGetLocation;
    Button buttonNext,buttonCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //set back button
        Toolbar toolbar = findViewById(R.id.toolbarRegistration);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //for location
        EditText_address1 = findViewById(R.id.et_register_address);
        EditText_area = findViewById(R.id.et_register_city);
        EditText_postcode = findViewById(R.id.et_register_postcode);
        EditText_state = findViewById(R.id.et_register_state);
        btnLocation = findViewById(R.id.imgBtn_register_location);

        //for validation
        textInputEditText_name=findViewById(R.id.et_register_name);
        textInputEditText_phone=findViewById(R.id.et_register_phone);
        textInputEditText_nric=findViewById(R.id.et_register_NRIC);
        textInputEditText_nric_confirm=findViewById(R.id.et_register_NRIC_confirm);
        textInputEditText_addressLine=findViewById(R.id.et_register_address);
        textInputEditText_city=findViewById(R.id.et_register_city);
        textInputEditText_postcode=findViewById(R.id.et_register_postcode);
        textInputEditText_state=findViewById(R.id.et_register_state);

        textInputLayout_name=findViewById(R.id.textInputLayout_register_name);
        textInputLayout_phone=findViewById(R.id.textInputLayout_register_phone);
        textInputLayout_nric=findViewById(R.id.textInputLayout_register_nric);
        textInputLayout_nric_confirm=findViewById(R.id.textInputLayout_register_nric_confirm);
        textInputLayout_addressLine=findViewById(R.id.textInputLayout_register_addressLine);
        textInputLayout_city=findViewById(R.id.textInputLayout_register_city);
        textInputLayout_postcode=findViewById(R.id.textInputLayout_register_postcode);
        textInputLayout_state=findViewById(R.id.textInputLayout_register_state);

        imageButtonGetLocation=findViewById(R.id.imgBtn_register_location);
        buttonNext=findViewById(R.id.btnNext);
        buttonCancel=findViewById(R.id.btnCancel_register);
    }

    @Override //when back button clicked
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void getLocation(View view) {
        //check if user have Location Permission
        grantPermission();
        checkLocationIsEnabled(); // redirect user to location setting
        getLocation();

    }

    private void getLocation() {
        try{
            locationManager=(LocationManager)getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,500,5, (LocationListener) this);
        }catch(SecurityException e){
            e.printStackTrace();
        }
    }

    private void checkLocationIsEnabled() {
        LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        boolean gpsEnabled = false; //initialization
        boolean networkEnabled = false; //initialization
        //check if location is enabled
        try{
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }catch (Exception e){
            e.printStackTrace();
        }

        //check if network is enabled
        try{
            networkEnabled=lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
        }catch(Exception e){
            e.printStackTrace();
        }

        if(!gpsEnabled && !networkEnabled){ //if GPS is disabled
            new AlertDialog.Builder(Registration.this)
                    .setTitle("Enable GPS Service")
                    .setCancelable(false)
                    .setPositiveButton("Enable", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //Intent is used to redirect to GPS setting
                            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    }).setNegativeButton("Cancel",null).show();
        }
    }

    private void grantPermission() {
        if (ActivityCompat.checkSelfPermission(Registration.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(Registration.this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            //grant permission
            ActivityCompat.requestPermissions(Registration.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION},100);
        }
    }


    @Override
    public void onLocationChanged(@NonNull Location location) {
        try{
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            //get location by using latitude and longitude
            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),
                    location.getLongitude(), 1);

            EditText_address1.setText(addressList.get(0).getAddressLine(0));
            EditText_area.setText(addressList.get(0).getLocality());
            EditText_state.setText(addressList.get(0).getAdminArea());
            EditText_postcode.setText(addressList.get(0).getPostalCode());
        }catch(IOException e){
            e.printStackTrace();
            Toast.makeText(this,"Unable to detect your location.",Toast.LENGTH_SHORT).show();
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

    /**
     * input validation
     */
    public void next(View view) {
        boolean isValid=true;

        //required field validator for all input field
        if(textInputEditText_name.getText().toString().isEmpty()){ //if field is empty
            textInputEditText_name.setError("Full Name cannot be empty!");//set error message
            textInputEditText_name.setFocusable(true);
            isValid=false; //validation is false
        }else if(textInputEditText_name.getText().toString().contentEquals(" ")){
            //validate white space
            textInputEditText_name.setError("No white space allowed.");
        }else{ textInputLayout_name.setErrorEnabled(false); } //validation correct,disable error msg

        if(textInputEditText_phone.getText().toString().isEmpty()){
            textInputEditText_phone.setError("Contact Number cannot be empty!");
            textInputEditText_phone.setFocusable(true);
            isValid=false;
        }else{ textInputLayout_phone.setErrorEnabled(false); }

        if(textInputEditText_nric.getText().toString().isEmpty()){
            textInputEditText_nric.setError("NRIC cannot be empty!");
            textInputEditText_nric.setFocusable(true);
            isValid=false;
        }else{ textInputLayout_nric.setErrorEnabled(false); }

        if(textInputEditText_nric_confirm.getText().toString().isEmpty()){
            textInputEditText_nric_confirm.setError("NRIC confirm cannot be empty!");
            textInputEditText_nric_confirm.setFocusable(true);
            isValid=false;
        }else{ textInputLayout_nric_confirm.setErrorEnabled(false); }

        if(textInputEditText_addressLine.getText().toString().isEmpty()){
            textInputEditText_addressLine.setError("Address Line cannot be empty!");
            textInputEditText_addressLine.setFocusable(true);
            isValid=false;
        }else{ textInputLayout_addressLine.setErrorEnabled(false); }

        if(textInputEditText_city.getText().toString().isEmpty()){
            textInputEditText_city.setError("City cannot be empty!");
            textInputEditText_city.setFocusable(true);
            isValid=false;
        }else{ textInputLayout_city.setErrorEnabled(false); }

        if(textInputEditText_postcode.getText().toString().isEmpty()){
            textInputEditText_postcode.setError("Postcode cannot be empty!");
            textInputEditText_postcode.setFocusable(true);
            isValid=false;
        }else{ textInputLayout_postcode.setErrorEnabled(false); }

        if(textInputEditText_state.getText().toString().isEmpty()){
            textInputEditText_state.setError("State cannot be empty!");
            textInputEditText_state.setFocusable(true);
            isValid=false;
        }else{ textInputLayout_state.setErrorEnabled(false); }

        //NRIC length validation
        if(textInputEditText_nric.getText().toString().trim().length()<12){
            textInputEditText_nric.setError("Your NRIC should be 12-digit!");
            textInputEditText_nric.setFocusable(true);
            isValid=false;
        }else{textInputLayout_nric.setErrorEnabled(false); }

        //NRIC confirm same value with NRIC
        if(textInputEditText_nric_confirm.getText().toString().
                contentEquals(textInputEditText_nric.getText().toString())) {
            //if both NRIC are same
            textInputLayout_nric_confirm.setErrorEnabled(false);
        }else{
            textInputEditText_nric_confirm.setError("NRIC mismatch!");
            textInputEditText_nric_confirm.setFocusable(true);
            isValid=false;
        }

        if(isValid){ //when passed validation
            //confirm information is all correct.
            //set all the input field not editable
            textInputEditText_name.setFocusable(false);
            textInputEditText_name.setFocusableInTouchMode(false);
            textInputEditText_name.setTextIsSelectable(false);
            textInputEditText_phone.setFocusable(false);
            textInputEditText_phone.setFocusableInTouchMode(false);
            textInputEditText_phone.setTextIsSelectable(false);
            textInputEditText_nric.setFocusable(false);
            textInputEditText_nric.setFocusableInTouchMode(false);
            textInputEditText_nric.setTextIsSelectable(false);
            textInputEditText_nric_confirm.setFocusable(false);
            textInputEditText_nric_confirm.setFocusableInTouchMode(false);
            textInputEditText_nric_confirm.setTextIsSelectable(false);
            textInputEditText_addressLine.setFocusable(false);
            textInputEditText_addressLine.setFocusableInTouchMode(false);
            textInputEditText_addressLine.setTextIsSelectable(false);
            textInputEditText_city.setFocusable(false);
            textInputEditText_city.setFocusableInTouchMode(false);
            textInputEditText_city.setTextIsSelectable(false);
            textInputEditText_postcode.setFocusable(false);
            textInputEditText_postcode.setFocusableInTouchMode(false);
            textInputEditText_postcode.setTextIsSelectable(false);
            textInputEditText_state.setFocusable(false);
            textInputEditText_state.setFocusableInTouchMode(false);
            textInputEditText_state.setTextIsSelectable(false);
            // If you have not set a click event before, you can omit it here
            imageButtonGetLocation.setOnClickListener(null);
            buttonNext.setText("Confirm");
            buttonCancel.setVisibility(View.VISIBLE);
        }
    }



    public void onRadioButtonClicked(View view) {
        boolean checked = ((RadioButton)view).isChecked();
        switch(view.getId()){
            case R.id.radioBtn_register_pfizer:
                if(checked)
                    //display chosen vaccine
                    displayToast(getString(R.string.text_pfizer_biontech));
                    //delivery = getString(R.string.text_sameday);
                break;
            case R.id.radioBtn_register_sinovac:
                if(checked)
                    //display chosen vaccine
                    displayToast(getString(R.string.text_sinovac_coronavac));
                    //delivery = getString(R.string.text_sameday);
                break;
            case R.id.radioBtn_register_AZ:
                if(checked)
                    //display chosen vaccine
                    displayToast(getString(R.string.text_astra_zeneca));
                    //delivery=getString(R.string.text_pickup);
                break;
            default:
                //do nothing
                break;
        }
    }

    public void displayToast(String message){
        Toast.makeText(getApplicationContext(),message,Toast.LENGTH_SHORT).show();
    }

    public void confirmation_cancel(View view) {
    }
}