package com.phicdy.totoanticipation.scheduler

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log

import java.util.Calendar
import java.util.Date

class DeadlineAlarm(private val context: Context) {

    fun setAtNoonOf(deadline: Date) {
        val calendar = Calendar.getInstance().apply {
            time = deadline
            set(Calendar.HOUR_OF_DAY, 12)
            set(Calendar.MINUTE, 0)
        }
        val now = System.currentTimeMillis()
        if (now >= calendar.timeInMillis) return
        val alm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        Log.d("Toto alarm", "Set alarm : " + calendar.time.toString())
        alm?.set(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, generateIntent())
    }

    fun cancel() {
        val alm = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager?
        alm?.cancel(generateIntent())
    }

    private fun generateIntent(): PendingIntent {
        val i = Intent(context, DeadlineAlarmReceiver::class.java).apply {
            action = ACTION
        }
        return PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
    }

    companion object {
        internal const val ACTION = "deadlineAlarm"
    }
}
