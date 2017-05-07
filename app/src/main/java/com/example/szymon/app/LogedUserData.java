package com.example.szymon.app;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.szymon.app.api.pojo.Fare;

import java.util.ArrayList;

public class LogedUserData {

    public static String USER_PHONE;
    public static String USER_PASSWORD;
    public static String USER_SURNAME;
    public static String USER_NAME;

    public static ArrayList<Fare> ALL_FARES_LIST = new ArrayList<>();
    public static ArrayList<Fare> ACCEPTED_FARES_LIST = new ArrayList<>();
    public static Thread mainTheard = new Thread();

    public static void saveCredentials(String login, String password, String firstName, String lastName, Context context) {
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("Authentication_Id", login);
        editor.putString("Authentication_Password", password);
        editor.putString("Authentication_Name", firstName);
        editor.putString("Authentication_Surname", lastName);
        editor.apply();
    }
}
