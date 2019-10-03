package com.phicdy.totoanticipation.api.di

import com.phicdy.totoanticipation.api.JLeagueService
import com.phicdy.totoanticipation.api.RakutenTotoService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
class ApiModule {

    @Provides
    @Singleton
    fun provideJLeagueService(): JLeagueService = Retrofit.Builder()
            .baseUrl("https://www.jleague.jp/")
            .build()
            .create(JLeagueService::class.java)

    @Provides
    @Singleton
    fun provideRakutenTotoService(): RakutenTotoService = Retrofit.Builder()
            .baseUrl("https://toto.rakuten.co.jp/")
            .build()
            .create(RakutenTotoService::class.java)
}