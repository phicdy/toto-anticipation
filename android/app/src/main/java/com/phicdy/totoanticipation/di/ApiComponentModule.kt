package com.phicdy.totoanticipation.di

import com.phicdy.totoanticipation.api.di.ApiComponent
import com.phicdy.totoanticipation.api.di.DaggerApiComponent
import com.phicdy.totoanticipation.repository.JLeagueRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object ApiComponentModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideApi(
            apiComponent: ApiComponent
    ): JLeagueRepository {
        return apiComponent.jLeagueRankingApi()
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideApiComponent() = DaggerApiComponent.factory().create()
}