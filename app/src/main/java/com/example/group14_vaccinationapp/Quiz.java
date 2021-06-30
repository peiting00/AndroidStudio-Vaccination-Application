package com.example.group14_vaccinationapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

public class Quiz extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.android.twoactivities.extra.MESSAGE";
    private CardView cvQues1Expand;
    private CardView cvQues2Expand;
    //private RadioGroup rdGroup1;
    private TextView txtErrorQ1, txtErrorQ2, txtErrorQ3, txtErrorQ4, txtErrorQ5, txtErrorQ6;
    private int cbxQ1CheckedAmount, cbxQ2CheckedAmount;
    private EditText edAgeQuiz;

    Boolean q1Valid = false, q2Valid = false, q3Valid = false, q4Valid = false, q5Valid = false, q6Valid = false;

    int q2Result = 0, q4Result = 0, q5Result = 0;
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
        //rdGroup1 = findViewById(R.id.rdGroup1);
        txtErrorQ1 = findViewById(R.id.txtErrorMsgQ1);
        txtErrorQ2 = findViewById(R.id.txtErrorMsgQ2);
        txtErrorQ3 = findViewById(R.id.txtErrorMsgQ3);
        txtErrorQ4 = findViewById(R.id.txtErrorMsgQ4);
        txtErrorQ5 = findViewById(R.id.txtErrorMsgQ5);
        txtErrorQ6 = findViewById(R.id.txtErrorMsgQ6);

        cbxQ1CheckedAmount = 0; cbxQ2CheckedAmount = 0;

        edAgeQuiz = findViewById(R.id.edAgeQuiz);
        edAgeQuiz.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    String ageCheck = "";
                    ageCheck = edAgeQuiz.getText().toString();
                    if(ageCheck.isEmpty()){
                        txtErrorQ6.setText("Required");
                        q6Valid = false;
                    }
                    else{
                        if(Integer.parseInt(ageCheck) < 0 || Integer.parseInt(ageCheck) > 130){
                            txtErrorQ6.setText("Invalid age");
                            q6Valid = false;
                        }else{
                            txtErrorQ6.setText("");
                            q6Valid = true;
                        }
                    }
                }catch(Exception e) {
                    txtErrorQ6.setText(e.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    public void setCbxQ1CheckedAmount(int choice){
        cbxQ1CheckedAmount = (choice == 1) ? cbxQ1CheckedAmount + 1 : cbxQ1CheckedAmount - 1;
    }

    public void setCbxQ2CheckedAmount(int choice){
        cbxQ2CheckedAmount = (choice == 1) ? cbxQ2CheckedAmount + 1 : cbxQ2CheckedAmount - 1;
    }


    public int getCbxQ1CheckedAmount(){
        return  cbxQ1CheckedAmount;
    }

    public int getCbxQ2CheckedAmount(){
        return  cbxQ2CheckedAmount;
    }


    public Boolean getFinalValidationQuiz(){
        return q1Valid && q2Valid && q3Valid && q4Valid && q5Valid && q6Valid;
    }

    @Override //when back button clicked
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void onCheckQues1(View view) {
        switch (view.getId()){
            case R.id.rdBtnQues1Yes:
                cvQues1Expand.setVisibility(View.GONE);
                txtErrorQ1.setText("");
                q1Valid = true;
                break;
            case R.id.rdBtnQues1No:
                cvQues1Expand.setVisibility(View.VISIBLE);
                checkIfCheckBoxQ1Checked();
                break;
        }
    }

    public void onCheckQues2(View view) {
        switch (view.getId()){
            case R.id.rdBtnQues2Yes:
                cvQues2Expand.setVisibility(View.VISIBLE);
                checkIfCheckBoxQ2Checked();
                q2Result = 1;
                break;
            case R.id.rdBtnQues2No:
                cvQues2Expand.setVisibility(View.GONE);
                txtErrorQ2.setText("");
                q2Valid = true;
                q2Result = 0;
                break;
        }
    }

    public void onCheckQues3(View view) {
        txtErrorQ3.setText("");
        q3Valid = true;
    }

    public void onCheckQues4(View view) {
        txtErrorQ4.setText("");
        q4Valid = true;

        switch (view.getId()){
            case R.id.rdBtnQues4Yes:
                q4Result = 1;
                break;
            case R.id.rdBtnQues4No:
                q4Result = 0;
                break;
        }
    }

    public void onCheckQues5(View view) {
        txtErrorQ5.setText("");
        q5Valid = true;

        switch (view.getId()){
            case R.id.rdBtnQues5Yes:
                q5Result = 1;
                break;
            case R.id.rdBtnQues5No:
                q5Result = 0;
                break;
        }
    }

    public void onCheckQues1Expand(View view) {
        CheckBox isChecked = findViewById(view.getId());
        setCbxQ1CheckedAmount((isChecked.isChecked()) ? 1 : 2);

        checkIfCheckBoxQ1Checked();
    }

    public void onCheckQues2Expand(View view) {
        CheckBox isChecked = findViewById(view.getId());
        setCbxQ2CheckedAmount((isChecked.isChecked()) ? 1 : 2);

        checkIfCheckBoxQ2Checked();
    }

    public void checkIfCheckBoxQ1Checked(){
        if(getCbxQ1CheckedAmount() == 0){
            txtErrorQ1.setText("Please select your reason");
            q1Valid = false;
        }
        else{
            txtErrorQ1.setText("");
            q1Valid = true;
        }
    }

    public void checkIfCheckBoxQ2Checked(){
        if(getCbxQ2CheckedAmount() == 0){
            txtErrorQ2.setText("Please select comordibities you are having");
            q2Valid = false;
        }
        else{
            txtErrorQ2.setText("");
            q2Valid = true;
        }
    }

    public void toVaccineRegis(View view) {
        if(getFinalValidationQuiz()){
            int insuitableRateForVaccine = q2Result + q4Result + q5Result;
            String msgUnsuitableStatus = (insuitableRateForVaccine > 1) ? "You have risk in taking COVID-19 Vaccination, Please kindly have a consultation with doctor." :
                    "You are advisable to take COVID-19 vaccination";

            Toast.makeText(Quiz.this,msgUnsuitableStatus,Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, Registration.class);
            intent.putExtra("age", edAgeQuiz.getText().toString());
            intent.putExtra("notes", msgUnsuitableStatus);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(Quiz.this,"Please make sure every question is answered correctly",Toast.LENGTH_SHORT).show();
        }
    }
}