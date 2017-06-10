package com.example.szymon.app.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.szymon.app.R;
import com.example.szymon.app.RecyclerAdapter;
import com.example.szymon.app.api.ApiClient;
import com.example.szymon.app.api.ApiImpl;
import com.example.szymon.app.api.UserService;
import com.example.szymon.app.api.pojo.CMFareRequest;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.szymon.app.LogedUserData.USER_PASSWORD;
import static com.example.szymon.app.LogedUserData.USER_PHONE;

public class DetailsFragment extends Fragment {

    View rootView;
    Button startFare;
    static Button endFare;
    static Button cancelFare;
    static TextView cost;
    static CardView costCard;
    static CardView endFareCard, cancelFareCard;
    public CMFareRequest fare;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_details, container, false);
        reconstructFareData();
        bindViews();
        endFare.setVisibility(View.INVISIBLE);
        costCard.setVisibility(View.INVISIBLE);
        onClick();
        return rootView;
    }

    private void reconstructFareData() {
        String jsonFareData = getArguments().getString("JSONfareData");
        fare = new Gson().fromJson(jsonFareData, CMFareRequest.class);
    }

    private void bindViews() {
        startFare = (Button) rootView.findViewById(R.id.start_fare);
        endFare = (Button) rootView.findViewById(R.id.end_fare);
        costCard = (CardView) rootView.findViewById(R.id.cost_card_view);
        cancelFare = (Button) rootView.findViewById(R.id.cancel_fare);
        cost = (TextView) rootView.findViewById(R.id.cost);
        endFareCard = (CardView) rootView.findViewById(R.id.end_fare_card);
        cancelFareCard = (CardView) rootView.findViewById(R.id.cancel_fare_card);
    }

    private void onClick() {
        startFare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startFare.setVisibility(View.INVISIBLE);
                endFare.setVisibility(View.VISIBLE);
            }
        });

        endFare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CostDialog costDialog = new CostDialog();
                Bundle fareData = new Bundle();
                fareData.putString("fareId", fare.getFareID());
                costDialog.setArguments(fareData);

                costDialog.show(getActivity().getSupportFragmentManager(), "CostaDialog");

                RecyclerAdapter recyclerAdapter = new RecyclerAdapter(getActivity(), getContext(), false);
                recyclerAdapter.deleteFare(fare.getFareID());
            }
        });

        cancelFare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String phoneNumber = USER_PHONE;
                final String password = USER_PASSWORD;
                UserService fareService = ApiClient.createService(UserService.class, phoneNumber, password);
                Call<String> call = fareService.cancelFare(fare.getFareID());
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if (response.isSuccessful()) {
                            Log.d("OK", "Odpowiedź z serwera: " + response.body());
                            Toast.makeText(getContext(), "Anulowano przejazd", Toast.LENGTH_LONG).show();
                        } else {
                            Log.d("Error", "Wystąpił błąd");
                        }
                    }
                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("Error", t.getMessage());
                    }
                });
            }
        });
    }

    public static class CostDialog extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final String fareId = getArguments().getString("fareId");
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            final EditText input = new EditText(getContext());

            builder.setTitle("Podaj koszt")
                    .setView(input)
                    .setPositiveButton("Potwierdz", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            costCard.setVisibility(View.VISIBLE);
                            cost.setText(input.getText());
                            endFareCard.setVisibility(View.GONE);
                            cancelFareCard.setVisibility(View.GONE);
                            ApiImpl.changeFareStatus(ApiImpl.Fares.COMPLETE, fareId, 25);
                        }
                    })
                    .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getContext(), "Anluj", Toast.LENGTH_SHORT).show();
                        }
                    });
            return builder.create();
        }
    }

}
