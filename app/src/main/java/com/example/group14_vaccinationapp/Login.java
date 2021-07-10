package com.example.group14_vaccinationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void goUserRegister(View view) { startActivity(new Intent(this,DeclarationVaccine.class)); }

    public void goAdminCreate(View view) {
        startActivity(new Intent(this,AdminCreateUser.class));
    }

    public void goAdminUpdate(View view) { startActivity(new Intent(this,AdminUpdateDelete.class)); }

    public void toStatusCallDb(View view) {
        startActivity(new Intent(this,VaccineRegisStatus.class));
    }
}