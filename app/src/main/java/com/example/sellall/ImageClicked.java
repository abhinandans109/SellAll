package com.example.sellall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.sellall.AdapterData.Imagegetter;
import com.example.sellall.Adapters.ImageAdapter;
import com.example.sellall.databinding.ActivityImageClickedBinding;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ImageClicked extends AppCompatActivity {
    RecyclerView recyclerView;
    ActivityImageClickedBinding b;
    DatabaseReference fd;
    ImageAdapter adapter;
    private ArrayList<String> list;
    public void phoneclicked(String s){
        Intent i=new Intent(Intent.ACTION_CALL);
        i.setData(Uri.parse(s));
        startActivity(i);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        b=ActivityImageClickedBinding.inflate(getLayoutInflater());
        super.onCreate(savedInstanceState);
        setContentView(b.getRoot());
        recyclerView=b.rv;
        list=new ArrayList();
        adapter= new ImageAdapter(this,list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent in=getIntent();

        if(in.getStringExtra("mob")!=null)phoneclicked(in.getStringExtra("mob"));
        list.addAll(in.getStringArrayListExtra("a"));
//        for(String i:list) Toast.makeText(this, i, Toast.LENGTH_SHORT).show();
//        list.add("https://firebasestorage.googleapis.com/v0/b/sellall2.appspot.com/o/UserSellDataImages%2F4ZKcSjDgnCY3odq2Ts7xZk60doJ2%2F1622995200369?alt=media&token=a8c51cc2-d721-4874-87ac-65c16c8667c0");
//        list.add("https://firebasestorage.googleapis.com/v0/b/sellall2.appspot.com/o/UserSellDataImages%2F4ZKcSjDgnCY3odq2Ts7xZk60doJ2%2F1622995200369?alt=media&token=a8c51cc2-d721-4874-87ac-65c16c8667c0");
//        list.add("https://firebasestorage.googleapis.com/v0/b/sellall2.appspot.com/o/UserSellDataImages%2F4ZKcSjDgnCY3odq2Ts7xZk60doJ2%2F1622995200369?alt=media&token=a8c51cc2-d721-4874-87ac-65c16c8667c0");
        adapter.notifyDataSetChanged();
//        list.add("https://firebasestorage.googleapis.com/v0/b/sellall2.appspot.com/o/UserSellDataImages%2F4ZKcSjDgnCY3odq2Ts7xZk60doJ2%2F1622995200369?alt=media&token=a8c51cc2-d721-4874-87ac-65c16c8667c0");
//        list.add("https://firebasestorage.googleapis.com/v0/b/sellall2.appspot.com/o/UserSellDataImages%2F4ZKcSjDgnCY3odq2Ts7xZk60doJ2%2F1622995200369?alt=media&token=a8c51cc2-d721-4874-87ac-65c16c8667c0");
//        list.add("https://firebasestorage.googleapis.com/v0/b/sellall2.appspot.com/o/UserSellDataImages%2F4ZKcSjDgnCY3odq2Ts7xZk60doJ2%2F1622995200369?alt=media&token=a8c51cc2-d721-4874-87ac-65c16c8667c0");
//        list.add("https://firebasestorage.googleapis.com/v0/b/sellall2.appspot.com/o/UserSellDataImages%2F4ZKcSjDgnCY3odq2Ts7xZk60doJ2%2F1622995200369?alt=media&token=a8c51cc2-d721-4874-87ac-65c16c8667c0");


    }
}