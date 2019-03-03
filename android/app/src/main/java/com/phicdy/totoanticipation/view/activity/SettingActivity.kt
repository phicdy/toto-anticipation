package com.phicdy.totoanticipation.view.activity

import android.os.Bundle
import android.preference.PreferenceActivity

import com.phicdy.totoanticipation.view.fragment.SettingFragment


class SettingActivity : PreferenceActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragment = SettingFragment()
        fragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment)
                .commit()
    }
}
