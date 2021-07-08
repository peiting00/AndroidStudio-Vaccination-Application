package com.example.group14_vaccinationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import android.database.sqlite.SQLiteDatabase;

public class VaccineRegisStatus extends AppCompatActivity {
    private DatabaseHelper dbHelper;
    private TextView txtName, txtIC, txtAge, txtPhone, txtAddress, txtNotes, txtVaccinePrefer, txtVaccineStatus;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine_regis_status);

        Toolbar toolbar = findViewById(R.id.toolbarRegisStatus);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        txtName = findViewById(R.id.txtReadName);
        txtIC = findViewById(R.id.txtReadIC);
        txtAge = findViewById(R.id.txtReadAge);
        txtPhone = findViewById(R.id.txtReadPhone);
        txtAddress = findViewById(R.id.txtReadAddress);
        txtNotes = findViewById(R.id.txtReadNotes);
        txtVaccinePrefer = findViewById(R.id.txtReadVaccinePrefer);
        txtVaccineStatus = findViewById(R.id.txtReadVaccineStatus);
        try{
            dbHelper = new DatabaseHelper(this);
            String name = "", IC = "000000000001", age = "", phone = "", address = "", notes = "",status="";

                Cursor cursor = dbHelper.readInfo(IC);
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