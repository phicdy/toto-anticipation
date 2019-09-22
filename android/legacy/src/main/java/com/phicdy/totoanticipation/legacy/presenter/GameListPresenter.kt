package com.phicdy.totoanticipation.legacy.presenter

import android.os.Handler
import android.os.Looper
import com.phicdy.totoanticipation.legacy.model.AutoAnticipation
import com.phicdy.totoanticipation.legacy.model.Game
import com.phicdy.totoanticipation.legacy.model.JLeagueRankingParser
import com.phicdy.totoanticipation.legacy.model.JLeagueRequestExecutor
import com.phicdy.totoanticipation.legacy.model.RakutenTotoInfoParser
import com.phicdy.totoanticipation.legacy.model.RakutenTotoRequestExecutor
import com.phicdy.totoanticipation.legacy.model.RakutenTotoTopParser
import com.phicdy.totoanticipation.legacy.model.TeamInfoMapper
import com.phicdy.totoanticipation.legacy.model.Toto
import com.phicdy.totoanticipation.legacy.model.scheduler.DeadlineAlarm
import com.phicdy.totoanticipation.legacy.model.storage.GameListStorage
import com.phicdy.totoanticipation.legacy.model.storage.SettingStorage
import com.phicdy.totoanticipation.legacy.view.GameListView
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.Executor

class GameListPresenter(private val view: GameListView,
                        private val rakutenTotoRequestExecutor: RakutenTotoRequestExecutor,
                        private val jLeagueRequestExecutor: JLeagueRequestExecutor,
                        private val storage: GameListStorage,
                        private val alarm: DeadlineAlarm,
                        private val settingStorage: SettingStorage
) : Presenter, RakutenTotoRequestExecutor.RakutenTotoRequestCallback, JLeagueRequestExecutor.JLeagueRequestCallback {
    private var toto: Toto = Toto(Toto.DEFAULT_NUMBER, Date())
    private var games: List<Game> = listOf()
    private var j1ranking: Map<String, Int> = HashMap()
    private var j2ranking: Map<String, Int> = HashMap()
    private var j3ranking: Map<String, Int> = HashMap()

    override fun onCreate() {
        view.startProgress()
        rakutenTotoRequestExecutor.fetchRakutenTotoTopPage(this)
        if (!settingStorage.isPrivacyPolicyAccepted) view.showPrivacyPolicyDialog()
    }

    override fun onResponseTotoTop(response: Response<ResponseBody>) {
        try {
            val body = response.body()?.string()
            body?.let {
                toto = RakutenTotoTopParser().latestToto(body)
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
                    jLeagueRequestExecutor.fetchJ1Ranking(this)
                } else {
                    view.stopProgress()
                    view.initList()
                    storage.store(toto, games)
                }
            } ?: view.stopProgress()
        } catch (e: IOException) {
            e.printStackTrace()
            view.stopProgress()
        }
    }

    override fun onFailureTotoTop(throwable: Throwable) {
        view.stopProgress()
    }

    override fun onResponseJ1Ranking(response: Response<ResponseBody>) {
        try {
            response.body()?.let {
                j1ranking = JLeagueRankingParser().ranking(it.string())
                jLeagueRequestExecutor.fetchJ2Ranking(this)
            } ?: view.stopProgress()
        } catch (e: IOException) {
            e.printStackTrace()
            view.stopProgress()
        }

    }

    override fun onFailureJ1Ranking(call: Call<ResponseBody>, throwable: Throwable) {
        view.stopProgress()
    }

    override fun onResponseJ2Ranking(response: Response<ResponseBody>) {
        try {
            response.body()?.let {
                j2ranking = JLeagueRankingParser().ranking(it.string())
                jLeagueRequestExecutor.fetchJ3Ranking(this)
            } ?: view.stopProgress()
        } catch (e: IOException) {
            e.printStackTrace()
            view.stopProgress()
        }

    }

    override fun onFailureJ2Ranking(call: Call<ResponseBody>, throwable: Throwable) {
        view.stopProgress()
    }

    override fun onResponseJ3Ranking(response: Response<ResponseBody>) {
        try {
            response.body()?.let {
                j3ranking = JLeagueRankingParser().ranking(it.string())
                rakutenTotoRequestExecutor.fetchRakutenTotoInfoPage(toto.number, this)
            } ?: view.stopProgress()
        } catch (e: IOException) {
            e.printStackTrace()
            view.stopProgress()
        }

    }

    override fun onFailureJ3Ranking(call: Call<ResponseBody>, throwable: Throwable) {
        view.stopProgress()
    }

    override fun onResponseTotoInfo(response: Response<ResponseBody>) {
        view.stopProgress()
        try {
            response.body()?.let {
                val body = it.string()
                val parser = RakutenTotoInfoParser()

                // Set title
                if (toto.number != Toto.DEFAULT_NUMBER) {
                    val format = SimpleDateFormat("MM/dd ", Locale.JAPAN)
                    view.setTitleFrom(toto.number, format.format(toto.deadline) + parser.deadlineTime(body))
                }

                // Parse games
                games = parser.games(body)
                for (game in games) {
                    val homeFullName = TeamInfoMapper().fullNameForJLeagueRanking(game.homeTeam)
                    val awayFullName = TeamInfoMapper().fullNameForJLeagueRanking(game.awayTeam)
                    var homeRank: Int? = j1ranking[homeFullName]
                    var awayRank: Int? = j1ranking[awayFullName]
                    if (homeRank == null || awayRank == null) {
                        homeRank = j2ranking[homeFullName]
                        awayRank = j2ranking[awayFullName]
                        if (homeRank == null || awayRank == null) {
                            homeRank = j3ranking[homeFullName]
                            awayRank = j3ranking[awayFullName]
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

            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun onFailureTotoInfo(throwable: Throwable) {
        view.stopProgress()
    }

    override fun onResume() {

    }

    override fun onPause() {

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
