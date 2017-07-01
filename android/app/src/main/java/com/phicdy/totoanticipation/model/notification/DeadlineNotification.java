package com.phicdy.totoanticipation.model.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.NotificationCompat;

import com.phicdy.totoanticipation.R;
import com.phicdy.totoanticipation.view.activity.GameListActivity;

public class DeadlineNotification {
    private static final int ID = 1000;
    public static void show(@NonNull Context context) {
        Intent intent = new Intent(context, GameListActivity.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new NotificationCompat.Builder(context)
                .setAutoCancel(true) // Delete notification when user taps
                .setContentTitle(context.getString(R.string.app_name))
                .setTicker(context.getString(R.string.notification_deadline_message)) // Message when notification shows for ~4.4
                .setContentText(context.getString(R.string.notification_deadline_message))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .build();
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(ID, notification);
    }

    public static void dismiss(@NonNull Context context) {
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(ID);
    }
}
