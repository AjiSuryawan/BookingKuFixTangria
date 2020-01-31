package com.tangria.spa.bookingku.Util;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.tangria.spa.bookingku.Activity.Main.MainActivity;
import com.tangria.spa.bookingku.R;

public class NotificationConfig {

    private Intent startActivityIntent;
    private Context context;

    public NotificationConfig(Context context) {
        this.context = context;
    }

    public void showNotification(String title, String desc, int NOTIFICATION_ID, String NOTIFICATION_CHANNEL_ID, String NOTIFICATION_CHANNEL_NAME) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("login", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("userid", 0);
        if (userId == 0) {
            startActivityIntent = new Intent(context, ControlClass.class);
            startActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Log.e("startactivity", "onReceive:  Control");
        } else {
            startActivityIntent = new Intent(context, MainActivity.class);
            startActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            Log.e("startactivity", "onReceive:  MainActivity");
        }

        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, startActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(desc)
                .setSmallIcon(R.mipmap.app_logo2)
                .setContentIntent(pendingIntent);
        Notification notification = notifBuilder.build();
        notification.flags = Notification.FLAG_NO_CLEAR | Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
