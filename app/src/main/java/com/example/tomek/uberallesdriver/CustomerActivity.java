package com.example.tomek.uberallesdriver;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.tomek.uberallesdriver.fragments.HistoryFragment;
import com.example.tomek.uberallesdriver.fragments.OrderFragment;
import com.example.tomek.uberallesdriver.fragments.SettingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CustomerActivity extends AppCompatActivity {


    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    OrderFragment orderFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        ButterKnife.bind(this);

        initNavigationView();

        orderFragment = new OrderFragment();
        openFragment(orderFragment);
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
    }

    public void initNavigationView() {
        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_history:
                                HistoryFragment historyFragment = new HistoryFragment();
                                openFragment(historyFragment);
                                break;
                            case R.id.action_order:
                                orderFragment = new OrderFragment();
                                openFragment(orderFragment);
                                break;
                            case R.id.action_settings:
                                SettingsFragment settingsFragment = new SettingsFragment();
                                openFragment(settingsFragment);
                                break;
                            default:
                                orderFragment = new OrderFragment();
                                openFragment(orderFragment);
                                break;
                        }
                        return true;
                    }
                }
        );
    }

    private void openFragment(final Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();

    }
}
