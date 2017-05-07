package com.example.szymon.app.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.szymon.app.R;
import com.example.szymon.app.RecyclerAdapter;
import com.example.szymon.app.api.pojo.Fare;
import com.example.szymon.app.api.pojo.Point;

import java.util.ArrayList;

import static com.example.szymon.app.LogedUserData.ALL_FARES_LIST;

public class AvailableJourneysFragment extends Fragment {

    RecyclerView recyclerView, recyclerViewNewFares;

    public static RecyclerAdapter adapter;
    public static RecyclerAdapter adapterNewFares;
    View rootView;

    public AvailableJourneysFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_available_journeys, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerViewNewFares = (RecyclerView) rootView.findViewById(R.id.recycler_view_new_fares);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        final LinearLayoutManager layoutManager = new LinearLayoutManager(rootView.getContext());
        layoutManager.setStackFromEnd(true);
        recyclerViewNewFares.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapter(ALL_FARES_LIST, getActivity(), getContext(), false);
        adapterNewFares = new RecyclerAdapter(ALL_FARES_LIST, getActivity(), getContext(), true);

        recyclerViewNewFares.setAdapter(adapterNewFares);
        recyclerView.setAdapter(adapter);

        return rootView;
    }

    private ArrayList<Fare> createExampleList() {

        ArrayList<Fare> fares = new ArrayList<>();
        Point first = new Point(17.152d, 20.241d);
        Point second = new Point(27.145d, 10.278d);
        Fare fare = new Fare("-KiQxGUbh-TvcznBn6nX", first, second, "Szymon Zwoliński", "500700600", "500145623", "2017-01-02T03:01:45+01:00", "2017-01-02T03:09:45+01:00", "placed");
        Fare fare2 = new Fare("-KiQxGUbh-TvcznBn6nX", first, second, "Szymon Zwoliński", "500700600", "500145623", "2017-01-02T03:01:45+01:00", "2017-01-02T03:09:45+01:00", "placed");
        fares.add(fare);
        fares.add(fare2);
        return fares;
    }
}
