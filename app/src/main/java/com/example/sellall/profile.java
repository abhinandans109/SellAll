package com.example.sellall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.sellall.databinding.ActivityProfileBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

public class profile extends AppCompatActivity {
    ActivityProfileBinding b;
    FirebaseAuth fa;
    FirebaseDatabase fd;
    Uri ur;
    StorageReference storage;
    FirebaseStorage fb;
    ProgressDialog pd,pd1,pd2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        b=ActivityProfileBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        fd=FirebaseDatabase.getInstance();
        setContentView(b.getRoot());
        fa=FirebaseAuth.getInstance();
        fb=FirebaseStorage.getInstance();
        storage=FirebaseStorage.getInstance().getReference("usersData");
        pd=new ProgressDialog(this);
        pd2=new ProgressDialog(this);

        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);

        pd2.setCancelable(false);
        pd2.setCanceledOnTouchOutside(false);
        pd.setTitle("Please Wait");
        pd.setMessage("Loading");
        pd2.setTitle("Please Wait");
        pd2.setMessage("Loding Profile");
//        pd2.show();
//        pd.show();
        updateprofile();

    }

    private void updateprofile() {

        fd.getReference().child("Users").
                child(fa.getCurrentUser().getUid().toString()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("mobileno")){
                    fd.getReference().child("Users").
                            child(fa.getCurrentUser().getUid().toString()).child("mobileno").
                            addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    b.mobileno.setHint(snapshot.getValue().toString());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }else{
                    fd.getReference().child("Users").
                            child(fa.getCurrentUser().getUid().toString()).child("mobileno").setValue("Add phone");
                }

                if(snapshot.hasChild("name")){
                    fd.getReference().child("Users").
                            child(fa.getCurrentUser().getUid().toString()).child("name").
                            addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    b.num1.setHint(snapshot.getValue().toString());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }else{
                    fd.getReference().child("Users").
                            child(fa.getCurrentUser().getUid().toString()).child("name").setValue("Add name");
                } if(snapshot.hasChild("email")){
                    fd.getReference().child("Users").
                            child(fa.getCurrentUser().getUid().toString()).child("email").
                            addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    b.profileEmail.setHint(snapshot.getValue().toString());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }else{
                    fd.getReference().child("Users").
                            child(fa.getCurrentUser().getUid().toString()).child("email").setValue("Add email");
                } if(snapshot.hasChild("address")){
                    fd.getReference().child("Users").
                            child(fa.getCurrentUser().getUid().toString()).child("address").
                            addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    b.address.setHint(snapshot.getValue().toString());
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }else{
                    fd.getReference().child("Users").
                            child(fa.getCurrentUser().getUid().toString()).child("address").setValue("Add address");
                }

                if(snapshot.hasChild("images")){
                    fd.getReference().child("Users").
                            child(fa.getCurrentUser().getUid().toString()).child("images").
                            addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    fd.getReference().child("Users").child(fa.getCurrentUser().getUid().toString())
                                            .child("images").addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            Picasso.get().load(snapshot.getValue().toString()).into(b.profilepic);
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                }else{
                    b.profilepic.setImageResource(R.drawable.pp);
                }
                pd2.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void saveClicked(View view) {
        if(!(b.mobileno.getHint().equals("Add phone"))&& b.mobileno.length()>0&&b.mobileno.length()!=10){
            b.mobileno.setError("Enter a Valid mobile no");
        }
        else if(b.mobileno.length()==0&& b.mobileno.getHint().equals("Add phone")){
            b.mobileno.setError("Enter mobile no");
        }else{
            pd1=new ProgressDialog(this);
            pd1.setTitle("Udating User Detail");
            pd1.setMessage("please Wait");
            pd1.show();
            DatabaseReference db=fd.getReference().child("Users").child(fa.getCurrentUser().getUid().toString());
            if(b.num1.length()==0) {
               db.child("name").setValue(b.num1.getHint());
           }
           else {
               db.child("name").setValue(b.num1.getText().toString());
           }
            if(b.profileEmail.length()!=0) {
               db.child("email").setValue(b.profileEmail.getText().toString());
           }
           else {
               db.child("email").setValue(b.profileEmail.getHint());
           }
            if(b.address.length()!=0) {
               db.child("address").setValue(b.address.getText().toString());
           }
           else {
               db.child("address").setValue(b.address.getHint());
           }
           if(b.mobileno.length()==0&& !(b.mobileno.getHint().equals("Add phone"))) {
               db.child("mobileno").setValue(b.mobileno.getHint()).addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                       pd1.dismiss();
                       startActivity(new Intent(getApplicationContext(), LoggedInToApp.class));
                   }
               });
           }else{
               db.child("mobileno").setValue(b.mobileno.getText().toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                   @Override
                   public void onSuccess(Void aVoid) {
                       pd1.dismiss();
                       startActivity(new Intent(getApplicationContext(), LoggedInToApp.class));
                   }
               });
           }
        }

    }

    public void immageclaicked(View view) {

        Intent in=new Intent(Intent.ACTION_GET_CONTENT);
        in.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true);
        in.setType("image/*");
        startActivityForResult(in,1);


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        pd.show();
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==RESULT_OK){
            if(data.getClipData()!=null){
                Toast.makeText(this, "Select single image", Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
            else {
                Uri u = data.getData();
                ur = u;
                StorageReference st = storage.child(fa.getCurrentUser().getUid().toString());
                st.putFile(ur).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                        st.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                fd.getReference().child("Users").child(fa.getCurrentUser().getUid().toString()).child("images").setValue(uri.toString()).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        DatabaseReference u = fd.getReference().child("Users").child(fa.getCurrentUser().getUid().toString()).child("images");
                                        u.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                // Toast.makeText(profile.this, , Toast.LENGTH_SHORT).show();
                                                Picasso.get().load(snapshot.getValue().toString()).into(b.profilepic);
                                                fd.getReference().child("Users").child(fa.getCurrentUser().getUid().toString()).child("images").setValue(snapshot.getValue().toString());
                                                pd.dismiss();

                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError error) {

                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }

                });
            }
        }
    }
    private String getex(Uri u){
        ContentResolver c=getContentResolver();
        MimeTypeMap mine=MimeTypeMap.getSingleton();
        return mine.getExtensionFromMimeType(c.getType(ur));
    }

}