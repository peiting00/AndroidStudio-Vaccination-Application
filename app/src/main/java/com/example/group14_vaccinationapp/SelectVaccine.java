package com.example.group14_vaccinationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

public class SelectVaccine extends AppCompatActivity {
    //declaration
    private TextView info_pfizer,info_sinovac,info_az;
    private ImageButton imgbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_vaccine);

        //set back button
        Toolbar toolbar = findViewById(R.id.toolbarVaccineInfor);
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

    /**
     * Pfizer vaccine
     * When user clicked the up/down arrow
     */
    public void pfizer(View view) {
        info_pfizer=findViewById(R.id.info_vaccine_pfizer);
        imgbtn=findViewById(R.id.imageButton);

        if(info_pfizer.getVisibility()==View.GONE){ //if info not visible
            //set info visible
            info_pfizer.setVisibility(View.VISIBLE);
            //change the image arrow-downwards
            imgbtn.setImageResource(R.drawable.ic_arrow_up);
        }else{ //if info visible
            //set info gone
            info_pfizer.setVisibility(View.GONE);
            //change the image arrow-upwards
            imgbtn.setImageResource(R.drawable.ic_arrow_down);
        }
    }

    /**
     * Sinovac vaccine
     * When user clicked the up/down arrow
     */
    public void sinovac(View view) {
        info_sinovac=findViewById(R.id.info_vaccine_sinovac);
        imgbtn=findViewById(R.id.imageButton2);
        if(info_sinovac.getVisibility()==View.GONE){ //if info not visible
            //set info visible
            info_sinovac.setVisibility(View.VISIBLE);
            //change the image arrow-downwards
            imgbtn.setImageResource(R.drawable.ic_arrow_up);
        }else{ //if info visible
            //set info gone
            info_sinovac.setVisibility(View.GONE);
            //change the image arrow-upwards
            imgbtn.setImageResource(R.drawable.ic_arrow_down);
        }
    }

    /**
     * Astra Zeneca vaccine
     * When user clicked the up/down arrow
     */
    public void az(View view) {
        info_az=findViewById(R.id.info_vaccine_az);
        imgbtn=findViewById(R.id.imageButton3);
        if(info_az.getVisibility()==View.GONE){ //if info not visible
            //set info visible
            info_az.setVisibility(View.VISIBLE);
            //change the image arrow-downwards
            imgbtn.setImageResource(R.drawable.ic_arrow_up);
        }else{ //if info visible
            //set info gone
            info_az.setVisibility(View.GONE);
            //change the image arrow-upwards
            imgbtn.setImageResource(R.drawable.ic_arrow_down);
        }
    }
}