package com.phicdy.totoanticipation.legacy.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.phicdy.totoanticipation.legacy.view.fragment.SettingFragment


class SettingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fragment = SettingFragment()
        supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, fragment)
                .commit()
    }
}
