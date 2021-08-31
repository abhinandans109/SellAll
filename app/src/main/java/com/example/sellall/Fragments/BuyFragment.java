package com.example.sellall.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.sellall.Adapters.ImageAdapter;
import com.example.sellall.Adapters.UsersAdapter;
import com.example.sellall.ImageClicked;
import com.example.sellall.R;
import com.example.sellall.AdapterData.UserSellData;
import com.example.sellall.databinding.FragmentBuyBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class BuyFragment extends Fragment {
    RecyclerView recyclerView;
    FragmentBuyBinding b;
    DatabaseReference fd;
    FirebaseAuth fa;
    DatabaseReference fd1;
    SwipeRefreshLayout s;
    UsersAdapter adapter;
    ArrayList<UserSellData> list;
    ProgressDialog pd;

    public void callcalled(String contactno) {
        Intent i = new Intent(Intent.ACTION_DIAL);
        String p = "tel:" + contactno;
        i.setData(Uri.parse(p));
        startActivity(i);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        pd=new ProgressDialog(getContext());

        // Inflate the layout for this fragment
        b =FragmentBuyBinding.inflate(inflater, container, false);
           fd= FirebaseDatabase.getInstance().getReference("UserSellData");
           fd1= FirebaseDatabase.getInstance().getReference("Users");
        recyclerView= b.recyclerview;
        Toast.makeText(getContext(), "Welcome To SellAll", Toast.LENGTH_SHORT).show();
        list=new ArrayList<>();
        fa=FirebaseAuth.getInstance();
        s=b.swipe;
        s.setColorSchemeColors(getResources().getColor(R.color.lightgreen));
//        recyclerView.setHasFixedSize(true);
        adapter =new UsersAdapter(getActivity(),list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        fd.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                for(DataSnapshot d:snapshot.getChildren()){
                    UserSellData u = d.getValue(UserSellData.class);
                    u.setPostid(d.getKey());
                    u.setUid(snapshot.getKey());
                    fd1.child(snapshot.getKey()).child("name").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot1) {

                            String s = snapshot1.getValue(String.class);
                            u.setPersonname(s);
                            adapter.notifyDataSetChanged();

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                    list.add(u);
                    pd.dismiss();
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
        s.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                adapter.notifyDataSetChanged();
                recyclerView.setAdapter(adapter);
                s.setRefreshing(false);
            }
        });

        return b.getRoot();

    }

}