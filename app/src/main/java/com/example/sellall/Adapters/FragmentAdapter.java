package com.example.sellall.Adapters;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.sellall.Fragments.BuyFragment;
import com.example.sellall.Fragments.SellFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: return new SellFragment();
            case 1: return new BuyFragment();
            default: return new SellFragment();
        }
//        return  null;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String s=null;
        switch(position){
            case 0: return "Sell";
            case 1: return "Buy";
            default: return "sell";
        }
//return  "hello";
    }
}
