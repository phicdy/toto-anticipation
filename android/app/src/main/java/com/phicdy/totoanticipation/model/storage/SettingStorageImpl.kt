package com.phicdy.totoanticipation.model.storage

import android.content.Context

class SettingStorageImpl(context: Context) : SettingStorage {

    private val preferences = context.getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE)

    override var isDeadlineNotify: Boolean
        get() = preferences.getBoolean(KEY_NOTIFY_DEADLINE, true)
        set(isEnabled) = preferences.edit().putBoolean(KEY_NOTIFY_DEADLINE, isEnabled).apply()

    companion object {
        private const val KEY_PREF = "keyPrefSetting"
        private const val KEY_NOTIFY_DEADLINE = "keyNotifyDeadline"
    }
}
