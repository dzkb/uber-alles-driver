package com.example.szymon.app.FirebaseCloudMessaging;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.example.szymon.app.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import static android.content.ContentValues.TAG;
import static com.example.szymon.app.DriverActivity.giveMeContext;

public class NotificationService extends FirebaseMessagingService {

    private static final String CANCEL_MESSAGE = "Twój przejazd został anulowany przez kierowce";
    private static final String MESSAGE_TITLE = "UWAGA!";
    private static final String CONFIRM_MESSAGE = "Jeden z kierowców podjął Twój przejazd. ";
    private static final String CONFIRM_SUBMESSAGE = "Kliknij, aby wyświetlić dodatkowe informacje";
    private static final String TOP_BAR_TITLE = "Aktualizacja statusu Twojego przejazdu";

    enum Type {
        CMLocalisationUpdate,
        CMFareConfirmation,
        CMFareCancellation
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData() != null) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData().toString());
            Type type = getTypeFromMessage(remoteMessage.getData());
            Map<String, String> data = remoteMessage.getData();
            switch (type) {
                case CMFareCancellation:
                    /* Notification + uruchomienie popup'a wyświetlającego informacje o anulowaniu
                    - po obydwu stronach takie samo działanie
                    */
                    String phoneNumber = data.get("phoneNumber");
                    String id = data.get("id");
                    String which = data.get("origin");
                    createNotification(Type.CMFareCancellation.toString(), CANCEL_MESSAGE, MESSAGE_TITLE, Log.class, "Szukaj nowego kierowcy",
                            phoneNumber, id, which);
                    break;
            }
        }
        if (remoteMessage.getNotification() != null) {
            showNotification(remoteMessage);
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        Log.d(TAG, "From: " + remoteMessage.getFrom());
    }

    public void showNotification(RemoteMessage remoteMessage) {
        String title = remoteMessage.getNotification().getTitle();
        String body = remoteMessage.getNotification().getBody();
        int mId = remoteMessage.getTtl();
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_directions_black_24dp)
                        .setContentTitle(title)
                        .setContentText(body);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
// mId allows you to update the notification later on.
        mNotificationManager.notify(mId, mBuilder.build());
    }

    private void createNotification(String type, String message, String title, Class<?> cls, String subText, String... params) {
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification myNotication;
        Intent intent = new Intent(giveMeContext(), cls);
        intent.putExtra("typ", type);
        intent.putExtra("parameters", params);
        PendingIntent pendingIntent = PendingIntent.getActivity(giveMeContext(), 1, intent, 0);

        Notification.Builder builder = new Notification.Builder(giveMeContext());

        builder.setAutoCancel(false);
        builder.setTicker(TOP_BAR_TITLE);
        builder.setContentTitle(title);
        builder.setContentText(message);
        builder.setSmallIcon(R.drawable.ic_local_taxi_black_1_24dp);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(true);
        builder.setSubText(subText);   //API level 16
        builder.setNumber(100);
        builder.build();

        myNotication = builder.getNotification();
        manager.notify(11, myNotication);
    }

    private Type getTypeFromMessage(Map<String, String> data) {
        String type = data.get("type");
        if (type.equals(Type.CMLocalisationUpdate.name())) return Type.CMLocalisationUpdate;
        else if (type.equals(Type.CMFareConfirmation.name())) return Type.CMFareConfirmation;
        else return Type.CMFareCancellation;
    }

}
