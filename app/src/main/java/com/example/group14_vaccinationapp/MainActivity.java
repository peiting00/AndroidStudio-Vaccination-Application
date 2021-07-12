package com.example.group14_vaccinationapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.group14_vaccinationapp.databinding.ActivityMainBinding;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private static final String TAG = MainActivity.class.getSimpleName();
    private TextView txtUserNameBar;

    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.group14_vaccinationapp";

    // Key for current NRIC
    private final String NRICPreference = "NRIC";
    // Key for current isAdmin
    private final String isAdminPreference = "isAdmin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        NavigationView navigationView1 = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView1.getHeaderView(0);
        txtUserNameBar = (TextView) headerView.findViewById(R.id.txtUserNameBar);

        DatabaseHelper dbHelper ;

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);

        String getNRICPreference = mPreferences.getString(NRICPreference, null);
        String getisAdminPreference = mPreferences.getString(isAdminPreference, null);

        try{
            dbHelper = new DatabaseHelper(this);

                Cursor cursor = dbHelper.readInfo(getNRICPreference);
            if (cursor.moveToFirst()) {
                String name = cursor.getString(1);
                txtUserNameBar.setText(name);
            }
        }catch(Exception e){
            txtUserNameBar.setText("User");
        }

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);

                // Set the data for the intent as the phone number.
                intent.setData(Uri.parse("tel:123456789"));

                // If the intent resolves to a package (app),
                // start the activity with the intent.
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                } else {
                    Log.d(TAG, "ImplicitIntents: Can't handle this!");
                }
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}