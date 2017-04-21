package com.example.szymon.app;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.szymon.app.fragments.AvailableJourneysFragment;
import com.example.szymon.app.fragments.HistoryFragment;
import com.example.szymon.app.fragments.SettingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DriverActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigationView;

    AvailableJourneysFragment availableJourneysFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        ButterKnife.bind(this);
        initNavigationView();

        availableJourneysFragment = new AvailableJourneysFragment();
        openFragment(availableJourneysFragment);
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
                                availableJourneysFragment = new AvailableJourneysFragment();
                                openFragment(availableJourneysFragment);
                                break;
                            case R.id.action_settings:
                                SettingsFragment settingsFragment = new SettingsFragment();
                                openFragment(settingsFragment);
                                break;
                            default:
                                availableJourneysFragment = new AvailableJourneysFragment();
                                openFragment(availableJourneysFragment);
                                break;
                        }
                        return true;
                    }
                }
        );
    }

//

    private void openFragment(final Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();

    }
}
