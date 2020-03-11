package com.phicdy.totoanticipation.di

import com.phicdy.totoanticipation.legacy.view.activity.TeamInfoActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeTeamInfoActivity(): TeamInfoActivity
}
