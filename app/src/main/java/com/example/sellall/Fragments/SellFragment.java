package com.example.sellall.Fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sellall.LoggedInToApp;
import com.example.sellall.AdapterData.UserSellData;
import com.example.sellall.databinding.FragmentSellBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class SellFragment extends Fragment {
    FragmentSellBinding b;
    FirebaseAuth fa;
    ArrayList<String> a;
    StorageReference s;
    ProgressDialog pd;
    DatabaseReference fd;
    DatabaseReference fd1;
    private int count=0;
    private int pos=0;
    private static final int RC=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        a = new ArrayList<>();
        b = FragmentSellBinding.inflate(inflater, container, false);
        pd = new ProgressDialog(getContext());
        pd.setMessage("Uploading images please wait");
        pd.setCancelable(false);
        pd.setCanceledOnTouchOutside(false);
        fa = FirebaseAuth.getInstance();
//        fd1=FirebaseDatabase.getInstance().getReference("UserBuyData").child(fa.getUid());
        fd = FirebaseDatabase.getInstance().getReference("UserSellData").child(fa.getUid()).child(String.valueOf(System.currentTimeMillis()));
        s = FirebaseStorage.getInstance().getReference("UserSellDataImages").child(fa.getUid());
        b.itemimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                pd.show();
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, RC);

            }
        });

        b.itempost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Creating Post");
                if (b.itemnName.length() == 0) b.itemnName.setError("");
                else if (b.itemQuantity.length() == 0) b.itemQuantity.setError("");
                else if (b.itemContactNo.length() == 0) b.itemContactNo.setError("");
                else if (b.itemContactNo.length() != 10) b.itemContactNo.setError("Enter a valid Mobile number" );
                else if (b.itemAddress.length() == 0) b.itemAddress.setError("");
                else if (count <2) {
                    Toast.makeText(getContext(), "Add Images", Toast.LENGTH_SHORT).show();
                }
                else if (b.itemPincode.length() == 0) b.itemPincode.setError("");
                else {
                    UserSellData u = new UserSellData(b.itemnName.getText().toString(), b.itemQuantity.getText().toString(), b.itemContactNo.getText().toString(), b.itemAddress.getText().toString(), b.itemPincode.getText().toString(), a);
                    fd.setValue(u).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            pd.dismiss();
                            Toast.makeText(getContext(), "Post Created Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getContext(), LoggedInToApp.class));
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getContext(),e.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        return b.getRoot();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        pd.show();
        if(requestCode==RC && resultCode== Activity.RESULT_OK) {
            b.itemimage.setImageURI(data.getData());
            if(data.getClipData()!=null){

                ClipData u = data.getClipData();
                count = data.getClipData().getItemCount();

                for (int i = 0; i < count; i++) {
                    StorageReference s1 = s.child(String.valueOf(System.currentTimeMillis()));
                    int finalI = i;
                    s1.putFile(u.getItemAt(i).getUri()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            s1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    a.add(uri.toString());
                                    if (finalI == count - 1) {
                                        pd.dismiss();
                                        Toast.makeText(getContext(), "Images Uploaded Sucessfully", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }


            }else {
                count=5;
                pd.setTitle("Please Wait");
                pd.setMessage("Uploading Image ");
//                Toast.makeText(getContext(), "Elese", Toast.LENGTH_SHORT).show();
                StorageReference s1 = s.child(String.valueOf(System.currentTimeMillis()));
                s1.putFile(data.getData()).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                     s1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                         @Override
                         public void onSuccess(Uri uri) {
                             a.add(uri.toString());
                             pd.dismiss();

                         }
                     }) ;
                    }
                });

            }

        }else {
            pd.dismiss();
            Toast.makeText(getContext(), "Select images", Toast.LENGTH_SHORT).show();
        }

    }
}