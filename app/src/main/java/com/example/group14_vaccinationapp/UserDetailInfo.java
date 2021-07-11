package com.example.group14_vaccinationapp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class UserDetailInfo extends AppCompatActivity {
    DatabaseHelper dbHelper;
    private TextView txtName, txtIC, txtAge, txtPhone, txtAddress, txtNotes, txtVaccinePrefer, txtVaccineStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail_info);

        Toolbar toolbar = findViewById(R.id.toolbar_UserDetails);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        String selectedNRIC = intent.getStringExtra("SelectedNRIC");

        ActionBar getToolBar = getSupportActionBar();
        getToolBar.setTitle(selectedNRIC + " Details");



        txtName = findViewById(R.id.txt_userDetails_ReadName);
        txtIC = findViewById(R.id.txt_userDetails_ReadIC);
        txtAge = findViewById(R.id.txt_userDetails_ReadAge);
        txtPhone = findViewById(R.id.txt_userDetails_ReadPhone);
        txtAddress = findViewById(R.id.txt_userDetails_ReadAddress);
        txtNotes = findViewById(R.id.txt_userDetails_ReadNotes);
        txtVaccinePrefer = findViewById(R.id.txt_userDetails_ReadVaccinePrefer);
        txtVaccineStatus = findViewById(R.id.txt_userDetails_ReadVaccineStatus);
        try{

            String name = "", IC = "000000000001", age = "", phone = "", address = "", notes = "",status="";

            dbHelper = new DatabaseHelper(this);
            Cursor cursor = dbHelper.readInfo(selectedNRIC);
            cursor.moveToFirst();

            name = cursor.getString(cursor.getColumnIndex("name"));
            txtName.setText(name);

            IC = cursor.getString(cursor.getColumnIndex("ic"));
            txtIC.setText(IC);

            age = cursor.getString(cursor.getColumnIndex("age"));
            txtAge.setText(age);

            phone = cursor.getString(cursor.getColumnIndex("phone"));
            txtPhone.setText(phone);

            address = cursor.getString(cursor.getColumnIndex("address"));
            txtAddress.setText(address);

            notes = cursor.getString(cursor.getColumnIndex("notes"));
            txtNotes.setText(notes);

            int vaccineID = Integer.parseInt(cursor.getString(cursor.getColumnIndex("vaccineID")));

            switch(vaccineID){
                case 1:
                    txtVaccinePrefer.setText(getResources().getString(R.string.text_pfizer_biontech));
                    break;
                case 2:
                    txtVaccinePrefer.setText(getResources().getString(R.string.text_sinovac_coronavac));
                    break;
                case 3:
                    txtVaccinePrefer.setText(getResources().getString(R.string.text_astra_zeneca));
                    break;
            }
            status = cursor.getString(cursor.getColumnIndex("status"));
            txtVaccineStatus.setText(status);

        }catch(Exception e){
            txtVaccineStatus.setText(e.toString());
        }
    }

    @Override //when back button clicked
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}