package com.example.group14_vaccinationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

public class VaccinationMenu extends AppCompatActivity {
    private UserInfo userInfo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination_menu);

        setUserInfo();

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
        Intent intent = new Intent(this, DeclarationVaccine.class);
        startActivity(intent);
    }

    public void toVaccineInfo(View view) {
        Intent intent = new Intent(getApplicationContext(),SelectVaccine.class);
        startActivity(intent);
    }

    public void toVaccineStatus(View view) {
        Intent intent = new Intent(this, VaccineRegisStatus.class);
        startActivity(intent);
    }

    public void setUserInfo() {
        userInfo = (UserInfo) getIntent().getSerializableExtra("userInfo");
    }
}