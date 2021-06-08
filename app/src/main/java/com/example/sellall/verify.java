package com.example.sellall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class   verify extends AppCompatActivity {
    FirebaseAuth mAuth;
    String mVerificationId;
    EditText ed;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_verify);
        mAuth=FirebaseAuth.getInstance();
        Intent in=getIntent();
        ed=findViewById(R.id.num1);
        pd= new ProgressDialog(this);
        String phoneNumber=in.getStringExtra("number");
//        Toast.makeText(this, phoneNumber, Toast.LENGTH_SHORT).show();
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber("+91"+phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        pd.setTitle("Logging In");
        pd.setMessage("Please Wait");
    }
    public void veri(View view) {
        pd.show();
        String code=ed.getText().toString();
        VerifyCode(code);
    }


        PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                mVerificationId=s;
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                pd.dismiss();
               VerifyCode(mVerificationId);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(verify.this, "Failed", Toast.LENGTH_SHORT).show();
            }
        };




    private void VerifyCode(String code) {
        if(ed.length()==0)ed.setError("Check otp");
        else {
            PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(mVerificationId, code);
            mAuth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
//                Toast.makeText(verify.this, code, Toast.LENGTH_SHORT).show();
                    pd.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "Login Successfull", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoggedInToApp.class));
                    } else
                        Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }



}