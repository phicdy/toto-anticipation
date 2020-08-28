package com.phicdy.totoanticipation.intentprovider

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import com.phicdy.totoanticipation.domain.Game

interface IntentProvider {
    fun gameList(context: Context): Intent
    fun license(fragment: Fragment)
    fun setting(fragment: Fragment)
    fun teamInfo(fragment: Fragment, game: Game)
}