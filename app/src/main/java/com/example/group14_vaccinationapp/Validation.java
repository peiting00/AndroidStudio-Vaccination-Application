package com.example.group14_vaccinationapp;

import android.content.Context;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class Validation extends Registration {
    /**
     * This object java file is an object of Registration class.
     */
    TextInputEditText textInputEditText;
    TextInputLayout textInputLayout;
    boolean isValid = false;
    Context context;

    //Phone regex for Malaysia phone number pattern validation
    private static final Pattern phone_pattern =
            Pattern.compile("^(01)[0-46-9][0-9]{7,8}$");

    public Validation(Context context) {//constructor
        this.context = context;
    }

    public boolean requiredFieldValidation(TextInputEditText textInputEditText,
                                           TextInputLayout textInputLayout) {

        //validate empty field
        if (textInputEditText.getText().toString().isEmpty()) {
            textInputEditText.setError("This field cannot be empty!");//set error message
            textInputEditText.setFocusable(true);
            return false; //validation is false
        } // validate white space
        else if (textInputEditText.getText().toString().contentEquals(" ")) {
            textInputEditText.setError("No whitespace allowed!");//set error message
            textInputEditText.setFocusable(true);
            return false; //validation is false
        } else {//no error occur
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public boolean phoneRegexValidate(TextInputEditText textInputEditText,
                                      TextInputLayout textInputLayout) {
        //validate phone number follow the required pattern
        if (!phone_pattern.matcher(textInputEditText.getText().toString()).matches()) {
            textInputEditText.setError("Contact Number is not valid!");
            textInputEditText.setFocusable(true);
            return false; //validation false
        } else { // no error
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public boolean matchValidate(TextInputEditText textInputEditText,
                                 TextInputEditText textInputEditTextConfirm, TextInputLayout textInputLayoutConfirm) {
        if (textInputEditTextConfirm.getText().toString().
                contentEquals(textInputEditText.getText().toString())) {
            //if both NRIC and password are same
            textInputLayoutConfirm.setErrorEnabled(false);
            return true;
        } else {
            textInputEditTextConfirm.setError("This field mismatched!");//set error message
            textInputEditText.setFocusable(true);
            return false;
        }
    }

    public boolean lengthValidate(TextInputEditText textInputEditText,
                                  TextInputLayout textInputLayout) {
        //NRIC length validation
        if (textInputEditText.getText().toString().trim().length() < 12) { //less than 12 digit
            textInputEditText.setError("Your NRIC should be 12-digit!"); //set error
            textInputEditText.setFocusable(true);
            return false;
        } else {//no error
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public boolean ageValidate(TextInputEditText textInputEditText,
                                                    TextInputLayout textInputLayout) {
        //Parse the string age to int
        int age = Integer.parseInt(textInputEditText.getText().toString());
        if (age < 0) { //age less than 0
            textInputEditText.setError("Age should not be less than 0"); //set error
            textInputEditText.setFocusable(true);
            return false; //validate fail
        }else if(age>120){
            textInputEditText.setError("Age should not be more than 120"); //set error
            textInputEditText.setFocusable(true);
            return false; //validate fail
        }else {//no error
            textInputLayout.setErrorEnabled(false);//disable the error message
            return true;
        }
    }

    //check if the user is exist in the database
    //pass the parameters editText and InputLayout into here
    public boolean checkUserExist(TextInputEditText textInputEditText,
                                                TextInputLayout textInputLayout) {
        //convert the EditText to String
        String ic = textInputEditText.getText().toString();
        //create a databaseHelper object
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        //inherit DatabaseHelper's function -isIC_Exist
        if (dbHelper.isIC_Exist(ic)) { //pass the IC to the function
            //if the validation passed (user exist)
            textInputEditText.setError("Your NRIC has been registered.");//set error
            textInputEditText.setFocusable(true);
            return true;//user exist
        } else {
            textInputLayout.setErrorEnabled(false); //they are new user
            return false;
        }
    }
}