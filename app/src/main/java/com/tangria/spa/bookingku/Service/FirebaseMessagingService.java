package com.tangria.spa.bookingku.Service;

import android.content.SharedPreferences;
import android.util.Log;
import com.tangria.spa.bookingku.Network.BookingClient;
import com.tangria.spa.bookingku.Network.BookingService;
import com.tangria.spa.bookingku.Util.NotificationConfig;
import com.google.firebase.messaging.RemoteMessage;
import org.json.JSONObject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService {
    private static final String NOTIFICATION_CHANNEL_ID = "channel_id";
    private static final String NOTIFICATION_CHANNEL_NAME = "BookingKu";
    private static final int NOTIFICATION_ID = 897;

    private static final String TAG = FirebaseMessagingService.class.getSimpleName();
    @Override
    public void onNewToken(String s) {
        SharedPreferences preferences = getSharedPreferences("firebase_token", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("firebase_token", s);
        editor.apply();
        BookingService service = BookingClient.getRetrofit().create(BookingService.class);
        int id = preferences.getInt("userid", 0);
        if(id != 0) {
            Call<Void> call = service.refreshToken(id, s);
            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    Log.e("Refresh token", "onResponse: success refresh token");
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    t.printStackTrace();
                    Log.e("FirebasToken", "onFailure: " + t.getMessage());
                }
            });
        }
        Log.e("firebase_token", "onNewToken: " + s);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "onMessageReceived: message received" );
            try {
                JSONObject jsonObject = new JSONObject(remoteMessage.getData());
                sendPushNotification(jsonObject);
            } catch (Exception e) {
                Log.e(TAG, "onMessageReceived: " + e.getMessage() );
            }

        }
    }

    public void sendPushNotification(JSONObject json) {
        try {
            Log.e(TAG, "sendPushNotification: " + json );
            String title = json.getString("title");
            String message = json.getString("message");
            NotificationConfig notificationConfig = new NotificationConfig(getApplicationContext());
            notificationConfig.showNotification(title, message, NOTIFICATION_ID, NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME);
        } catch (Exception e) {
            Log.e(TAG, "sendPushNotification: " + e.getMessage() );
            e.printStackTrace();
        }
    }
}
