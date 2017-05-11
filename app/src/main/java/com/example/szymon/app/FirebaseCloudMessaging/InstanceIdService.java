package com.example.szymon.app.FirebaseCloudMessaging;

/**
 * Created by dzaku_000 on 2017-05-11.
 */

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static android.content.ContentValues.TAG;

public class InstanceIdService extends FirebaseInstanceIdService {

    private static String registrationToken;

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        registrationToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + registrationToken);
        //sendRegistrationToServer(registrationToken);
    }

    public static String getRegistrationToken() {
        return registrationToken;
    }
}
