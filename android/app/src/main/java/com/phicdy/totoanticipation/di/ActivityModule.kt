package com.phicdy.totoanticipation.di

import com.phicdy.totoanticipation.view.activity.GameListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeGameListActivity(): GameListActivity
}
