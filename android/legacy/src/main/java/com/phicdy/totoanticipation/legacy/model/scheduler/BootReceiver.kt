package com.phicdy.totoanticipation.legacy.model.scheduler

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.phicdy.totoanticipation.legacy.model.storage.GameListStorageImpl

class BootReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("Boot", "receive boot")
        if (context == null || intent == null || intent.action == null) return
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val storage = GameListStorageImpl(context)
            val deadline = storage.totoDeadline()
            Log.d("Boot", "deadline: $deadline")
            val now = System.currentTimeMillis()
            if (deadline.time == 0L || deadline.time <= now) return
            DeadlineAlarm(context).setAtNoonOf(deadline)
        }
    }
}
