package com.example.group14_vaccinationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivityAdmin extends AppCompatActivity {
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.group14_vaccinationapp";

    // Key for current NRIC
    private final String NRICPreference = "NRIC";
    // Key for current isAdmin
    private final String isAdminPreference = "isAdmin";
    TextView txtWelcomeMsg;
    private DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        String getNRICPreference = mPreferences.getString(NRICPreference, null);
        String getisAdminPreference = mPreferences.getString(isAdminPreference, null);

        txtWelcomeMsg = findViewById(R.id.txt_mainAdmin_welcomeMsg);


        txtWelcomeMsg.setText();
    }

    public void logout(View view) {
        // calling method to edit values in shared prefs.
        SharedPreferences.Editor editor = mPreferences.edit();

        // below line will clear
        // the data in shared prefs.
        editor.clear();

        // below line will apply empty
        // data to shared prefs.
        editor.apply();

        // starting mainactivity after
        // clearing values in shared preferences.
        Intent i = new Intent(MainActivityAdmin.this, Login.class);
        startActivity(i);
        finish();
    }
}