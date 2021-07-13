package com.example.group14_vaccinationapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class CovidWebsite extends AppCompatActivity {
    Toolbar toolbar;
    private TextView mWebsite1, mWebsite2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covid_website);

        toolbar = findViewById(R.id.toolbar_CovidWebsite);

        // set back button
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override //when back button clicked
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    public void openWebsiteJKJAV(View view) {
        // get website url
        mWebsite1 = findViewById(R.id.label_website_jkjav);
        String url = mWebsite1.getText().toString();
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("Implicit Intent", "Can't handle this!");
        }
    }

    public void openWebsiteMOH(View view) {
        //get website url
        mWebsite2 = findViewById(R.id.label_website_moh);
        String url2 = mWebsite2.getText().toString();
        Uri webpage2 = Uri.parse(url2);
        Intent intent2 = new Intent(Intent.ACTION_VIEW, webpage2);
        if (intent2.resolveActivity(getPackageManager()) != null) {
            startActivity(intent2);
        } else {
            Log.d("Implicit Intent", "Can't handle this!");
        }
    }

    public void toVideo(View view) {
        startActivity(new Intent(CovidWebsite.this, VideoAnimation.class));
    }

    public void toAudio(View view) {
        try {
            startActivity(new Intent(CovidWebsite.this, Audio.class));
        } catch (Exception e) {
            Toast.makeText(CovidWebsite.this, e.toString(), Toast.LENGTH_SHORT).show();
        }

    }
}