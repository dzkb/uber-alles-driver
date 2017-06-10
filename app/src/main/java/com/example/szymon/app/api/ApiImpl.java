package com.example.szymon.app.api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.szymon.app.api.pojo.Fare;
import com.example.szymon.app.api.pojo.Point;
import com.example.szymon.app.api.pojo.RegistrationToken;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.szymon.app.LogedUserData.ALL_FARES_LIST;
import static com.example.szymon.app.LogedUserData.USER_PASSWORD;
import static com.example.szymon.app.LogedUserData.USER_PHONE;
import static com.example.szymon.app.fragments.AvailableJourneysFragment.adapter;

public class ApiImpl {

    public static Context context;

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
                    fares.addAll(response.body());
//                    fares.addAll(response.body().subList(0, 2));
                    Log.d("OK", "Liczba pobranych przejazdów " + fares.size());
                    adapter.notifyDataSetChanged();

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
        changeFareStatus(parameter, fareId, -1);
    }

    public static void changeFareStatus(Fares parameter, final String fareId, int cost) {
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
                call = fareService.completeFare(fareId, cost);
                break;
            default:
                Log.d("Error", "Coś sie spiepszyło");
        }
        final boolean shouldToast = parameter == Fares.CONFIRM;
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.d("OK", "Odpowiedź z serwera: " + response.body());
                } else {
                    try {
                        JSONObject errorData = new JSONObject(response.errorBody().string());
                        String error = errorData.getString("error");
                        if (shouldToast && context != null) {
                            Toast.makeText(context, error, Toast.LENGTH_LONG).show();
                        }
                        Log.d("Error", error);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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

    public static void updateRegistrationToken(final RegistrationToken registrationToken) {
        final String phoneNumber = USER_PHONE;
        final String password = USER_PASSWORD;
        UserService fareService = ApiClient.createService(UserService.class, phoneNumber, password);
        Call<String> call = fareService.putRegistrationToken(registrationToken);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("TOKEN", "Zaktualizowano registration token");
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}
