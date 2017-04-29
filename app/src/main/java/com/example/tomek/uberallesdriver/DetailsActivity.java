package com.example.tomek.uberallesdriver;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.tomek.uberallesdriver.api.pojo.Fare;
import com.example.tomek.uberallesdriver.fragments.HistoryFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.example.tomek.uberallesdriver.LogedUserData.ACTIVE_FARE_ID;
import static com.example.tomek.uberallesdriver.LogedUserData.FARES_LIST;
import static com.example.tomek.uberallesdriver.LogedUserData.deleteFareByKey;
import static com.example.tomek.uberallesdriver.fragments.DriverInformationFragment.deleteFare;

public class DetailsActivity extends AppCompatActivity {

    TextView arrivalDate;
    TextView passenger;
    String currentKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        currentKey = getIntent().getBundleExtra("bundle").getString("key");
        Fare fare = FARES_LIST.get(currentKey);
        arrivalDate = (TextView) findViewById(R.id.arrival_time);
        passenger = (TextView) findViewById(R.id.passenger_name);
        arrivalDate.setText(fare.getStartingDate());
        passenger.setText(fare.getClientName());
        ButterKnife.bind(this);
    }

    @OnClick(R.id.cancel_ride_activity)
    public void onCancelButtonClick(View v) {
        deleteFareByKey(currentKey);
        deleteFare(currentKey);
        HistoryFragment.refershing = true;
        finish();
    }
}