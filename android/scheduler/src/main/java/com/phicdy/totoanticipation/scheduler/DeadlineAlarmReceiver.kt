package com.phicdy.totoanticipation.scheduler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.text.TextUtils

import javax.inject.Inject

class DeadlineAlarmReceiver : BroadcastReceiver() {

    @Inject
    lateinit var deadlineNotification: DeadlineNotification

    override fun onReceive(context: Context?, intent: Intent?) {
        if (context == null || intent == null || TextUtils.isEmpty(intent.action) ||
                intent.action != DeadlineAlarm.ACTION)
            return
        deadlineNotification.show(context)
    }
}
