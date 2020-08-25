package com.phicdy.totoanticipation.feature.gamelist

import android.os.Handler
import android.os.Looper
import com.phicdy.totoanticipation.domain.AutoAnticipation
import com.phicdy.totoanticipation.domain.Game
import com.phicdy.totoanticipation.domain.Team
import com.phicdy.totoanticipation.domain.TeamInfoMapper
import com.phicdy.totoanticipation.domain.Toto
import com.phicdy.totoanticipation.domain.TotoNumber
import com.phicdy.totoanticipation.repository.JLeagueRepository
import com.phicdy.totoanticipation.repository.RakutenTotoRepository
import com.phicdy.totoanticipation.scheduler.DeadlineAlarm
import com.phicdy.totoanticipation.storage.GameListStorage
import com.phicdy.totoanticipation.storage.SettingStorage
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.Executor
import javax.inject.Inject

class GameListPresenter @Inject constructor(
        private val view: GameListView,
        private val jLeagueRepository: JLeagueRepository,
        private val rakutenTotoRepository: RakutenTotoRepository,
        private val storage: GameListStorage,
        private val alarm: DeadlineAlarm,
        private val settingStorage: SettingStorage
) {
    private var toto: Toto = Toto(Toto.DEFAULT_NUMBER, Date())
    private var games: List<Game> = listOf()
    private var j1ranking: List<Team> = emptyList()
    private var j2ranking: List<Team> = emptyList()
    private var j3ranking: List<Team> = emptyList()

    suspend fun onCreate() {
        view.startProgress()
        val t = rakutenTotoRepository.fetchLatestToto()
        t?.let {
            toto = it
            if (toto.number == Toto.DEFAULT_NUMBER) {
                view.stopProgress()
                view.hideList()
                view.hideFab()
                view.hideAnticipationMenu()
                view.showEmptyView()
                return
            }
            if (settingStorage.isDeadlineNotify) alarm.setAtNoonOf(toto.deadline)
            games = storage.list(toto.number)
            if (games.isEmpty()) {
                val j1rankingDeferred = coroutineScope { async { jLeagueRepository.fetchJ1Ranking() } }
                val j2rankingDeferred = coroutineScope { async { jLeagueRepository.fetchJ2Ranking() } }
                val j3rankingDeferred = coroutineScope { async { jLeagueRepository.fetchJ3Ranking() } }

                val totoInfo = rakutenTotoRepository.fetchTotoInfo(TotoNumber(toto.number))
                totoInfo?.let {
                    // Set title
                    val format = SimpleDateFormat("MM/dd ", Locale.JAPAN)
                    view.setTitleFrom(toto.number, format.format(toto.deadline) + totoInfo.deadline.toString())

                    j1ranking = j1rankingDeferred.await()
                    j2ranking = j2rankingDeferred.await()
                    j3ranking = j3rankingDeferred.await()

                    games = totoInfo.games
                    for (game in games) {
                        val homeFullName = TeamInfoMapper().fullNameForJLeagueRanking(game.homeTeam)
                        val awayFullName = TeamInfoMapper().fullNameForJLeagueRanking(game.awayTeam)
                        var homeRank = j1ranking.firstOrNull { team -> team.name == homeFullName }?.ranking
                        var awayRank = j1ranking.firstOrNull { team -> team.name == awayFullName }?.ranking
                        if (homeRank == null || awayRank == null) {
                            homeRank = j2ranking.firstOrNull { team -> team.name == homeFullName }?.ranking
                            awayRank = j2ranking.firstOrNull { team -> team.name == awayFullName }?.ranking
                            if (homeRank == null || awayRank == null) {
                                homeRank = j3ranking.firstOrNull { team -> team.name == homeFullName }?.ranking
                                awayRank = j3ranking.firstOrNull { team -> team.name == awayFullName }?.ranking
                            }
                        }
                        if (homeRank == null || awayRank == null) {
                            continue
                        }
                        game.homeRanking = homeRank
                        game.awayRanking = awayRank
                    }
                    view.initList()
                    storage.store(toto, games)
                    view.stopProgress()
                } ?: stopAndShowEmptyView()
            } else {
                view.stopProgress()
                view.initList()
            }
        } ?: view.stopProgress()
        if (!settingStorage.isPrivacyPolicyAccepted) view.showPrivacyPolicyDialog()
    }

    private fun stopAndShowEmptyView() {
        view.showEmptyView()
        view.stopProgress()
    }

    fun onHomeRadioButtonClicked(position: Int, isChecked: Boolean) {
        onRadioButtonClicked(position, isChecked, Game.Anticipation.HOME)
    }

    fun onAwayRadioButtonClicked(position: Int, isChecked: Boolean) {
        onRadioButtonClicked(position, isChecked, Game.Anticipation.AWAY)
    }

    fun onDrawRadioButtonClicked(position: Int, isChecked: Boolean) {
        onRadioButtonClicked(position, isChecked, Game.Anticipation.DRAW)
    }

    private fun onRadioButtonClicked(position: Int, isChecked: Boolean, anticipation: Game.Anticipation) {
        if (position >= games.size || position < 0) return
        if (isChecked) {
            games[position].anticipation = anticipation
            storage.store(toto, games)
        }
    }

    fun gameAt(position: Int): Game =
            if (position >= games.size || position < 0) throw IndexOutOfBoundsException("Invalid game position: $position, game size: " + games.size)
            else games[position]

    fun gameSize(): Int = games.size

    fun onFabClicked() {
        storage.store(toto, games)
        view.startTotoAnticipationActivity(toto.number)
    }

    fun onOptionsSettingSelected() {
        view.goToSetting()
    }

    fun onOptionsAutoAnticipationSelected() {
        for (game in games) {
            if (game.homeRanking == Game.defaultRank || game.awayRanking == Game.defaultRank) {
                view.showAnticipationNotSupport()
                return
            }
        }
        view.showAnticipationStart()
        view.startProgress()
        object : Thread() {
            override fun run() {
                sleep(4000)
                AutoAnticipation().exec(games)
                MainThreadExecutor().execute { finishAnticipation() }
            }
        }.start()
    }

    fun finishAnticipation() {
        view.stopProgress()
        view.showAnticipationFinish()
        view.notifyDataSetChanged()
        storage.store(toto, games)
    }

    fun onPrivacyPolicyAccepted() {
        settingStorage.isPrivacyPolicyAccepted = true
    }

    class MainThreadExecutor : Executor {
        private val handler: Handler = Handler(Looper.getMainLooper())

        override fun execute(r: Runnable) {
            handler.post(r)
        }
    }
}
