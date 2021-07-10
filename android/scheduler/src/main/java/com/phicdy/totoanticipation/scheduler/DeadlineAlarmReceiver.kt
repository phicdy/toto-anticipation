package com.phicdy.totoanticipation.scheduler

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import dagger.android.DaggerBroadcastReceiver
import javax.inject.Inject

class DeadlineAlarmReceiver : DaggerBroadcastReceiver() {

    @Inject
    lateinit var deadlineNotification: DeadlineNotification

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        if (context == null || intent == null || TextUtils.isEmpty(intent.action) ||
                intent.action != DeadlineAlarm.ACTION)
            return
        deadlineNotification.show(context)
    }
}
