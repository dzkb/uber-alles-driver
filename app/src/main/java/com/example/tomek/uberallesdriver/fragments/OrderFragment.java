package com.example.tomek.uberallesdriver.fragments;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.tomek.uberallesdriver.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static com.example.tomek.uberallesdriver.LogedUserData.USER_NAME;
import static com.example.tomek.uberallesdriver.LogedUserData.USER_PHONE;
import static com.example.tomek.uberallesdriver.LogedUserData.USER_SURNAME;
import static com.example.tomek.uberallesdriver.utils.DateHelper.compareDate;
import static com.example.tomek.uberallesdriver.utils.DateHelper.compareTime;

import static android.widget.Toast.LENGTH_SHORT;
import static android.widget.Toast.makeText;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    MapView mapView;
    private View rootView;
    public static final String OLD_DATE = "Podaj date z przyszłosci";
    private GoogleMap googleMap;
    private Address startAddress = null;
    private Address endAddress = null;

    private FloatingActionButton openNextFragment;

    public static final double WROCLAW_LAT = 51.1078852;
    public static final double WROCLAW_LNG = 17.0385376;


    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_order, container, false);

        initOnClick();

        openNextFragment.setVisibility(View.INVISIBLE);

        mapView = initMap();


        mapView.onCreate(savedInstanceState);
        mapView.onResume(); // needed to get the map to display immediately


        return rootView;
    }


    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    private MapView initMap() {
        mapView = (MapView) rootView.findViewById(R.id.map_view);

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                LatLng wroclaw = new LatLng(WROCLAW_LAT, WROCLAW_LNG);
                // googleMap.animateCamera(CameraUpdateFactory.newLatLng(wroclaw));
                CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                        wroclaw, 12);
                googleMap.animateCamera(location);

            }
        });


        return mapView;

    }

    private void initOnClick() {


        ImageView myLocation = (ImageView) rootView.findViewById(R.id.location_image);
        final FloatingActionButton fabAddLocation = (FloatingActionButton) rootView.findViewById(R.id.fab_add_location);
        final EditText startPosition = (EditText) rootView.findViewById(R.id.start_point_edit_text);
        final EditText destinantionPosition = (EditText) rootView.findViewById(R.id.descination_point_edit_text);
        final TextView time = (TextView) rootView.findViewById(R.id.time);

        openNextFragment = (FloatingActionButton) rootView.findViewById(R.id.fab_open_next_fragment);

        myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (ActivityCompat.checkSelfPermission(rootView.getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(rootView.getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                googleMap.getUiSettings().setMyLocationButtonEnabled(false);
                googleMap.setMyLocationEnabled(true);

            }
        });

        fabAddLocation.setOnClickListener(new View.OnClickListener()

        {

            @Override
            public void onClick(View v) {
                String startLocation = getLocation(startPosition);
                String destinationLocation = getLocation(destinantionPosition);
                List<Address> addressList = null;
                List<Address> addressList1 = null;


                if (startLocation != null || !startLocation.equals("")) {
                    Geocoder geocoder = new Geocoder(rootView.getContext());
                    try {
                        addressList = geocoder.getFromLocationName("Wrocław " + startLocation, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    if (startLocation == null || startLocation.equals("") || destinationLocation == null || destinationLocation.equals("")) {
                        Toast.makeText(getContext(), "Uzupełnij lokalizację ", Toast.LENGTH_SHORT).show();
                    } else {
                        startAddress = addressList.get(0);
                        LatLng latLng = new LatLng(startAddress.getLatitude(), startAddress.getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(latLng).title(startLocation));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    }

                }

                if (destinationLocation != null || !destinationLocation.equals("")) {
                    Geocoder geocoder = new Geocoder(rootView.getContext());
                    try {
                        addressList1 = geocoder.getFromLocationName("Wrocław " + destinationLocation, 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (destinationLocation == null || destinationLocation.equals("") || startLocation == null || startLocation.equals("")) {
                        Toast.makeText(getContext(), "Uzupełnij lokazliację ", Toast.LENGTH_SHORT).show();
                    } else {
                        endAddress = addressList1.get(0);
                        LatLng latLng = new LatLng(endAddress.getLatitude(), endAddress.getLongitude());
                        googleMap.addMarker(new MarkerOptions().position(latLng).title(startLocation));
                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                    }
                }

                if (destinationLocation != null && !destinationLocation.equals("") && startLocation != null && !startLocation.equals("")) {
                    fabAddLocation.setVisibility(View.INVISIBLE);
                    openNextFragment.setVisibility(View.VISIBLE);

                }

            }
        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment date = new DatePickerFragment();
                date.journeyTime = time;
                date.show(getActivity().getSupportFragmentManager(), "DatePicker");
            }
        });

        openNextFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmFragment confirmFragment = new ConfirmFragment();
                Bundle bundle = new Bundle();
                bundle.putDouble("StartLat", startAddress.getLatitude());
                bundle.putDouble("StartLong", startAddress.getLongitude());
                bundle.putDouble("EndLat", endAddress.getLatitude());
                bundle.putDouble("EndLong", endAddress.getLongitude());
                bundle.putString("name", USER_NAME + " " + USER_SURNAME);
                bundle.putString("phone", USER_PHONE);
                bundle.putString("time", getFareDateISO8601());
                confirmFragment.setArguments(bundle);
                openFragment(confirmFragment);
            }
        });


    }

    public static class TimePickerFragment extends DialogFragment
            implements TimePickerDialog.OnTimeSetListener {

        static TextView journeyTime;
        static Calendar givenCalendar;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return it
            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));

        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

            givenCalendar.set(Calendar.MINUTE, minute);
            givenCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
            Calendar c = Calendar.getInstance();
            if (compareDate(givenCalendar, c) == 0) {
                if (compareTime(givenCalendar, c)) {
                    setEditText(hourOfDay, minute);
                } else journeyTime.setText(OLD_DATE);
            } else setEditText(hourOfDay, minute);
        }

        private void setEditText(int hourOfDay, int minute) {
            final StringBuilder sb = new StringBuilder(journeyTime.getText().length());
            sb.append(journeyTime.getText());
            journeyTime.setText(sb.toString() + StringUtils.leftPad(Integer.toString(hourOfDay), 2, '0') + ":" + StringUtils.leftPad(Integer.toString(minute), 2, '0'));
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        TextView journeyTime;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int givenYear, int givenMonth, int givenDay) {

            Calendar current = Calendar.getInstance();
            Calendar validDate = Calendar.getInstance();
            validDate.set(givenYear, givenMonth, givenDay);
            if (compareDate(validDate, current) >= 0) {
                TimePickerFragment.journeyTime = journeyTime;
                TimePickerFragment.givenCalendar = validDate;
                journeyTime.setText(StringUtils.leftPad(Integer.toString(givenDay), 2, '0') + "/" + StringUtils.leftPad(Integer.toString(givenMonth), 2, '0') + " ");
                TimePickerFragment timePicker = new TimePickerFragment();
                timePicker.show(getActivity().getSupportFragmentManager(), "timePicker");
            } else {
                journeyTime.setText(OLD_DATE);
            }
        }

    }

    private String getLocation(EditText locationField) {
        String location = null;
        try {
            location = locationField.getText().toString();
        } catch (NullPointerException emptyField) {
            makeText(getActivity().getApplicationContext(), "Enter any value", LENGTH_SHORT).show();
        }
        return location;
    }

    private void openFragment(final Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();

    }

    private static String getFareDateISO8601() {
        Date date = new Date(TimePickerFragment.givenCalendar.getTimeInMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", new Locale("pl"));
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+1"));
        String dateString = dateFormat.format(date);
        dateString = dateString.substring(0, dateString.length() - 1) + "+01:00";
        return dateString;
    }
}