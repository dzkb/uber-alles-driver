package com.example.tomek.uberallesdriver.utils;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tomek.uberallesdriver.fragments.HistoryFragment;
import com.example.tomek.uberallesdriver.fragments.OrderFragment;
import com.example.tomek.uberallesdriver.fragments.SettingsFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter{

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new HistoryFragment();
            case 1:
                return new OrderFragment();
            case 2:
                return new SettingsFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
