package com.example.szymon.app;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.szymon.app.api.pojo.CMFareRequest;
import com.example.szymon.app.api.pojo.HistoryFare;
import com.example.szymon.app.utils.CommonDate;

import java.util.ArrayList;


public class RecyclerHistoryAdapter extends RecyclerView.Adapter<RecyclerHistoryAdapter.ViewHolder> {

    ArrayList<HistoryFare> faresList;

    public RecyclerHistoryAdapter(ArrayList<HistoryFare> faresList) {
        this.faresList = faresList;
    }

    @Override
    public RecyclerHistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_view_row, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HistoryFare fare = faresList.get(position);
        holder.startPoint.setText(fare.getStartingPoint());
        holder.destinationPoint.setText(fare.getEndingPoint());
        holder.date.setText(CommonDate.getFormattedTime(fare.getStartingDate()));

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
}
