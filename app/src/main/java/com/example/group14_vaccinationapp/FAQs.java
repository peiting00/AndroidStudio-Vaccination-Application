package com.example.group14_vaccinationapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class FAQs extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faqs);

        Toolbar toolbar = findViewById(R.id.toolbarFAQs);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        TextView textView = findViewById(R.id.txtFAQsA3);
        textView.setClickable(true);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        String text = "COVID-19 is thought to spread mainly through close contact from person to person, including between people who are physically near each other (within about 6 feet). People who are infected but do not show symptoms can also spread the virus to others. Cases of reinfection with COVID-19 have been reported but are rare. We are still learning about how the virus spreads and the severity of illness it causes.\n" +
                "        \n\nCOVID-19 spreads very easily from person to person. How easily a virus spreads from person to person can vary. The virus that causes COVID-19 appears to spread more efficiently than influenza but not as efficiently as measles, which is among the most contagious viruses known to affect people.\n" +
                "        \n\nFor more information about how COVID-19 spreads, visit the <a href = 'https://www.cdc.gov/coronavirus/2019-ncov/prevent-getting-sick/how-covid-spreads.html'>How COVID-19 Spreads page</a> to learn how COVID-19 spreads and how to protect yourself.";
        textView.setText(Html.fromHtml(text));

        TextView textView02 = findViewById(R.id.txtFAQsA4);
        textView02.setClickable(true);
        textView02.setMovementMethod(LinkMovementMethod.getInstance());
        text = "Visit the <a href = 'https://www.cdc.gov/coronavirus/2019-ncov/prevent-getting-sick/prevention.html'>How to Protect Yourself & Others</a> page to learn about how to protect yourself from respiratory illnesses, like COVID-19.";
        textView02.setText(Html.fromHtml(text));

        TextView textView03 = findViewById(R.id.txtClickMoreHos);
        textView03.setClickable(true);
        textView03.setMovementMethod(LinkMovementMethod.getInstance());
        text = "<a href = 'http://covid-19.moh.gov.my/hotline'>Click here for more</a>";
        textView03.setText(Html.fromHtml(text));
    }
    @Override //when back button clicked
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()== android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void toHospital(View view) {
        ImageView btnHospital = (ImageView)view;
        String loc = btnHospital.getContentDescription().toString();
        Uri addressUri = Uri.parse("geo:0,0?q=" + loc);
        Intent intent = new Intent(Intent.ACTION_VIEW, addressUri);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            Log.d("ImplicitIntents", "Can't handle this intent!");
        }
    }
}