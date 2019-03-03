package com.phicdy.totoanticipation.model.scheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.phicdy.totoanticipation.model.notification.DeadlineNotification;

public class DeadlineAlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (context == null || intent == null || TextUtils.isEmpty(intent.getAction()) ||
                !intent.getAction().equals(DeadlineAlarm.ACTION)) return;
        DeadlineNotification.Companion.show(context);
    }
}
