package com.example.group14_vaccinationapp;

import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

public class AdminUpdateDelete extends AppCompatActivity {

    List<User> userList;
    ListView listView;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update_delete);

        dbHelper = new DatabaseHelper(this);
        userList = new ArrayList<>();
        listView = (ListView) findViewById(R.id.listview_UserList);
        loadUserFromDatabase();

        //set back button
        Toolbar toolbar = findViewById(R.id.toolbar_adminUpdateDel);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        EditText searchQuery = findViewById(R.id.et_search_AdminUpdateDel);
        searchQuery.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override //searchQuery_editText
            public void afterTextChanged(Editable s) {
                //while user is entering the NRIC,searching is ongoing...
                searchUserBy(searchQuery.getText().toString());
            }
        });
    }

    private void searchUserBy(String clause) {
        //search all the user that fits with clause(ic)
        //using the function from DatabaseHelper
        Cursor cursor = dbHelper.searchUserBy(clause);
        userList.clear();//clear the array list
        if (cursor.moveToFirst()) {
            do {
                userList.add(new User( //add the retrieve item from cursor into array list
                        cursor.getString(0),//ic
                        cursor.getString(1),//name
                        //cursor.getString(2),//password
                        cursor.getString(3),//age
                        cursor.getString(4),//phone
                        cursor.getString(5),//address
                        cursor.getString(6),//status
                        cursor.getString(7),//notes
                        //cursor.getString(8),//isAdmin
                        cursor.getString(9)
                ));
            } while (cursor.moveToNext());
        }
        //pass the database, LayoutResource,user array list to Adapter
        UserAdapter userAdapter = new UserAdapter(AdminUpdateDelete.this, R.layout.list_layout_user, userList, dbHelper);
        listView.setAdapter(userAdapter);
    }

    @Override //when back button clicked
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    private void loadUserFromDatabase() {
        //load all user in the database
        Cursor cursor = dbHelper.getAllUser();
        //load data from database
        //then store into the User Class
        if (cursor.moveToFirst()) {
            do {
                userList.add(new User(
                        cursor.getString(0),//ic
                        cursor.getString(1),//name
                        //cursor.getString(2),//password
                        cursor.getString(3),//age
                        cursor.getString(4),//phone
                        cursor.getString(5),//address
                        cursor.getString(6),//status
                        cursor.getString(7),//notes
                        //cursor.getString(8),//isAdmin
                        cursor.getString(9)
                ));
            } while (cursor.moveToNext());
        }

        //pass the database, LayoutResource,user array list to Adapter
        UserAdapter userAdapter = new UserAdapter(this, R.layout.list_layout_user, userList, dbHelper);
        listView.setAdapter(userAdapter);
    }


}