package com.example.group14_vaccinationapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

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
        userList=new ArrayList<>();
        listView = (ListView)findViewById(R.id.listview_UserList);
        loadUserFromDatabase();

    }

    private void loadUserFromDatabase() {
        //load all user in the database
        Cursor cursor = dbHelper.getAllUser();
        //load data from database
        //then store into the User Class
        if(cursor.moveToFirst()){
            do{
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
            }while(cursor.moveToNext());
        }

        //pass the database, LayoutResource,user array list to Adapter
        UserAdapter userAdapter = new UserAdapter(this,R.layout.list_layout_user,userList,dbHelper);
        listView.setAdapter(userAdapter);

    }
}