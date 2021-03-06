package com.phicdy.totoanticipation.di

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.phicdy.totoanticipation.GameListActivity
import com.phicdy.totoanticipation.R
import com.phicdy.totoanticipation.TotoAnticipationApplication
import com.phicdy.totoanticipation.domain.Game
import com.phicdy.totoanticipation.feature.gamelist.GameListFragmentDirections
import com.phicdy.totoanticipation.feature.totoanticipation.TotoAnticipationActivity
import com.phicdy.totoanticipation.intentprovider.IntentProvider
import com.phicdy.totoanticipation.storage.GameListStorage
import com.phicdy.totoanticipation.storage.SettingStorage
import com.phicdy.totoanticipation.storage.impl.GameListStorageImpl
import com.phicdy.totoanticipation.storage.impl.SettingStorageImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object AppModule {
    @JvmStatic
    @Provides
    @Singleton
    fun provideApp(application: TotoAnticipationApplication): Application = application

    @JvmStatic
    @Provides
    @Singleton
    fun provideIntentProvider(): IntentProvider = object : IntentProvider {
        override fun gameList(context: Context): Intent = Intent(context, GameListActivity::class.java)
        override fun totoAnticipation(context: Context, totoNum: String): Intent = Intent(context, TotoAnticipationActivity::class.java).apply {
            putExtra(TotoAnticipationActivity.KEY_TOTO_NUM, totoNum)
        }

        override fun license(fragment: Fragment) {
            fragment.findNavController().navigate(R.id.action_settingFragment_to_licenseFragment)
        }

        override fun setting(fragment: Fragment) {
            fragment.findNavController().navigate(R.id.action_gameListFragment_to_settingFragment)
        }

        override fun teamInfo(fragment: Fragment, game: Game) {
            fragment.findNavController().navigate(
                    GameListFragmentDirections.actionGameListFragmentToTeamInfoFragment(game.homeTeam, game.awayTeam)
            )
        }
    }

    @JvmStatic
    @Provides
    @Singleton
    fun provideGameListStorage(application: TotoAnticipationApplication): GameListStorage = GameListStorageImpl(application)

    @JvmStatic
    @Provides
    @Singleton
    fun provideSettingStorage(application: TotoAnticipationApplication): SettingStorage = SettingStorageImpl(application)
}