package com.example.group14_vaccinationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class DeclarationVaccine extends AppCompatActivity {
    //private  toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_declaration_vaccine);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarDeclare);
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

    public void isCheckedcbxAgreement(View view) {
        CheckBox checkBox = findViewById(R.id.cbxAgreement);
        Button btnSubmit = findViewById(R.id.btnSubmitDeclare);

        btnSubmit.setEnabled(checkBox.isChecked());
    }

    public void toQuiz(View view) {
        Intent intent = new Intent(this, Quiz.class);
        startActivity(intent);
    }
}