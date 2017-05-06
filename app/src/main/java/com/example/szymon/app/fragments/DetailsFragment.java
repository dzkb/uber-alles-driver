package com.example.szymon.app.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.szymon.app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailsFragment extends Fragment {

    View rootView;
    Button startFare, endFare;
    CardView costCard;

    public DetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_details, container, false);
        bindViews();
        endFare.setVisibility(View.INVISIBLE);
        costCard.setVisibility(View.INVISIBLE);
        onClick();
        return rootView;
    }

    private void bindViews(){
        startFare= (Button) rootView.findViewById(R.id.start_fare);
        endFare= (Button) rootView.findViewById(R.id.end_fare);
        costCard = (CardView) rootView.findViewById(R.id.cost_card_view);
    }

    private void onClick(){
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
                costDialog.show(getActivity().getSupportFragmentManager(), "CostaDialog");
                costCard.setVisibility(View.VISIBLE);
            }
        });
    }

    public static class CostDialog extends DialogFragment {
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Podaj koszt")
                    .setMessage("25zł")
                    .setPositiveButton("Potwierdz", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getContext(), "OK", Toast.LENGTH_SHORT).show();

                        }
                    })
                    .setNegativeButton("Anuluj", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getContext(), "Anluju", Toast.LENGTH_SHORT).show();
                        }
                    });
            return builder.create();
        }
    }

}
