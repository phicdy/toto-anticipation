package com.phicdy.totoanticipation.model.scheduler;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class DeadlineAlarm {

    private final Context context;
    static final String ACTION = "deadlineAlarm";

    public DeadlineAlarm(@NonNull Context context) {
        this.context = context;
    }

    public void set5hoursBefore(@NonNull Date deadline) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(deadline);
        calendar.add(Calendar.HOUR_OF_DAY, -5);
        AlarmManager alm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Log.d("Toto alarm", "Set alarm : " + calendar.getTime().toString());
        alm.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), generateIntent());
    }

    public void cancel() {
        AlarmManager alm = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alm.cancel(generateIntent());
    }

    private PendingIntent generateIntent() {
        Intent i = new Intent(context, DeadlineAlarmReceiver.class);
        i.setAction(ACTION);
        return PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
