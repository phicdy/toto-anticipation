package com.phicdy.totoanticipation.model.scheduler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.TextUtils

import com.phicdy.totoanticipation.model.notification.DeadlineNotification

class DeadlineAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null || TextUtils.isEmpty(intent.action) ||
                intent.action != DeadlineAlarm.ACTION)
            return
        DeadlineNotification.show(context)
    }
}
