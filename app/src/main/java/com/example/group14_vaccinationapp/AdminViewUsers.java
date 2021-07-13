package com.example.group14_vaccinationapp;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class AdminViewUsers extends AppCompatActivity {
    private String NRICs[];
    private String names[];
    DatabaseHelper dbHelper;

    RecyclerView recycler_AdminView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_users);

        Toolbar toolbar = findViewById(R.id.toolbar_adminView);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        dbHelper = new DatabaseHelper(this);

        recycler_AdminView = findViewById(R.id.recycleV_adminViewUser);

        int countUsers = dbHelper.getUserNum();
        NRICs = new String[countUsers];
        names = new String[countUsers];
        loadUserFromDatabase();

        UserRecycleViewAdapter adapter = new UserRecycleViewAdapter(this, NRICs, names);
        recycler_AdminView.setAdapter(adapter);
        recycler_AdminView.setLayoutManager(new LinearLayoutManager(this));

        EditText searchQuery = findViewById(R.id.et_search_AdminView);
        searchQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchUserBy(searchQuery.getText().toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override //when back button clicked
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void searchUserBy(String clause) {
        Cursor cursor = dbHelper.searchUserBy(clause);
        NRICs = new String[cursor.getCount()];
        names = new String[cursor.getCount()];

        returnRead(cursor);

        UserRecycleViewAdapter adapter = new UserRecycleViewAdapter(this, NRICs, names);
        recycler_AdminView.setAdapter(adapter);
        recycler_AdminView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void loadUserFromDatabase() {
        //load all user in the database
        Cursor cursor = dbHelper.getAllUser();
        //load data from database
        //then store into the User Class
        returnRead(cursor);
    }

    private Cursor returnRead(Cursor cursor) {
        int i = 0, j = 0;
        if (cursor.moveToFirst()) {
            do {
                NRICs[i++] = cursor.getString(cursor.getColumnIndex("ic"));
                names[j++] = cursor.getString(cursor.getColumnIndex("name"));
            } while (cursor.moveToNext());
        }
        return cursor;
    }
}