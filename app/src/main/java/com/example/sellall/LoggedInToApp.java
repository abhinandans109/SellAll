package com.example.sellall;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.sellall.Adapters.FragmentAdapter;
import com.example.sellall.Adapters.MyPosts;
import com.example.sellall.Fragments.BuyFragment;
import com.example.sellall.databinding.ActivityLoggedInToAppBinding;
import com.google.firebase.auth.FirebaseAuth;

public class LoggedInToApp extends AppCompatActivity {
    FirebaseAuth a;
    ActivityLoggedInToAppBinding b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        b=ActivityLoggedInToAppBinding.inflate(getLayoutInflater());
        setContentView(b.getRoot());
        a=FirebaseAuth.getInstance();
        ActionBar actionBar;
        actionBar = getSupportActionBar();
        ColorDrawable colorDrawable
                = new ColorDrawable(Color.parseColor("#FF93C25C"));
        actionBar.setBackgroundDrawable(colorDrawable);
        b.viewpager.setAdapter(new FragmentAdapter(getSupportFragmentManager()));
        b.tablayout.setupWithViewPager(b.viewpager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater in=getMenuInflater();
        in.inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.profile:
                startActivity(new Intent(this, profile.class));
                break;

            case R.id.posts:
                startActivity(new Intent(this, MyPosts.class));
                break;

            case R.id.logout:
                a.signOut();
                startActivity(new Intent(this, MainActivity.class));
                break;

        }
        return  false;
    }

}