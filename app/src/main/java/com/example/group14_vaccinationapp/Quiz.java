package com.example.group14_vaccinationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class Quiz extends AppCompatActivity {
    private CardView cvQues1Expand;
    private CardView cvQues2Expand;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Toolbar toolbar = findViewById(R.id.toolbarQuiz);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        cvQues1Expand = findViewById(R.id.cvQues1Expand);
        cvQues2Expand = findViewById(R.id.cvQues2Expand);
    }

    @Override //when back button clicked
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void toVaccineRegis(View view) {
        TextView txtError = findViewById(R.id.txtErrorMsgQ1);
        txtError.setText("Wrong! Wrong! Wrong! Absolutely Wrong! Your concept totally wrong!");
//        Intent intent = new Intent(this, Registration.class);
//        startActivity(intent);
    }

    public void onCheckQues1(View view) {
        switch (view.getId()){
            case R.id.rdBtnQues1Yes:
                cvQues1Expand.setVisibility(View.GONE);
                break;
            case R.id.rdBtnQues1No:
                cvQues1Expand.setVisibility(View.VISIBLE);
                break;
        }
    }

    public void onCheckQues2(View view) {
        switch (view.getId()){
            case R.id.rdBtnQues2Yes:
                cvQues2Expand.setVisibility(View.VISIBLE);
                break;
            case R.id.rdBtnQues2No:
                cvQues2Expand.setVisibility(View.GONE);
                break;
        }
    }
}