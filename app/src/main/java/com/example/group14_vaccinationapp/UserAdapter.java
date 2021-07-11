package com.example.group14_vaccinationapp;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    //extends the adapter for array user list
    Context context;//to store the current content
    int layoutRes; //layout resource
    List<User> userList;
    DatabaseHelper dbHelper;

    public UserAdapter(Context context, int layoutRes, List<User> userList, DatabaseHelper dbHelper) {
        super(context, layoutRes, userList);
        this.context = context;
        this.layoutRes = layoutRes;
        this.userList = userList;
        this.dbHelper = dbHelper;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //create new layout using inflater
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutRes, null);
        //get Ready to fill up text for list view Layout
        //from list_layout_user.xml
        TextView tv_IC = view.findViewById(R.id.tv_list_IC);
        TextView tv_name = view.findViewById(R.id.tv_list_name);
        TextView tv_age = view.findViewById(R.id.tv_list_age);
        TextView tv_phone = view.findViewById(R.id.tv_list_phone);
        TextView tv_address = view.findViewById(R.id.tv_list_address);
        TextView tv_status = view.findViewById(R.id.tv_list_status);
        TextView tv_notes = view.findViewById(R.id.tv_list_notes);
        TextView tv_vaccineID = view.findViewById(R.id.tv_list_vaccineID);

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

        /*
         * List view's edit button clicked
         * Trigger alert dialog
         */
        view.findViewById(R.id.btnEditUser).setOnClickListener(v -> {
            updateUser(user);//pass the array
        });

        /*
         * List view's delete button clicked
         * Trigger alert dialog to confirm delete
         */
        view.findViewById(R.id.btnDeleteUser).setOnClickListener(v -> {
            deleteUser(user);//pass the array user list
        });

        return view;
    }

    /*
     * ListView -> Delete icon button
     */
    private void deleteUser(final User user) {
        //create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Are you sure to delete this user?");
        builder.setMessage("NRIC: " + user.getIc() + "\nName: " + user.getName());
        builder.setPositiveButton("Yes", (dialog, which) -> {
            //delete user from database
            if (dbHelper.deleteUser(user.getIc()))
                loadUserFromDatabaseAgain();
            Toast.makeText(context.getApplicationContext(), "User Deleted", Toast.LENGTH_SHORT).show();
        });
        builder.setNegativeButton("Cancel", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void loadUserFromDatabaseAgain() {
        //load all user in the database
        Cursor cursor = dbHelper.getAllUser();
        userList.clear();//clear the existing array list

        //load data from database
        //then store into the User Array List
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
        notifyDataSetChanged(); //notify the User Array List to refresh
    }

    /*
     * List View -> Update icon button
     * Display dialog_update_user
     * @param user array list
     */
    private void updateUser(final User user) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_update_user, null);
        builder.setView(view);

        TextView errorMessage = view.findViewById(R.id.tv_errorMessage_inDialog);
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        final TextInputLayout textInputLayout_nric = view.findViewById(R.id.Layout_adminUpdate_nric);
        final TextInputLayout textInputLayout_name = view.findViewById(R.id.Layout_adminUpdate_name);
        final TextInputLayout textInputLayout_age = view.findViewById(R.id.Layout_adminUpdate_age);
        final TextInputLayout textInputLayout_phone = view.findViewById(R.id.Layout_adminUpdate_phone);
        final TextInputLayout textInputLayout_address = view.findViewById(R.id.Layout_adminUpdate_address);
        final TextInputLayout textInputLayout_status = view.findViewById(R.id.Layout_adminUpdate_status);
        final TextInputLayout textInputLayout_notes = view.findViewById(R.id.Layout_adminUpdate_notes);

        final TextInputEditText et_nric = view.findViewById(R.id.et_adminUpdate_nric);
        final TextInputEditText et_name = view.findViewById(R.id.et_adminUpdate_name);
        final TextInputEditText et_age = view.findViewById(R.id.et_adminUpdate_age);
        final TextInputEditText et_phone = view.findViewById(R.id.et_adminUpdate_phone);
        final TextInputEditText et_address = view.findViewById(R.id.et_adminUpdate_address);
        final TextInputEditText et_status = view.findViewById(R.id.et_adminUpdate_status);
        final TextInputEditText et_notes = view.findViewById(R.id.et_adminUpdate_notes);
        final Spinner spinner = view.findViewById(R.id.spinner_AdminUpdate_vaccineType);

        et_nric.setText(user.getIc());
        et_name.setText(user.getName());
        et_age.setText(user.getAge());
        et_phone.setText(user.getPhone());
        et_address.setText(user.getAddress());
        et_status.setText(user.getStatus());
        et_notes.setText(user.getNotes());

        //when user click the calender icon
        view.findViewById(R.id.iv_adminUpdate_calander).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance(); //get Calendar instance according to TimeZone in Java runtime environment
                int year = calendar.get(Calendar.YEAR); //get current Year
                int month = calendar.get(Calendar.MONTH); // get current Month
                int day = calendar.get(Calendar.DAY_OF_MONTH); //get current Day

                //create a Date Picker Dialog (pop out)
                DatePickerDialog picker = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // When date set/selected
                        //concat the selected year,month,dayofMonth into a string
                        String date = dayOfMonth + " / " + (month+1) + " / " + year;
                        //set the selected date into the editText
                        et_status.setText(String.valueOf("First/Two dose: " + date));
                    }
                }, year, month, day); //A calendar with year,month and day
                // System.currentTimeMillis() -> returns the current time in milliseconds
                // '-1000'
                picker.getDatePicker().setMinDate(System.currentTimeMillis()-1000);
                picker.show();
            }
        });


        /*
         * When user clicks 'update' button in the alert dialog
         */
        view.findViewById(R.id.btnUpdateUser).setOnClickListener(new View.OnClickListener() {
            Boolean valid = false;//validation flag
            String vaccineID; //to store the vaccineID from spinner

            @Override
            public void onClick(View v) {
                String name = et_name.getText().toString(),
                        ic = et_nric.getText().toString(),
                        age = et_age.getText().toString(),
                        phone = et_phone.getText().toString(),
                        address = et_address.getText().toString(),
                        status = et_status.getText().toString(),
                        notes = et_notes.getText().toString();

                //validation
                Validation validation = new Validation(context);
                if (validation.requiredFieldValidation(et_nric, textInputLayout_nric) &&
                        validation.lengthValidate(et_nric, textInputLayout_nric) &&
                        validation.requiredFieldValidation(et_name, textInputLayout_name) &&
                        validation.requiredFieldValidation(et_age, textInputLayout_age) &&
                        validation.ageValidate(et_age, textInputLayout_age) &&
                        validation.requiredFieldValidation(et_phone, textInputLayout_phone) &&
                        validation.phoneRegexValidate(et_phone, textInputLayout_phone) &&
                        validation.requiredFieldValidation(et_address, textInputLayout_address) &&
                        validation.requiredFieldValidation(et_status, textInputLayout_status) &&
                        validation.requiredFieldValidation(et_notes, textInputLayout_notes)) {

                    //validate selected spinner
                    String selected = (String) spinner.getSelectedItem();
                    if (selected.contentEquals("Please select a vaccine")) {
                        errorMessage.setText("Please select a vaccine for user.");
                        errorMessage.setVisibility(View.VISIBLE);
                        valid = false;
                    } else {
                        vaccineID = String.valueOf(spinner.getSelectedItemPosition());
                        valid = true;
                    }

                    //IF Pass all the validation
                    if (valid) {
                        try { // Update the data
                            if (dbHelper.updateUser(ic, name, age, phone, address, status, notes, vaccineID)) {
                                Toast.makeText(context, "User Updated.", Toast.LENGTH_SHORT).show();
                                loadUserFromDatabaseAgain();
                                alertDialog.dismiss(); //close the alert dialog
                            }
                        } catch (Exception e) { //Error updating the database
                            e.printStackTrace();
                            Toast.makeText(context, "Something Went Wrong!Please try later.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        /*
         * WHEN user clicks on ' delete' button
         * System close the update alert dialog
         */
        view.findViewById(R.id.btnCancel_adminUpdate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();//close the alert dialog
            }
        });
    }


    //below is to show what date picked
    public static void processDatePickerResult(int year, int month, int day) {
        //The month integer returned by the date picker starts
        // counting at 0 for January, so you need to add 1 to it
        // to show months starting at 1.
        String month_string = Integer.toString(month + 1);
        String day_string = Integer.toString(day);
        String year_string = Integer.toString(year);
        String dateMessage = (month_string + "/" + day_string + "/" + year_string);

    }
}
