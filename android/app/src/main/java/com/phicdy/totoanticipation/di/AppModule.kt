package com.phicdy.totoanticipation.di

import android.app.Application
import android.content.Context
import android.content.Intent
import com.phicdy.totoanticipation.TotoAnticipationApplication
import com.phicdy.totoanticipation.intentprovider.IntentProvider
import com.phicdy.totoanticipation.legacy.view.activity.GameListActivity
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideApp(application: TotoAnticipationApplication): Application = application

    @JvmStatic
    @Provides
    @Singleton
    fun provideIntentProvider(): IntentProvider = object : IntentProvider {
        override fun gameList(context: Context): Intent = Intent(context, GameListActivity::class.java)
    }
}