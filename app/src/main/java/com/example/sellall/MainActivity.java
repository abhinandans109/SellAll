package com.example.sellall;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.sellall.AdapterData.userdetails;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
     private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    FirebaseDatabase db;
    EditText email,pas;
    ProgressDialog pd;
    FirebaseAuth fa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        fa=FirebaseAuth.getInstance();
        if(fa.getUid()!=null){
            startActivity(new Intent(this,LoggedInToApp.class));
            finish();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        FirebaseUser u;
        mAuth=FirebaseAuth.getInstance();
        pd=new ProgressDialog(this);
        pd.setTitle("Logging In");
        pd.setMessage("Please Wait");
        db=FirebaseDatabase.getInstance();
        email=findViewById(R.id.id);
        pas=findViewById(R.id.pass);
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        ((ImageView)findViewById(R.id.google)).setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    public void signup(View view) {
        startActivity(new Intent(this,Register.class));


    }
    int RC_SIGN_IN=34;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void signIn() {
        pd.show();
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account.getIdToken());

            } catch (ApiException e) {

            }
        }
    }
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        pd.dismiss();
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            userdetails u=new userdetails();
                            u.setEmail(user.getEmail());
                            u.setName(user.getDisplayName());
                            u.setMobileno(user.getPhoneNumber());
                            u.setImages(user.getPhotoUrl().toString());
                            db.getReference().child("Users").child(fa.getUid()).setValue(u);
//                            updateUI(user);
                            startActivity(new Intent(getApplicationContext(),LoggedInToApp.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
//                            updateUI(null);
                        }
                    }
                });
    }
    public void phonelogin(View view) {
        startActivity(new Intent(this,phonelogin.class));
    }

    public void LoginClicked(View view){
        pd.show();
        if(email.length()==0) {
            email.setError("Enter email");
        }
        else if(pas.length()==0)pas.setError("Enter password");
        else {
            mAuth.signInWithEmailAndPassword(email.getText().toString(), pas.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    pd.dismiss();
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Login Sucessful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), LoggedInToApp.class));
                    } else {
                        Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}