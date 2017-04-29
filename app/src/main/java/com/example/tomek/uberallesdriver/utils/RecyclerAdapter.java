package com.example.tomek.uberallesdriver.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tomek.uberallesdriver.DetailsActivity;
import com.example.tomek.uberallesdriver.R;
import com.example.tomek.uberallesdriver.api.pojo.Fare;
import com.example.tomek.uberallesdriver.api.pojo.Point;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import static com.example.tomek.uberallesdriver.LogedUserData.ACTIVE_FARE_ID;
import static com.example.tomek.uberallesdriver.LogedUserData.FARES_LIST;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    ArrayList<Fare> faresList;
    Activity activity;

    public RecyclerAdapter(ArrayList<Fare> faresList, Activity activity) {
        this.faresList = faresList;
        this.activity = activity;
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
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ACTIVE_FARE_ID = getKeyByValue(FARES_LIST, fare);
                Bundle bundle = new Bundle();
                bundle.putString("key", ACTIVE_FARE_ID);
                Intent intent = new Intent(activity, DetailsActivity.class);
                intent.putExtra("bundle", bundle);
                activity.startActivity(intent);
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
        private CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            startPoint = (TextView) itemView.findViewById(R.id.start_point_name);
            destinationPoint = (TextView) itemView.findViewById(R.id.destination_point_name);
            date = (TextView) itemView.findViewById(R.id.fare_date);
            cardView = (CardView) itemView.findViewById(R.id.card_view);
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
}
