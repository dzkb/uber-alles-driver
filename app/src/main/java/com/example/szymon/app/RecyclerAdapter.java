package com.example.szymon.app;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.szymon.app.api.pojo.Fare;
import com.example.szymon.app.api.pojo.Point;
import com.example.szymon.app.fragments.DetailsFragment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    ArrayList<Fare> faresList;
    Activity activity;
    Context context;
    Boolean isNewFaresList;

    public RecyclerAdapter(ArrayList<Fare> faresList, Activity activity, Context context, Boolean isNewFaresList) {
        this.faresList = faresList;
        this.activity = activity;
        this.context = context;
        this.isNewFaresList = isNewFaresList;
    }


    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

        final Fare fare = faresList.get(position);
        Point startPoint = fare.getStartingPoint();
        Point destinationPoint = fare.getEndingPoint();
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(activity.getApplicationContext(), Locale.getDefault());
        double startPointLatitude = startPoint.getLatitude();
        double startPointLongitude = startPoint.getLongitude();
        try {
            addresses = geocoder.getFromLocation(startPointLatitude, startPointLongitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

        holder.startPoint.setText(address);
        List<Address> addressesDestination = null;
        double destinationPointLatitude = destinationPoint.getLatitude();
        double destinationPointLongitude = destinationPoint.getLongitude();
        try {
            addressesDestination = geocoder.getFromLocation(destinationPointLatitude, destinationPointLongitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String addressDestination = addressesDestination.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()

        holder.destinationPoint.setText(addressDestination);
//        holder.startPoint.setText(String.valueOf((round(startPoint.getLatitude(), 2) + ", " + round(startPoint.getLongitude(), 2))));
//        holder.destinationPoint.setText(String.valueOf((round(destinationPoint.getLatitude(), 2) + ", " + round(destinationPoint.getLongitude(), 2))));
        holder.date.setText(fare.getStartingDate());
        holder.newFare.setVisibility(View.INVISIBLE);
        if (isNewFaresList) {
            holder.cardView.setBackgroundColor(Color.LTGRAY);
            holder.newFare.setVisibility(View.VISIBLE);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DetailsFragment detailsFragment = new DetailsFragment();
                openFragment(detailsFragment);
            }
        });
    }

    @Override
    public int getItemCount() {
        return faresList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView startPoint;
        private TextView destinationPoint;
        private TextView date;
        private TextView newFare;
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            startPoint = (TextView) itemView.findViewById(R.id.start_point_name);
            destinationPoint = (TextView) itemView.findViewById(R.id.destination_point_name);
            date = (TextView) itemView.findViewById(R.id.fare_date);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
            newFare = (TextView) itemView.findViewById(R.id.new_fare_text_view);
        }
    }

    public static String getKeyByValue(Map<String, Fare> map, Fare value) {
        for (Map.Entry<String, Fare> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    private void openFragment(final Fragment fragment) {
        FragmentManager fragmentManager = ((AppCompatActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();

    }

    public static double round(Double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
