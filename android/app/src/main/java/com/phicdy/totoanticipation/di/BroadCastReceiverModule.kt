package com.phicdy.totoanticipation.di

import com.phicdy.totoanticipation.scheduler.BootReceiver
import com.phicdy.totoanticipation.scheduler.DeadlineAlarmReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface BroadCastReceiverModule {

    @ContributesAndroidInjector
    fun contributeBootReceiver(): BootReceiver

    @ContributesAndroidInjector
    fun contributeDeadlineAlarmReceiver(): DeadlineAlarmReceiver
}
