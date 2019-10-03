package com.phicdy.totoanticipation.api.di

import com.phicdy.totoanticipation.api.JLeagueRankingApi
import com.phicdy.totoanticipation.api.RakutenTotoApi
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
    fun rakutenTotoApi(): RakutenTotoApi

    @Component.Factory
    interface Factory {
        fun create(): ApiComponent
    }
}