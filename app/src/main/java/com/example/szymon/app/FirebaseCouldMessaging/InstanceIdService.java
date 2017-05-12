package com.example.szymon.app.FirebaseCouldMessaging;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;
import static com.example.szymon.app.FirebaseCouldMessaging.Connect.call;


public class InstanceIdService extends FirebaseInstanceIdService {

    private static String registrationToken;

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        registrationToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + registrationToken);
        //sendRegistrationToServer(registrationToken);
    }

    private void sendRegistrationToServer(final String refreshedToken) {

        new Thread() {
            @Override
            public void run() {
                String resp = call("https://uberalles.herokuapp.com/test/messaging?to=" + refreshedToken + "&notification=Zapomniałeś hasła? Twój problem");
                Log.d(TAG, "RESPONSE: " + resp);
            }
        }.start();
    }

    public static String getRegistrationToken() {
        return registrationToken;
    }
}
