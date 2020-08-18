package com.phicdy.totoanticipation.intentprovider

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment

interface IntentProvider {
    fun gameList(context: Context): Intent
    fun license(fragment: Fragment)
}