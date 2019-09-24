package com.phicdy.totoanticipation.api.di

import com.phicdy.totoanticipation.api.JLeagueRankingApi
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
            ApiModule::class
        ]
)
interface ApiComponent {
    fun jLeagueRankingApi(): JLeagueRankingApi

    @Component.Factory
    interface Factory {
        fun create(): ApiComponent
    }
}