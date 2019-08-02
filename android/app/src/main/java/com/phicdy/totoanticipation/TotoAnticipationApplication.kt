package com.phicdy.totoanticipation

import com.phicdy.totoanticipation.advertisement.AdProvider
import com.phicdy.totoanticipation.di.DaggerAppComponent
import com.phicdy.totoanticipation.model.notification.NotificationChannelFactory
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Inject

class TotoAnticipationApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerAppComponent.factory().create(this)

    @Inject
    lateinit var provider: AdProvider

    override fun onCreate() {
        super.onCreate()
        NotificationChannelFactory().create(this)
        provider.init(this)
    }
}