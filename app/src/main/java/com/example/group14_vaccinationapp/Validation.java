package com.example.group14_vaccinationapp;

import android.content.Context;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class Validation extends Registration{
    /**
     * This object java file is an object of Registration class.
     */
    TextInputEditText textInputEditText;
    TextInputLayout textInputLayout;
    boolean isValid=false;
    Context context;

    //Phone regex for Malaysia phone number pattern validation
    private static final Pattern phone_pattern=
            Pattern.compile("^(01)[0-46-9][0-9]{7,8}$");

    public Validation(Context context){//constructor
        this.context=context;
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
        }
        else{//no error occur
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public boolean phoneRegexValidate(TextInputEditText textInputEditText,
                                      TextInputLayout textInputLayout){
        //validate phone number follow the required pattern
        if(!phone_pattern.matcher(textInputEditText.getText().toString()).matches()){
            textInputEditText.setError("Contact Number is not valid!");
            textInputEditText.setFocusable(true);
            return false; //validation false
        }
        else{ // no error
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public boolean matchValidate(TextInputEditText textInputEditText,
                                 TextInputEditText textInputEditTextConfirm,TextInputLayout textInputLayoutConfirm){
        if(textInputEditTextConfirm.getText().toString().
                contentEquals(textInputEditText.getText().toString())) {
            //if both NRIC and password are same
            textInputLayoutConfirm.setErrorEnabled(false);
            return true;
        }else{
            textInputEditTextConfirm.setError("This field mismatched!");//set error message
            textInputEditText.setFocusable(true);
            return false;
        }
    }

    public boolean lengthValidate(TextInputEditText textInputEditText,
                                  TextInputLayout textInputLayout){
        //NRIC length validation
        if(textInputEditText.getText().toString().trim().length()<12){ //less than 12 digit
            textInputEditText.setError("Your NRIC should be 12-digit!"); //set error
            textInputEditText.setFocusable(true);
            return false;
        }else{//no error
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public boolean checkUserExist(TextInputEditText textInputEditText,TextInputLayout textInputLayout){
        String ic = textInputEditText.getText().toString();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        if(dbHelper.isIC_Exist(ic)) {
            textInputLayout.setError("This NRIC has been registered.");
            textInputEditText.setFocusable(true);
            return true;//user exist
        }else {
            textInputLayout.setErrorEnabled(false); //new user
            return false;
        }
    }
}