package com.phicdy.totoanticipation.scheduler

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.phicdy.totoanticipation.intentprovider.IntentProvider
import javax.inject.Inject

class DeadlineNotification @Inject constructor(
        private val intentProvider: IntentProvider
) {

    fun show(context: Context) {
        val intent = intentProvider.gameList(context)
        val pi = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = NotificationCompat.Builder(context, ChannelID.DEADLINE.name)
                .setAutoCancel(true) // Delete notification when user taps
                .setContentTitle(context.getString(R.string.app_name))
                .setTicker(context.getString(R.string.notification_deadline_message)) // Message when notification shows for ~4.4
                .setContentText(context.getString(R.string.notification_deadline_message))
                .setSmallIcon(R.mipmap.ic_launcher_material)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.mipmap.ic_launcher))
                .setContentIntent(pi)
                .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                .build()
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(ID, notification)
    }

    companion object {
        private const val ID = 1000

    }
}
