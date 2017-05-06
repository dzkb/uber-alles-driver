package com.example.szymon.app.api;

import android.util.Log;
import com.example.szymon.app.api.pojo.Fare;
import com.example.szymon.app.api.pojo.Localisation;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.szymon.app.LogedUserData.USER_PASSWORD;
import static com.example.szymon.app.LogedUserData.USER_PHONE;


public class ApiImpl {

    public static List<Fare> getFares(Fares parameter) {
        final List<Fare> fares = new ArrayList<>();
        final String phoneNumber = USER_PHONE;
        final String password = USER_PASSWORD;
        UserService fareService = ApiClient.createService(UserService.class, phoneNumber, password);
        Call<List<Fare>> call = null;
        switch (parameter) {
            case ALL:
                call = fareService.allFares();
                break;
            case ONLY_ACCEPTED:
                call = fareService.acceptedFares();
        }

        call.enqueue(new Callback<List<Fare>>() {
            @Override
            public void onResponse(Call<List<Fare>> call, Response<List<Fare>> response) {
                if (response.isSuccessful()) {
                    fares.addAll(response.body());
                    Log.d("OK", "Liczba pobranych przejazdów " + fares.size());
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

    public static void sendLocatisation(final Localisation localisation) {
        final String phoneNumber = USER_PHONE;
        final String password = USER_PASSWORD;
        UserService fareService = ApiClient.createService(UserService.class, phoneNumber, password);
        Call<Localisation> call = fareService.putLocalisation(localisation);
        call.enqueue(new Callback<Localisation>() {
            @Override
            public void onResponse(Call<Localisation> call, Response<Localisation> response) {
                if (response.isSuccessful()) {
                    Log.d("OK", "Wysłana lokalizacja: " + response.body());
                } else {
                    Log.d("Error", "Coś poszło nie tak . . .");
                }
            }

            @Override
            public void onFailure(Call<Localisation> call, Throwable t) {
                Log.d("Error", "Komunikacja zawiodła");
                Log.d("Error", t.getMessage());
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
