package com.phicdy.totoanticipation.di

import com.phicdy.totoanticipation.admob.di.AdComponent
import com.phicdy.totoanticipation.admob.di.DaggerAdComponent
import com.phicdy.totoanticipation.advertisement.AdProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AdComponentModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideAdProvider(
            adComponent: AdComponent
    ): AdProvider {
        return adComponent.adProvider()
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideAdComponent() = DaggerAdComponent.factory().create()
}