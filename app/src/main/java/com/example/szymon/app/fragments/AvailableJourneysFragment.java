package com.example.szymon.app.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.szymon.app.R;
import com.example.szymon.app.RecyclerAdapter;
import com.example.szymon.app.api.pojo.Fare;
import com.example.szymon.app.api.pojo.Point;

import java.util.ArrayList;
import java.util.HashMap;

import static com.example.szymon.app.LogedUserData.FARES_LIST;

/**
 * A simple {@link Fragment} subclass.
 */
public class AvailableJourneysFragment extends Fragment {

    RecyclerView recyclerView, recyclerViewNewFares;

    ArrayList<Fare> fares;
    RecyclerAdapter adapter;
    RecyclerAdapter adapterNewFares;
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

        adapter = new RecyclerAdapter(createExampleList(), getActivity(), getContext(),false);
        adapterNewFares = new RecyclerAdapter(createExampleList(), getActivity(), getContext(),true);


        recyclerView.setAdapter(adapter);
        recyclerViewNewFares.setAdapter(adapterNewFares);

        return rootView;
    }
    private ArrayList<Fare> createExampleList() {
        fares = new ArrayList<>();

        if (FARES_LIST == null) {
            Point first = new Point(17.1, 20.2);
            Point second = new Point(27.1, 10.2);
            FARES_LIST = new HashMap<>();
            Fare fare = new Fare(first, second, "Szymon Zwoliński", 500700600, "12/03/1016");
            FARES_LIST.put("-KiQxGUbh-TvcznBn6nX", fare);
        }
        return new ArrayList(FARES_LIST.values());

    }



}
