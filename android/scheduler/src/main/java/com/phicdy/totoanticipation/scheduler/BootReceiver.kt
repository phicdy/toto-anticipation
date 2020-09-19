package com.phicdy.totoanticipation.scheduler

import android.content.Context
import android.content.Intent
import android.util.Log
import com.phicdy.totoanticipation.storage.GameListStorage
import dagger.android.DaggerBroadcastReceiver
import javax.inject.Inject

class BootReceiver : DaggerBroadcastReceiver() {

    @Inject
    lateinit var storage: GameListStorage

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)
        Log.d("Boot", "receive boot")
        if (context == null || intent == null || intent.action == null) return
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            val deadline = storage.totoDeadline()
            Log.d("Boot", "deadline: $deadline")
            val now = System.currentTimeMillis()
            if (deadline.time == 0L || deadline.time <= now) return
            DeadlineAlarm(context).setAtNoonOf(deadline)
        }
    }
}
