package com.phicdy.totoanticipation

import com.phicdy.totoanticipation.di.DaggerAppComponent
import com.phicdy.totoanticipation.model.notification.NotificationChannelFactory
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class TotoAnticipationApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerAppComponent.factory().create(this)

    override fun onCreate() {
        super.onCreate()
        NotificationChannelFactory().create(this)
    }
}