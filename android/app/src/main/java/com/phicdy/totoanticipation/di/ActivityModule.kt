package com.phicdy.totoanticipation.di

import com.phicdy.totoanticipation.di_common.ActivityScope
import com.phicdy.totoanticipation.feature.totoanticipation.TotoAnticipationActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityModule {
    @ContributesAndroidInjector
    @ActivityScope
    fun contribute(): TotoAnticipationActivity
}
