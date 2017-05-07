package com.example.szymon.app.api;

import android.util.Log;
import com.example.szymon.app.api.pojo.Fare;
import com.example.szymon.app.api.pojo.Localisation;
import com.example.szymon.app.api.pojo.Point;

import org.apache.commons.lang3.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.szymon.app.LogedUserData.ALL_FARES_LIST;
import static com.example.szymon.app.LogedUserData.USER_PASSWORD;
import static com.example.szymon.app.LogedUserData.USER_PHONE;
import static com.example.szymon.app.LogedUserData.mainTheard;
import static com.example.szymon.app.fragments.AvailableJourneysFragment.adapter;
import static com.example.szymon.app.fragments.AvailableJourneysFragment.adapterNewFares;

public class ApiImpl {

    public static ArrayList<Fare> getFares(Fares parameter) {
        final ArrayList<Fare> fares = new ArrayList<>();
        final String phoneNumber = USER_PHONE;
        final String password = USER_PASSWORD;
        UserService fareService = ApiClient.createService(UserService.class, phoneNumber, password);
        Call<List<Fare>> call = null;
        switch (parameter) {
            case ALL:
                call = fareService.allFares();
                ALL_FARES_LIST = fares;
                break;
            case ONLY_ACCEPTED:
                call = fareService.acceptedFares();
                break;
            default:
                Log.d("Error", "Incorrect task");
        }

        call.enqueue(new Callback<List<Fare>>() {
            @Override
            public void onResponse(Call<List<Fare>> call, Response<List<Fare>> response) {
                if (response.isSuccessful()) {
                    fares.addAll(response.body().subList(0, 2));
                    Log.d("OK", "Liczba pobranych przejazdów " + fares.size());
                    adapter.notifyDataSetChanged();
                    adapterNewFares.notifyDataSetChanged();
                } else {
                    Log.d("Error", "Coś poszło nie tak . . .");
                }
            }

            @Override
            public void onFailure(Call<List<Fare>> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
        return fares;
    }

    public static void changeFareStatus(Fares parameter, final String fareId) {
        final String phoneNumber = USER_PHONE;
        final String password = USER_PASSWORD;
        UserService fareService = ApiClient.createService(UserService.class, phoneNumber, password);
        Call<String> call = null;
        switch (parameter) {
            case CONFIRM:
                call = fareService.acceptFare(fareId);
                break;
            case CANCEL:
                call = fareService.cancelFare(fareId);
                break;
            case COMPLETE:
                call = fareService.completeFare(fareId);
                break;
            default:
                Log.d("Error", "Coś sie spiepszyło");
        }
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d("OK", "Odpowiedź z serwera: " + response.body());
                } else {
                    Log.d("Error", "Coś poszło nie tak . . .");
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });
    }

    public static void sendLocatisation(final Point localisation) {
        final String phoneNumber = USER_PHONE;
        final String password = USER_PASSWORD;
        UserService fareService = ApiClient.createService(UserService.class, phoneNumber, password);
        Call<Point> call = fareService.putLocalisation(localisation);
        call.enqueue(new Callback<Point>() {
            @Override
            public void onResponse(Call<Point> call, Response<Point> response) {
                if (response.isSuccessful()) {
                    System.out.println("Lokalizacja wysłana");
                    Log.d("OK", "Wysłana lokalizacja: " + response.body());
                } else {
                    Log.d("Error", "Coś poszło nie tak . . .");
                }
            }
            @Override
            public void onFailure(Call<Point> call, Throwable t) {
                System.out.println("Lokalizacja nie została wysłana");
                Log.d("Error", "Komunikacja zawiodła");
            }
        });
    }

    public enum Fares {
        ALL,
        ONLY_ACCEPTED,
        CANCEL,
        CONFIRM,
        COMPLETE
    }
}
