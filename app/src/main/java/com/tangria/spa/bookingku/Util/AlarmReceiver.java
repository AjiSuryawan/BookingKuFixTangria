package com.tangria.spa.bookingku.Util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    private static final String NOTIFICATION_CHANNEL_ID = "channel_id";
    private static final String NOTIFICATION_CHANNEL_NAME = "BookingKu";
    private static final int NOTIFICATION_ID = 987;

    @Override
    public void onReceive(Context context, Intent intent) {
        final String title = "Bookingku";
        final String desc = "Layanan anda akan segera dimulai dalam 2 jam lagi";

        NotificationConfig notificationConfig = new NotificationConfig(context);
        notificationConfig.showNotification(title, desc, NOTIFICATION_ID, NOTIFICATION_CHANNEL_ID, NOTIFICATION_CHANNEL_NAME);
    }
}
