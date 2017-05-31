package com.example.szymon.app.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.szymon.app.R;
import com.example.szymon.app.RecyclerAdapter;
import com.example.szymon.app.api.ApiImpl;
import com.example.szymon.app.api.pojo.CMFareRequest;
import com.example.szymon.app.api.pojo.Fare;
import com.example.szymon.app.api.pojo.HistoryFare;
import com.example.szymon.app.api.pojo.Point;
import com.example.szymon.app.database.FeedReaderDbHelper;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.szymon.app.LogedUserData.ALL_FARES_LIST;

public class AvailableJourneysFragment extends Fragment {

    RecyclerView recyclerView;

    public static RecyclerAdapter adapter;

    BottomSheetBehavior bottomSheetBehavior;
    //ArrayList<CMFareRequest> faresRequestList;

    View rootView;
    private Button fareAcceptBtn;

    public AvailableJourneysFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_available_journeys, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));

        adapter = new RecyclerAdapter(getActivity(), getContext(), false);

        recyclerView.setAdapter(adapter);

        bottomSheetBehavior = BottomSheetBehavior.from(rootView.findViewById(R.id.bottomSheetLayout));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        return rootView;
    }

    private String getTimeFromDate(String date) {
        return date.substring(11, 16);
    }

    private String findLocation(Point point) {
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(rootView.getContext(), Locale.getDefault());
        double startPointLatitude = point.getLatitude();
        double startPointLongitude = point.getLongitude();
        try {
            addresses = geocoder.getFromLocation(startPointLatitude, startPointLongitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

        return address.length() >= 20 ? address.substring(0, 17) + "..." : address;
    }

    private void openBottomSheet(View rootView, final BottomSheetBehavior bottomSheetBehavior, final CMFareRequest fareRequest) {
        fareAcceptBtn = (Button) rootView.findViewById(R.id.fare_accept_btn);
        TextView startPoint = (TextView) rootView.findViewById(R.id.bottom_sheet_start_point_name);
        TextView endPoint = (TextView) rootView.findViewById(R.id.bottom_sheet_destination_point_name);
        TextView date = (TextView) rootView.findViewById(R.id.bottom_sheet_fare_date);

        startPoint.setText(findLocation(fareRequest.getStartingPoint()));
        endPoint.setText(findLocation(fareRequest.getEndingPoint()));
        date.setText(getTimeFromDate(fareRequest.getStartingDate()));
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        fareAcceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                if(faresRequestList==null){
//                    faresRequestList = new ArrayList<CMFareRequest>();
//                }
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                ApiImpl.changeFareStatus(ApiImpl.Fares.CONFIRM, fareRequest.getFareID());

                adapter.addFare(fareRequest);

                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
                String user = prefs.getString("Authentication_Id", "");
                String fareId = fareRequest.getFareID();
                String date = fareRequest.getStartingDate();
                String startingPoint = findLocation(fareRequest.getStartingPoint());
                String endingPoint = findLocation(fareRequest.getEndingPoint());
                FeedReaderDbHelper helper = new FeedReaderDbHelper(getContext());
                HistoryFare fare = new HistoryFare(fareId,date,startingPoint,endingPoint);
                helper.insert(helper.getWritableDatabase(),fare,user);
            }
        });
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

    public void onFareRequested(CMFareRequest fareRequest) {
        Log.d("AvailableJourneys", fareRequest.toString());
        openBottomSheet(rootView, bottomSheetBehavior, fareRequest);

    }
}
