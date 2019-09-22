package com.phicdy.totoanticipation.di

import com.phicdy.totoanticipation.legacy.view.activity.GameListActivity
import com.phicdy.totoanticipation.legacy.view.activity.TeamInfoActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeGameListActivity(): GameListActivity

    @ContributesAndroidInjector
    abstract fun contributeTeamInfoActivity(): TeamInfoActivity
}
