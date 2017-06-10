package com.example.szymon.app.FirebaseCloudMessaging;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.szymon.app.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static android.content.ContentValues.TAG;

public class NotificationService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData() != null ) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData().toString());
            Map<String, String> data = remoteMessage.getData();
            if (data.get("type").equals("CMFareRequest")) {
                Intent intent = new Intent("CMFareRequest");
                JSONObject jsonData = new JSONObject();
                try {
                    jsonData.put("fareID", data.get("fareID"));
                    jsonData.put("clientPhone", data.get("clientPhone"));
                    jsonData.put("startingPoint", new JSONObject(data.get("startingPoint")));
                    jsonData.put("endingPoint", new JSONObject(data.get("endingPoint")));
                    jsonData.put("startingDate", data.get("startingDate"));
                    System.out.println(jsonData.toString());
                    intent.putExtra("payload", jsonData.toString());
                    LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        if (remoteMessage.getNotification() != null ) {
            showNotification(remoteMessage);
        }
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }

    public void showNotification(RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        int mId = remoteMessage.getTtl();
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_local_taxi_black_24dp)
                        .setContentTitle(title)
                        .setContentText(body);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(mId, mBuilder.build());
    }
}
