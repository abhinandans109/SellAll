package com.example.sellall;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.sellall.AdapterData.CommentsData;
import com.example.sellall.AdapterData.UserSellData;
import com.example.sellall.Adapters.CommentsAdapter;
import com.example.sellall.Adapters.UsersAdapter;
import com.example.sellall.databinding.ActivityCommentsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class comments extends AppCompatActivity {
    ActivityCommentsBinding b;
    FirebaseAuth fa;
    DatabaseReference db;
    DatabaseReference db1;
    ArrayList<CommentsData> list;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b=ActivityCommentsBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        list=new ArrayList<>();
        db1=FirebaseDatabase.getInstance().getReference("Users");
        fa=FirebaseAuth.getInstance();
        Intent i=getIntent();
        recyclerView=b.comentrecyclerview;
        UserSellData u=i.getParcelableExtra("data");
        db= FirebaseDatabase.getInstance().getReference("UserSellData").
        child(i.getStringExtra("uid")).child(i.getStringExtra("postid"));
        CommentsAdapter adapter=new CommentsAdapter(this,list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("comments")){
                    db.child("comments").addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                            for(DataSnapshot d:snapshot.getChildren()){
                                CommentsData c= new CommentsData();
                                db1.child(snapshot.getKey()).child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        String name=snapshot.getValue(String.class);
                                        c.setCommentname(name);
                                        adapter.notifyDataSetChanged();
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                                String s= d.getValue(String.class);
                                c.setCommentmessage(s);
//                                Toast.makeText(comments.this, c.getCommentname()+" "+c.getCommentmessage(), Toast.LENGTH_SHORT).show();
                                 list.add(c);
                                adapter.notifyDataSetChanged();
//
                            }
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        b.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference dbref=db.child("comments").child(fa.getUid()).getRef();
                db.child("comments").child(fa.getUid()).child(String.valueOf(System.currentTimeMillis())).setValue(b.commentText.getText().toString());
                b.commentText.setText("");
            }
        });

    }
}