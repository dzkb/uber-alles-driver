package com.example.tomek.uberallesdriver;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.tomek.uberallesdriver.api.pojo.Fare;

import java.util.HashMap;

public class LogedUserData {

    public static String USER_NAME;
    public static String USER_SURNAME;
    public static String USER_PHONE;
    public static String USER_PASSWORD;

    public static String ACTIVE_FARE_ID;

    public static HashMap<String, Fare> FARES_LIST = null;

    public static void addFare(String key, Fare fare) {
        if (FARES_LIST == null) FARES_LIST = new HashMap<>();
        FARES_LIST.put(key, fare);
    }

    public static void deleteFareByKey(String key) {
        FARES_LIST.remove(key);
    }

    public static void saveCredentials(String login, String password, String firstName, String lastName, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Authentication_Id", login);
        editor.putString("Authentication_Password", password);
        editor.putString("Authentication_Name", firstName);
        editor.putString("Authentication_Surname", lastName);
        editor.apply();
    }
}
