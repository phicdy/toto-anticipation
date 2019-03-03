package com.phicdy.totoanticipation.model.notification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.support.v4.app.NotificationCompat
import android.support.v4.content.ContextCompat
import com.phicdy.totoanticipation.R
import com.phicdy.totoanticipation.view.activity.GameListActivity

class DeadlineNotification {

    companion object {
        private const val ID = 1000

        fun show(context: Context) {
            val intent = Intent(context, GameListActivity::class.java)
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
    }
}
