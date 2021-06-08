package com.example.sellall.Adapters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.sellall.AdapterData.UserSellData;
import com.example.sellall.databinding.ActivityMyPostsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyPosts extends AppCompatActivity {
  private   RecyclerView recyclerView;
    private  ActivityMyPostsBinding b;
    private  DatabaseReference fd;
    private   FirebaseAuth fa;
    private UsersAdapter adapter;
    private  ArrayList<UserSellData> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b=ActivityMyPostsBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        list=new ArrayList<>();
        fa=FirebaseAuth.getInstance();
        recyclerView=b.recyclerview2;
        adapter=new UsersAdapter(this,list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        fd=FirebaseDatabase.getInstance().getReference("UserSellData").child(fa.getUid());
        fd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d:snapshot.getChildren()){
                    UserSellData u=d.getValue(UserSellData.class);
                    list.add(u);
                    adapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}