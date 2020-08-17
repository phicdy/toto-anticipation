package com.phicdy.totoanticipation.storage.impl

import android.content.Context
import com.phicdy.totoanticipation.storage.SettingStorage

class SettingStorageImpl(context: Context) : SettingStorage {

    private val preferences = context.getSharedPreferences(KEY_PREF, Context.MODE_PRIVATE)

    override var isDeadlineNotify: Boolean
        get() = preferences.getBoolean(KEY_NOTIFY_DEADLINE, true)
        set(isEnabled) = preferences.edit().putBoolean(KEY_NOTIFY_DEADLINE, isEnabled).apply()

    override var isPrivacyPolicyAccepted: Boolean
        get() = preferences.getBoolean(KEY_PRIVACY_POLICY_ACCEPTED, false)
        set(isEnabled) = preferences.edit().putBoolean(KEY_PRIVACY_POLICY_ACCEPTED, isEnabled).apply()

    companion object {
        private const val KEY_PREF = "keyPrefSetting"
        private const val KEY_NOTIFY_DEADLINE = "keyNotifyDeadline"
        private const val KEY_PRIVACY_POLICY_ACCEPTED = "keyPrivacyPolicyAccepted"
    }
}
