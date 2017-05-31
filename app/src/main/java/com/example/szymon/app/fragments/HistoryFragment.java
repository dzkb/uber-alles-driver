package com.example.szymon.app.fragments;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.szymon.app.R;
import com.example.szymon.app.RecyclerAdapter;
import com.example.szymon.app.RecyclerHistoryAdapter;
import com.example.szymon.app.api.pojo.HistoryFare;
import com.example.szymon.app.database.FeedReaderDbHelper;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {


    RecyclerView recyclerView;
    View rootView;

    public static RecyclerHistoryAdapter adapter;


    public HistoryFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        rootView = inflater.inflate(R.layout.fragment_history,container,false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view_history);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String user = prefs.getString("Authentication_Id", "");
        FeedReaderDbHelper helper = new FeedReaderDbHelper(getContext());

        HashMap<String,HistoryFare> map = helper.selectFareByUser(user);
        ArrayList<HistoryFare> list = new ArrayList<HistoryFare>(map.values());

        adapter = new RecyclerHistoryAdapter(list);

        recyclerView.setAdapter(adapter);

        return rootView;
    }



}
