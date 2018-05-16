package com.phicdy.totoanticipation

import android.app.Application
import com.phicdy.totoanticipation.model.notification.NotificationChannelFactory

class TotoAnticipationApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        NotificationChannelFactory().create(this)
    }
}