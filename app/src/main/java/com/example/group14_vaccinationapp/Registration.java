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
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class Registration extends AppCompatActivity implements LocationListener{

    TextInputEditText EditText_address1,EditText_area, EditText_postcode, EditText_state;
    ImageButton btnLocation;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        //set back button
        Toolbar toolbar = findViewById(R.id.toolbarRegistration);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        EditText_address1 = findViewById(R.id.et_register_address);
        EditText_area = findViewById(R.id.et_register_area);
        EditText_postcode = findViewById(R.id.et_register_postcode);
        EditText_state = findViewById(R.id.et_register_state);
        btnLocation = findViewById(R.id.imgBtn_register_location);
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
}