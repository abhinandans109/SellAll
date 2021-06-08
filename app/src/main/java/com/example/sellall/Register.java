package com.example.sellall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

//import com.example.sellall.databinding.ActivityRegisterBinding;
import com.example.sellall.AdapterData.userdetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private FirebaseAuth f;
   private EditText email,pass;
   EditText name,cnfpss,mobo;
   FirebaseDatabase db;
   ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        f=FirebaseAuth.getInstance();
        email=findViewById(R.id.email);
        mobo=findViewById(R.id.number);
        cnfpss=findViewById(R.id.cnfpassword);
        name=findViewById(R.id.name);
        pass=findViewById(R.id.password);
        db=FirebaseDatabase.getInstance();
        findViewById(R.id.register).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(name.length()==0)name.setError("Enter Name");
                else if(email.length()==0)email.setError("Enter Email");
                else if(pass.length()==0)pass.setError("Enter Password");
                else if(cnfpss.length()==0)cnfpss.setError("Confirm password");
                else if(mobo.length()==0)mobo.setError("Enter Mobile Number");
                else if(!pass.getText().toString().equals(cnfpss.getText().toString()))cnfpss.setError("Passwords Do Not Match");
                else {
                    pd = new ProgressDialog(Register.this);
                    pd.setTitle("Creating Account");
                    pd.setMessage("Please wait!");
                    pd.show();
                    String mail, pas;
                    pas = pass.getText().toString();
                    mail = email.getText().toString();
                    f.createUserWithEmailAndPassword(mail, pas).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            pd.dismiss();
                            if (task.isSuccessful()) {
                                userdetails ud = new userdetails(((EditText) findViewById(R.id.name)).getText().toString(), ((EditText) findViewById(R.id.email)).getText().toString(), ((EditText) findViewById(R.id.password)).getText().toString(), ((EditText) findViewById(R.id.number)).getText().toString());
                                String id = task.getResult().getUser().getUid();
                                db.getReference().child("Users").child(id).setValue(ud);
                                Toast.makeText(Register.this, "Registration Sucessful", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(), LoggedInToApp.class));
                            } else {
                                Toast.makeText(Register.this, "Try Again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    public void signin(View view) {
        startActivity(new Intent(this,MainActivity.class));
    }
}
