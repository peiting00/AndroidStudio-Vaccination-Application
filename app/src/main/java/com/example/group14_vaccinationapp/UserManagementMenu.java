package com.example.group14_vaccinationapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableRow;

public class UserManagementMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_management_menu);

        Toolbar toolbar = findViewById(R.id.toolbar_UserManageMenu);
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

    public void adminOperation(View view){
       switch (view.getId()){
           case R.id.tabRow_userManage_createUser:Users:
               startActivity(new Intent(UserManagementMenu.this, AdminCreateUser.class));
               break;
           case R.id.tabRow_userManage_viewAllUsers:
               startActivity(new Intent(UserManagementMenu.this, AdminViewUsers.class));
               break;
           case R.id.tabRow_userManage_manageUsers:
               startActivity(new Intent(UserManagementMenu.this, AdminUpdateDelete.class));
        }
    }
}