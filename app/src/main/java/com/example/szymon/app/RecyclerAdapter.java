package com.example.szymon.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
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

import java.util.ArrayList;
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
        holder.startPoint.setText(String.valueOf((startPoint.getLatitude() + ", " + startPoint.getLongitude())));
        holder.destinationPoint.setText(String.valueOf((destinationPoint.getLatitude() + ", " + destinationPoint.getLongitude())));
        holder.date.setText(fare.getStartingDate());
        holder.newFare.setVisibility(View.INVISIBLE);
        if (isNewFaresList ){
            holder.cardView.setBackgroundColor(Color.LTGRAY);
            holder.newFare.setVisibility(View.VISIBLE);
        }
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent intent = new Intent(activity, DetailsActivity.class);
//                activity.startActivity(intent);
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
}