package com.example.group14_vaccinationapp;


import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
//extends the adapter for array user list
    Context context;
    int layoutRes;
    List<User> userList;
    DatabaseHelper dbHelper;


    public UserAdapter(Context context,int layoutRes,List<User> userList,DatabaseHelper dbHelper){
        super(context,layoutRes,userList);
        this.context=context;
        this.layoutRes=layoutRes;
        this.userList=userList;
        this.dbHelper=dbHelper;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        //create new layout using inflater
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutRes,null);
        //get Ready to fill up text for list view Layout
        //from list_layout_user.xml
        TextView tv_IC=view.findViewById(R.id.tv_list_IC);
        TextView tv_name=view.findViewById(R.id.tv_list_name);
        TextView tv_age=view.findViewById(R.id.tv_list_age);
        TextView tv_phone=view.findViewById(R.id.tv_list_phone);
        TextView tv_address=view.findViewById(R.id.tv_list_address);
        TextView tv_status=view.findViewById(R.id.tv_list_status);
        TextView tv_notes=view.findViewById(R.id.tv_list_notes);
        TextView tv_vaccineID=view.findViewById(R.id.tv_list_vaccineID);

        final User user = userList.get(position);
        //set the textview in the Layout using User array list
        tv_IC.setText(user.getIc());
        tv_name.setText(user.getName());
        tv_age.setText(user.getAge());
        tv_phone.setText(user.getPhone());
        tv_address.setText(user.getAddress());
        tv_status.setText(user.getStatus());
        tv_notes.setText(user.getNotes());
        tv_vaccineID.setText(user.getVaccineID());

        view.findViewById(R.id.btnEditUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUser(user);//pass the array
            }
        });

        view.findViewById(R.id.btnDeleteUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteUser(user);//pass the array
            }
        });

        return view ;
    }

    private void deleteUser(final User user) {

    }

    private void updateUser(final User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        //View view = inflater.inflate();
    }
}
