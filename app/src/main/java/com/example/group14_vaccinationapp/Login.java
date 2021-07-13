package com.example.group14_vaccinationapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    private EditText edLoginIC;
    private EditText edLoginPassword;
    private Button btnLogin;
    private TextView txtErrorIC, txtErrorPass;
    private DatabaseHelper dbHelper;
    private SharedPreferences mPreferences;
    private String sharedPrefFile = "com.example.android.group14_vaccinationapp";

    // Key for current NRIC
    private final String NRICPreference = "NRIC";
    // Key for current isAdmin
    private final String isAdminPreference = "isAdmin";
    private final String passwordPreference = "password";

    Boolean edLoginICValid = false, edPasswordValid = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edLoginIC = findViewById(R.id.ed_Login_NRIC);
        edLoginPassword = findViewById(R.id.ed_Login_Password);
        btnLogin = findViewById(R.id.btn_Login_login);
        txtErrorIC = findViewById(R.id.txt_login_errorMsgNRIC);
        txtErrorPass = findViewById(R.id.txt_login_errorMsgPassword);

        mPreferences = getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        String getNRICPreference = mPreferences.getString(NRICPreference, null);
        String getisAdminPreference = mPreferences.getString(isAdminPreference, null);
        String getPasswordPreference = mPreferences.getString(passwordPreference, null);

        if(getNRICPreference != null && getPasswordPreference != null){
            if(getisAdminPreference.equals("1")){
                startActivity(new Intent(this,MainActivityAdmin.class));
            }else{
                startActivity(new Intent(this,MainActivity.class));
            }
        }

        edLoginIC.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try{
                    String ICCheck = "";
                    ICCheck = edLoginIC.getText().toString();
                    if(ICCheck.isEmpty()){
                        txtErrorIC.setText("Required");
                        edLoginICValid = false;
                    }
                    else{
                        txtErrorIC.setText("");
                        edLoginICValid = true;
                    }
                }catch(Exception e) {
                    txtErrorIC.setText(e.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        edLoginPassword.addTextChangedListener(new TextWatcher() {
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String passCheck = "";
                passCheck = edLoginPassword.getText().toString();
                if(passCheck.isEmpty()){
                    txtErrorPass.setText("Required");
                    edPasswordValid = false;
                }
                else{
                    txtErrorPass.setText("");
                    edPasswordValid = true;
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.finishAffinity();
    }

    public void goUserRegister(View view) { startActivity(new Intent(this,DeclarationVaccine.class)); }

    public void goAdminCreate(View view) {
        startActivity(new Intent(this,AdminCreateUser.class));
    }

    public void goAdminUpdate(View view) { startActivity(new Intent(this,AdminUpdateDelete.class)); }

    public void toStatusCallDb(View view) {
        startActivity(new Intent(this,VaccineRegisStatus.class));
    }

    public void login(View view) {
        if(edLoginICValid && edPasswordValid){
            String edNRIC = edLoginIC.getText().toString();
            String edPassword = edLoginPassword.getText().toString();

            try{
                dbHelper = new DatabaseHelper(this);

                if(dbHelper.isIC_Exist(edNRIC)){
                    Cursor cursor = dbHelper.readInfo(edNRIC);
                    cursor.moveToFirst();
                    String passwordDb = cursor.getString(cursor.getColumnIndex("password"));
                    String isAdmin = cursor.getString(cursor.getColumnIndex("isAdmin"));

                    if(!edPassword.equals(passwordDb)){
                        Toast.makeText(Login.this, "Invalid NRIC or password!",Toast.LENGTH_SHORT).show();
                    }else{
                        SharedPreferences.Editor editor = mPreferences.edit();

                        editor.putString(NRICPreference, edNRIC);
                        editor.putString(isAdminPreference, isAdmin);
                        editor.putString(passwordPreference, edPassword);
                        editor.commit();

                        if(isAdmin.equals("1")){
                            startActivity(new Intent (this, MainActivityAdmin.class));
                            finish();
                        }else{
                            startActivity(new Intent (this, MainActivity.class));
                            finish();
                        }
                    }
                }else{
                    Toast.makeText(Login.this, "Invalid NRIC or password!",Toast.LENGTH_SHORT).show();
                }

            }catch(Exception e){
                Toast.makeText(Login.this,e.toString() ,Toast.LENGTH_SHORT).show(); //R.string.thing_went_wrong
            }
        }
        else{
            Toast.makeText(Login.this,"Please make sure every credential is filled in correctly",Toast.LENGTH_SHORT).show();
        }
    }
}