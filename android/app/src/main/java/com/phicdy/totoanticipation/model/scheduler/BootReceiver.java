package com.phicdy.totoanticipation.model.scheduler;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.phicdy.totoanticipation.model.storage.GameListStorage;
import com.phicdy.totoanticipation.model.storage.GameListStorageImpl;

import java.util.Date;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Boot", "receive boot");
        if (context == null || intent == null || intent.getAction() == null) return;
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {
            GameListStorage storage = new GameListStorageImpl(context);
            Date deadline = storage.totoDeadline();
            Log.d("Boot", "deadline: " + deadline.toString());
            long now = System.currentTimeMillis();
            if (deadline.getTime() == 0 || deadline.getTime() <= now) return;
            new DeadlineAlarm(context).setAtNoonOf(deadline);
        }
    }
}
