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
            String name = "", IC = "", age = "", phone = "", address = "", notes = "", vaccinePrefer = "";
            if(!dbHelper.isBlank()){
                Cursor cursor = dbHelper.readInfo();

                name = cursor.getString(cursor.getColumnIndex("name"));
                txtName.setText(name);

                IC = cursor.getString(cursor.getColumnIndex("IC"));
                txtIC.setText(IC);

                age = cursor.getString(cursor.getColumnIndex("age"));
                txtAge.setText(age);

                phone = cursor.getString(cursor.getColumnIndex("phone"));
                txtPhone.setText(phone);

                address = cursor.getString(cursor.getColumnIndex("address"));
                txtAddress.setText(address);

                notes = cursor.getString(cursor.getColumnIndex("notes"));
                txtNotes.setText(notes);

                vaccinePrefer = cursor.getString(cursor.getColumnIndex("vaccinePrefer"));
                txtVaccinePrefer.setText(vaccinePrefer);

                txtVaccineStatus.setText("Pending");
            }else{
                txtVaccineStatus.setText("You have not registered for your vaccine yet");
            }
        }catch(Exception e){
            txtVaccineStatus.setText(e.toString());

            //db.execSQL("CREATE TABLE user (ID INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, IC INTEGER, age INTEGER, phone TEXT, address TEXT, notes TEXT, vaccinePrefer TEXT)");
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