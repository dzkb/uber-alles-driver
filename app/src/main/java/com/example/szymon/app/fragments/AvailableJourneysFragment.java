package com.example.szymon.app.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.szymon.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AvailableJourneysFragment extends Fragment {


    public AvailableJourneysFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_available_journeys, container, false);
    }

}
