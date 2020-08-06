package com.phicdy.totoanticipation.di

import com.phicdy.totoanticipation.TotoAnticipationApplication
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(
        modules = [
            AndroidInjectionModule::class,
            AppModule::class,
            FragmentModule::class,
            AdComponentModule::class,
            ApiComponentModule::class
        ]
)
interface AppComponent : AndroidInjector<TotoAnticipationApplication> {
    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<TotoAnticipationApplication>
}