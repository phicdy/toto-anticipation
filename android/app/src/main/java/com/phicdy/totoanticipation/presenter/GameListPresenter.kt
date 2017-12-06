package com.phicdy.totoanticipation.presenter

import android.os.Handler
import android.os.Looper
import com.phicdy.totoanticipation.model.*
import com.phicdy.totoanticipation.model.scheduler.DeadlineAlarm
import com.phicdy.totoanticipation.model.storage.GameListStorage
import com.phicdy.totoanticipation.view.GameListView

import java.io.IOException
import java.text.SimpleDateFormat

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import java.util.Date
import java.util.Locale
import java.util.concurrent.Executor

class GameListPresenter(private val view: GameListView,
                        private val rakutenTotoRequestExecutor: RakutenTotoRequestExecutor,
                        private val jLeagueRequestExecutor: JLeagueRequestExecutor,
                        private val storage: GameListStorage, private val isDeadlineNotify: Boolean,
                        private val alarm: DeadlineAlarm) : Presenter, RakutenTotoRequestExecutor.RakutenTotoRequestCallback, JLeagueRequestExecutor.JLeagueRequestCallback {
    private var toto: Toto = Toto(Toto.DEFAULT_NUMBER, Date())
    private var games: List<Game> = listOf()
    private var j1ranking: Map<String, Int> = HashMap()
    private var j2ranking: Map<String, Int> = HashMap()
    private var j3ranking: Map<String, Int> = HashMap()

    override fun onCreate() {
        view.startProgress()
        rakutenTotoRequestExecutor.fetchRakutenTotoTopPage(this)
    }

    override fun onResponseTotoTop(response: Response<ResponseBody>) {
        try {
            val body = response.body().string()
            toto = RakutenTotoTopParser().latestToto(body)
            if (toto.number == Toto.DEFAULT_NUMBER) {
                view.stopProgress()
                return
            }
            if (isDeadlineNotify) alarm.setAtNoonOf(toto.deadline)
            games = storage.list(toto.number)
            if (games.size == 0) {
                jLeagueRequestExecutor.fetchJ1Ranking(this)
            } else {
                view.stopProgress()
                view.initList()
                storage.store(toto, games)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            view.stopProgress()
        }

    }

    override fun onFailureTotoTop(call: Call<ResponseBody>, throwable: Throwable) {
        view.stopProgress()
    }

    override fun onResponseJ1Ranking(response: Response<ResponseBody>) {
        try {
            val body = response.body().string()
            j1ranking = JLeagueRankingParser().ranking(body)
            jLeagueRequestExecutor.fetchJ2Ranking(this)
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
            val body = response.body().string()
            j2ranking = JLeagueRankingParser().ranking(body)
            jLeagueRequestExecutor.fetchJ3Ranking(this)
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
            val body = response.body().string()
            j3ranking = JLeagueRankingParser().ranking(body)
            rakutenTotoRequestExecutor.fetchRakutenTotoInfoPage(toto.number, this)
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
            val body = response.body().string()
            val parser = RakutenTotoInfoParser()

            // Set title
            if (toto.number != Toto.DEFAULT_NUMBER) {
                val format = SimpleDateFormat("MM/dd ", Locale.JAPAN)
                view.setTitleFrom(toto.number, format.format(toto.deadline) + parser.deadlineTime(body))
            }

            // Parse games
            games = parser.games(body)
            for (game in games) {
                val homeFullName = TeamInfoMapper.fullNameForJLeagueRanking(game.homeTeam)
                val awayFullName = TeamInfoMapper.fullNameForJLeagueRanking(game.awayTeam)
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
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    override fun onFailureTotoInfo(call: Call<ResponseBody>, throwable: Throwable) {
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

    fun gameAt(position: Int): Game? =
            if (position >= games.size || position < 0) null else games[position]

    fun gameSize(): Int = games.size

    fun onFabClicked() {
        storage.store(toto, games)
        view.startTotoAnticipationActivity(toto.number)
    }

    fun onOptionsSettingSelected() {
        view.goToSetting()
    }

    fun onOptionsAutoAnticipationSelected() {
        view.showAnticipationStart()
        view.startProgress()
        object: Thread() {
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

    class MainThreadExecutor : Executor {
        private val handler: Handler = Handler(Looper.getMainLooper())

        override fun execute(r: Runnable) {
            handler.post(r);
        }
    }
}
