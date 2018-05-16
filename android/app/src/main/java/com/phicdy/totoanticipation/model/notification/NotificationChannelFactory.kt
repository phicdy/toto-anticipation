package com.phicdy.totoanticipation.model.notification

import android.app.NotificationManager
import android.app.NotificationChannel
import android.content.Context
import android.os.Build
import com.phicdy.totoanticipation.R


class NotificationChannelFactory {

    fun create(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.notification_channel_deadline)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(ChannelID.DEADLINE.name, name, importance)
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}