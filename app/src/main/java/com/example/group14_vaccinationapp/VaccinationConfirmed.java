package com.example.group14_vaccinationapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VaccinationConfirmed extends AppCompatActivity {

    private TextView name,phone,address,NRIC,vaccine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccination_confirmed);

        name=findViewById(R.id.etName);
        phone=findViewById(R.id.et_phone);
        address=findViewById(R.id.et_address);
        NRIC=findViewById(R.id.et_NRIC);
        vaccine=findViewById(R.id.et_vaccine);

        Bundle confirmInfo = getIntent().getExtras();
        name.setText(confirmInfo.getString("name"));
        phone.setText(confirmInfo.getString("phone"));
        address.setText(confirmInfo.getString("address"));
        NRIC.setText(confirmInfo.getString("NRIC"));
        vaccine.setText(confirmInfo.getString("vaccine"));

        Runnable r = new Runnable() {

            @Override
            public void run() {
                // if you are redirecting from a fragment then use getActivity() as the context.
                Intent intent2 = new Intent(getApplicationContext(), RegistrationSuccess.class);
                startActivity(intent2);
                finish();//user cannot switch back
            }
        };

        Handler h = new Handler();
        // The Runnable will be executed after the given delay time
        h.postDelayed(r, 3000); // delay 3 sec
    }
}