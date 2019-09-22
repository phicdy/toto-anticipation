package com.phicdy.totoanticipation.advertisement_provider.di

import com.phicdy.totoanticipation.advertisement.AdProvider
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(
        modules = [AdModule::class]
)
interface AdComponent {
    fun adProvider(): AdProvider

    @Component.Factory
    interface Factory {
        fun create(): AdComponent
    }
}