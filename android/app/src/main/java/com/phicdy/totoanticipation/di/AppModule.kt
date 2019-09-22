package com.phicdy.totoanticipation.di

import android.app.Application
import com.phicdy.totoanticipation.TotoAnticipationApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideApp(application: TotoAnticipationApplication): Application = application
}