package com.example.group14_vaccinationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivityAdmin extends AppCompatActivity {
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.group14_vaccinationapp";
    private String getNRICPreference;
    private String getisAdminPreference;
    // Key for current NRIC
    private final String NRICPreference = "NRIC";
    // Key for current isAdmin
    private final String isAdminPreference = "isAdmin";
    TextView txtWelcomeMsg, txtNumOfRegistered;
    private DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        getNRICPreference = mPreferences.getString(NRICPreference, null);
        getisAdminPreference = mPreferences.getString(isAdminPreference, null);

        txtWelcomeMsg = findViewById(R.id.txt_mainAdmin_welcomeMsg);
        txtNumOfRegistered = findViewById(R.id.txt_mainAdmin_numRegis);


        dbHelper = new DatabaseHelper(this);
        Cursor cursor = dbHelper.readInfo(getNRICPreference);
        cursor.moveToFirst();
        String adminName = cursor.getString(cursor.getColumnIndex("name"));

        int registeredNum = dbHelper.getUserNum();

        txtNumOfRegistered.setText(Integer.toString(registeredNum));
        txtWelcomeMsg.setText("Welcome! admin " + adminName);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        dbHelper = new DatabaseHelper(this);
        Cursor cursor = dbHelper.readInfo(getNRICPreference);
        cursor.moveToFirst();
        //String adminName = cursor.getString(cursor.getColumnIndex("name"));

        int registeredNum = dbHelper.getUserNum();

        txtNumOfRegistered.setText(Integer.toString(registeredNum));
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

    public void toUserManagement(View view) {
        if(getisAdminPreference.equals("1")){
            startActivity(new Intent(MainActivityAdmin.this, UserManagementMenu.class));
        }else{
            Toast.makeText(MainActivityAdmin.this, "You have no access to this feature",Toast.LENGTH_SHORT).show();
        }

    }

    public void toCovidNews(View view) {
        startActivity(new Intent(MainActivityAdmin.this, CovidWebsite.class));
    }
}