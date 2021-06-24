package com.example.group14_vaccinationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

public class CovidWebsite extends AppCompatActivity {

    Toolbar toolbar;
    private TextView mWebsite1,mWebsite2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_website);

        toolbar=findViewById(R.id.toolbar);

        // set back button
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mWebsite1=findViewById(R.id.web_desc);
        mWebsite2=findViewById(R.id.web_desc_2);
    }

    @Override //when back button clicked
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void openWebsite(View view) {
        if (view.getId() == R.id.web_desc) {
            String url = mWebsite1.getText().toString();
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Log.d("Website:JKJAV", "Can't handle this!");
            }
        } else if (view.getId() == R.id.web_desc_2) {
            String url = mWebsite2.getText().toString();
            Uri webpage = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Log.d("Website:MOH", "Can't handle this!");
            }
        }
    }
}