package com.phicdy.totoanticipation.advertisement_provider.di

import com.phicdy.totoanticipation.admob.AdmobProvider
import com.phicdy.totoanticipation.advertisement.AdProvider
import dagger.Module
import dagger.Provides

@Module
class AdModule {

    @Provides
    fun provideAdProvider(): AdProvider = AdmobProvider()
}