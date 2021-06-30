package com.example.group14_vaccinationapp;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class Validation extends Registration{

    TextInputEditText textInputEditText;
    TextInputLayout textInputLayout;
    boolean isValid=false;
    private static final Pattern phone_pattern=
            Pattern.compile("^(01)[0-46-9][0-9]{7,8}$");

    public Validation(){//constructor
//        this.textInputEditText=textInputEditText;
//        this.textInputLayout=textInputLayout;
    }

    public boolean requiredFieldValidation(TextInputEditText textInputEditText, TextInputLayout textInputLayout) {

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
        else{
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public boolean phoneRegexValidate(TextInputEditText textInputEditText, TextInputLayout textInputLayout){
        if(!phone_pattern.matcher(textInputEditText.getText().toString()).matches()){
            textInputEditText.setError("Contact Number is not valid!");
            textInputEditText.setFocusable(true);
            return false;
        }
        else{
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    public boolean matchValidate(TextInputEditText textInputEditText, TextInputEditText textInputEditTextConfirm,TextInputLayout textInputLayout){
        if(textInputEditTextConfirm.getText().toString().
                contentEquals(textInputEditText.getText().toString())) {
            //if both NRIC are same
            textInputLayout.setErrorEnabled(false);
            return true;
        }else{
            textInputEditText.setError("confirm NRIC mismatched!");
            textInputEditText.setFocusable(true);
            return false;
        }
    }

    public boolean lengthValidate(TextInputEditText textInputEditText, TextInputLayout textInputLayout){
        //NRIC length validation
        if(textInputEditText.getText().toString().trim().length()<12){
            textInputEditText.setError("Your NRIC should be 12-digit!");
            textInputEditText.setFocusable(true);
            return false;
        }else{
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }


}