package com.example.group14_vaccinationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class VaccinationMenu extends AppCompatActivity {
    private DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination_menu);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarVaccination);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override //when back button clicked
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void toDeclarationVaccine(View view) {
        dbHelper = new DatabaseHelper(this);
        if(!dbHelper.isBlank()) {
            Toast.makeText(getApplicationContext(),"You have already registered for vaccination",Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(this, DeclarationVaccine.class);
            startActivity(intent);
        }
    }

    public void toVaccineInfo(View view) {
        Intent intent = new Intent(getApplicationContext(),SelectVaccine.class);
        startActivity(intent);
    }

    public void toVaccineStatus(View view) {
        Intent intent = new Intent(this, VaccineRegisStatus.class);
        startActivity(intent);
    }
}