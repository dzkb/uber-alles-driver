package com.example.tomek.uberallesdriver.utils;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.tomek.uberallesdriver.fragments.DriverInformationFragment;
import com.example.tomek.uberallesdriver.fragments.SummaryMapFragment;

public class CustomPagerAdapter extends FragmentPagerAdapter {

    int tabCount;

    public CustomPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SummaryMapFragment summaryMapFragment = new SummaryMapFragment();
                return summaryMapFragment;
            case 1:
                DriverInformationFragment driverInformationFragment = new DriverInformationFragment();
                return driverInformationFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
