package com.phicdy.totoanticipation.model.scheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Date;

public class DeadlineAlarm {

    static final String ACTION = "deadlineAlarm";

    public void set(@NonNull Context context, @NonNull Date date) {
        Intent i = new Intent(context, DeadlineAlarmReceiver.class);
        i.setAction(ACTION);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Log.d("Toto alarm", "Set alarm : " + date.toString());
        alm.set(AlarmManager.RTC_WAKEUP, date.getTime(), pi);
    }
}
