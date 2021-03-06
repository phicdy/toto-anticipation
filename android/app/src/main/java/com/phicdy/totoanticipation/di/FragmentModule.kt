package com.phicdy.totoanticipation.di

import com.phicdy.totoanticipation.di_common.FragmentScope
import com.phicdy.totoanticipation.feature.gamelist.GameListFragment
import com.phicdy.totoanticipation.feature.teaminfo.TeamInfoFragment
import com.phicdy.totoanticipation.setting.SettingFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentModule {
    @ContributesAndroidInjector(modules = [GameListFragment.Module::class])
    @FragmentScope
    fun contributeGameListFragment(): GameListFragment

    @ContributesAndroidInjector
    @FragmentScope
    fun contributeTeamInfoFragment(): TeamInfoFragment

    @ContributesAndroidInjector
    @FragmentScope
    fun contributeSettingFragment(): SettingFragment
}
