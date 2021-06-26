package com.example.group14_vaccinationapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.text.TextUtils.split;

public class Registration extends AppCompatActivity {

    TextInputEditText EditText_address1, EditText_address2,EditText_area, EditText_postcode, EditText_state;
    ImageButton btnLocation;
    //FusedLocationProviderClient
    FusedLocationProviderClient fusedLocationProviderClient;
    String addressArea;

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
        EditText_address2=findViewById(R.id.et_register_address2);
        EditText_area = findViewById(R.id.et_register_area);
        EditText_postcode = findViewById(R.id.et_register_postcode);
        EditText_state = findViewById(R.id.et_register_state);
        btnLocation = findViewById(R.id.imgBtn_register_location);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
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
        if (ActivityCompat.checkSelfPermission(Registration.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                @Override
                public void onComplete(@NonNull Task<Location> task) {
                    //get result in a Location variable
                    Location location = task.getResult();
                    if(location!=null){
                        Geocoder geocoder = new Geocoder(Registration.this, Locale.getDefault());
                        try{
                            //get location by using latitude and longitude
                            List<Address> addressList = geocoder.getFromLocation(location.getLatitude(),
                                    location.getLongitude(), 1);
                            // split the full address by ,
                            String[] addressSplitList = split(addressList.get(0).getAddressLine(0),",");
                            // split again the address with only postcode and area using space
                            String[] addressSplitList2 = split(addressSplitList[3]," "); //eg:83000 batu pahat

                            String addressLine = addressSplitList[0]+addressSplitList[1]; //house no,street name
                            String addressLine2=addressSplitList[2];//taman xxx
                            String addressState=addressSplitList[4];//state

                            if(addressSplitList2[3]!=null){
                                //addressSplitList2[0] will be empty
                                //addressSplitList[1] will be postcode
                                //if area/city is two name
                                addressArea=addressSplitList2[2]+" "+addressSplitList2[3];
                            }else{
                                //if area/city is single name
                                addressArea=addressSplitList2[2];
                            }
                            // set the geographical address to the textInputLayout
                            EditText_address1.setText(addressLine);
                            EditText_address2.setText(addressLine2);
                            EditText_area.setText(addressArea);
                            EditText_state.setText(addressState);
                            EditText_postcode.setText(""+addressList.get(0).getPostalCode());
                        }catch(IOException e){
                            e.printStackTrace();
                        }
                    }else{
                        //if unable to detect location
                        Toast.makeText(Registration.this,"No location detected",Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            //request Location permission
            ActivityCompat.requestPermissions(Registration.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 100);
        }
    }

}