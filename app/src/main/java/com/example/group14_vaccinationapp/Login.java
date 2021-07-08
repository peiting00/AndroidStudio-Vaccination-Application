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

    public void GoUserRegister(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }

    public void GoAdminCreate(View view) {
        startActivity(new Intent(this,AdminCreateUser.class));
    }

    public void GoAdminUpdate(View view) {
        startActivity(new Intent(this,AdminUpdateDelete.class));
    }
}