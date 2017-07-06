package com.phicdy.totoanticipation.model.storage;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class SettingStorageImpl implements SettingStorage {

    private final SharedPreferences preferences;
    private static final String KEY_PREF = "keyPrefSetting";
    private static final String KEY_NOTIFY_DEADLINE = "keyNotifyDeadline";
    public SettingStorageImpl(@NonNull Context context) {
        preferences = context.getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE);
    }

    @Override
    public boolean isDeadlineNotify() {
        return preferences.getBoolean(KEY_NOTIFY_DEADLINE, true);
    }

    @Override
    public void setDeadlineNotify(boolean isEnabled) {
        preferences.edit().putBoolean(KEY_NOTIFY_DEADLINE, isEnabled).apply();
    }
}
