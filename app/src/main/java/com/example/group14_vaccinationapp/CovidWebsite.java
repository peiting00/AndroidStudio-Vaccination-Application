package com.example.group14_vaccinationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
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

        toolbar=findViewById(R.id.toolbar_CovidWebsite);

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


    public void openWebsiteJKJAV(View view) {
        // get website url
        mWebsite1=findViewById(R.id.label_website_jkjav);
        String url = mWebsite1.getText().toString();

        Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
        intent.putExtra(SearchManager.QUERY,url); // 'url' is the query
        if(intent.resolveActivity(getPackageManager())!=null){
            startActivity(intent);
        }

    }

    public void openWebsiteMOH(View view) {
        //get website url
        mWebsite2=findViewById(R.id.label_website_moh);
        String url2 = mWebsite2.getText().toString();

        Intent intent2 = new Intent(Intent.ACTION_WEB_SEARCH);
        intent2.putExtra(SearchManager.QUERY,url2); // 'url' is the query
        if(intent2.resolveActivity(getPackageManager())!=null){
            startActivity(intent2);
        }

    }
}