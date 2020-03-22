package com.phicdy.totoanticipation.di

import com.phicdy.totoanticipation.legacy.view.fragment.TeamInfoFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector
    abstract fun contributeTeamInfoActivity(): TeamInfoFragment
}
