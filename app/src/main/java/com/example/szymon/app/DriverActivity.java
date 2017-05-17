package com.example.szymon.app;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.szymon.app.FirebaseCloudMessaging.InstanceIdService;
import com.example.szymon.app.api.ApiImpl;
import com.example.szymon.app.api.pojo.CMFareRequest;
import com.example.szymon.app.api.pojo.Localisation;
import com.example.szymon.app.api.pojo.RegistrationToken;
import com.example.szymon.app.fragments.AvailableJourneysFragment;
import com.example.szymon.app.fragments.HistoryFragment;
import com.example.szymon.app.fragments.SettingsFragment;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;

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
        GPSTracker mGPS = new GPSTracker(this);
        ApiImpl.context = getApplicationContext();

        String registrationToken = FirebaseInstanceId.getInstance().getToken();
        ApiImpl.updateRegistrationToken(new RegistrationToken(registrationToken));

        if (mGPS.canGetLocation()) {
            mGPS.getLocation();
            Log.d("Info", "Lat" + mGPS.getLatitude() + "Lon" + mGPS.getLongitude());
        } else {
            Log.d("Error", "Unabletofind");
            System.out.println("Unable");
        }

        availableJourneysFragment = new AvailableJourneysFragment();
        openFragment(availableJourneysFragment);

        LocalBroadcastManager.getInstance(this).registerReceiver(mMessageReceiver,
                new IntentFilter("CMFareRequest"));
    }

    @Override
    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mMessageReceiver);
        super.onDestroy();
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

    private void openFragment(final Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();

    }
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String payload = intent.getStringExtra("payload");
            Log.d("Receiver", "Got message: " + payload);
            Gson gson = new Gson();

            CMFareRequest request = gson.fromJson(payload, CMFareRequest.class);
            availableJourneysFragment.onFareRequested(request);
        }
    };
}
