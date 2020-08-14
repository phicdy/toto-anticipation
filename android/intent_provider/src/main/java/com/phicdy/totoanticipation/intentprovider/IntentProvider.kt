package com.phicdy.totoanticipation.intentprovider

import android.content.Context
import android.content.Intent

interface IntentProvider {
    fun gameList(context: Context): Intent
}