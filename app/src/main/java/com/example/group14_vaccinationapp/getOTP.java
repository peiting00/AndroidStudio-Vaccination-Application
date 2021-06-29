package com.example.group14_vaccinationapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;


public class getOTP extends AppCompatActivity {


    ProgressBar progressBar;
    String verificationOTPBySystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_otp);
        progressBar=findViewById(R.id.progressbar_verify_otp);
        String phone = getIntent().getStringExtra("phone");
        sendOTPtoUser(phone);
    }


    public void sendOTPtoUser(String phone){

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
             "+6"+phone,     // Phone number to verify
             60,            //Timeout duration
             TimeUnit.SECONDS, // Timeout and unit
             this,                // Activity (for callback binding)
             mCallbacks);        // OnVerificationStateChangedCallbacks
    }


    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent( String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationOTPBySystem = s;
            //if the phone can support reCaptcha, OTP code will not working.
            //redirect to firebase verify not robot
            Toast.makeText(getOTP.this,"Account Verified!",Toast.LENGTH_SHORT).show();
            Runnable r = new Runnable() {

                @Override
                public void run() {
                    // if you are redirecting from a fragment then use getActivity() as the context.
                    Intent intent = new Intent(getOTP.this, RegistrationSuccess.class);
                    startActivity(intent);
                    finish();//user cannot switch back
                }
            };

            Handler h = new Handler();
            // The Runnable will be executed after the given delay time
            h.postDelayed(r, 2000); // delay 3 sec
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code!=null){
                //make the progressBar visible
                progressBar.setVisibility(View.VISIBLE);
                verifyOTP(code);
            }

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(getOTP.this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyOTP(String verificationOTPByUser){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationOTPBySystem,verificationOTPByUser);
        signInUserByCredentials(credential);
    }

    private void signInUserByCredentials(PhoneAuthCredential credential) {

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(getOTP.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Intent intent = new Intent(getApplicationContext(),RegistrationSuccess.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        }else{
                            //if OTP is entered wrongly
                            Toast.makeText(getOTP.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}