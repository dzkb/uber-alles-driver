package com.example.tomek.uberallesdriver.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tomek.uberallesdriver.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;

/**
 * A simple {@link Fragment} subclass.
 */
public class SummaryMapFragment extends Fragment {

    MapView mapView;
    private View rootView;
    private GoogleMap googleMap;
    public static final double WROCLAW_LAT = 51.1078852;
    public static final double WROCLAW_LNG = 17.0385376;

    public SummaryMapFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_summary_map, container, false);
        mapView = initMap();


        mapView.onCreate(savedInstanceState);
        mapView.onResume(); // needed to get the map to display immediately
        return rootView;
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
                LatLng wroclaw = new LatLng(WROCLAW_LAT,WROCLAW_LNG);
                // googleMap.animateCamera(CameraUpdateFactory.newLatLng(wroclaw));
                CameraUpdate location = CameraUpdateFactory.newLatLngZoom(
                        wroclaw, 12);
                googleMap.animateCamera(location);

            }
        });



        return mapView;

    }

}
