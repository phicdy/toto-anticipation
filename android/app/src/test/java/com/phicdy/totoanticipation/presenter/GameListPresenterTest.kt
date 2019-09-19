package com.phicdy.totoanticipation.presenter

import com.phicdy.totoanticipation.model.Game
import com.phicdy.totoanticipation.model.Game.Anticipation
import com.phicdy.totoanticipation.model.JLeagueRequestExecutor
import com.phicdy.totoanticipation.model.JLeagueService
import com.phicdy.totoanticipation.model.RakutenTotoRequestExecutor
import com.phicdy.totoanticipation.model.RakutenTotoService
import com.phicdy.totoanticipation.model.TestRakutenTotoInfoPage
import com.phicdy.totoanticipation.model.TestRakutenTotoPage
import com.phicdy.totoanticipation.model.Toto
import com.phicdy.totoanticipation.model.scheduler.DeadlineAlarm
import com.phicdy.totoanticipation.model.storage.GameListStorage
import com.phicdy.totoanticipation.model.storage.SettingStorage
import com.phicdy.totoanticipation.view.GameListView
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import retrofit2.Response
import java.util.ArrayList
import java.util.Date

class GameListPresenterTest {

    private lateinit var presenter: GameListPresenter
    private lateinit var rakutenTotoRequestExecutor: RakutenTotoRequestExecutor
    private lateinit var jLeagueRequestExecutor: JLeagueRequestExecutor
    private lateinit var view: MockView
    private lateinit var storage: GameListStorage
    private lateinit var settingStorage: SettingStorage
    private lateinit var alarm: DeadlineAlarm

    @Before
    fun setup() {
        val service = RakutenTotoService.Factory.create()
        rakutenTotoRequestExecutor = RakutenTotoRequestExecutor(service)
        val service1 = JLeagueService.Factory.create()
        jLeagueRequestExecutor = JLeagueRequestExecutor(service1)
        storage = MockStorage()
        settingStorage = MockSettingStorage()
        alarm = Mockito.mock(DeadlineAlarm::class.java)
        view = MockView()
        presenter = GameListPresenter(view, rakutenTotoRequestExecutor, jLeagueRequestExecutor, storage,
                alarm, settingStorage)
    }

    @Test
    fun progressBarStartsAfterOnCreate() {
        presenter.onCreate()
        assertTrue(view.isProgressing)
    }

    @Test
    fun testOnResume() {
        // For coverage
        presenter.onResume()
    }

    @Test
    fun testOnPause() {
        // For coverage
        presenter.onPause()
    }

    @Test
    fun progressBarStopsWhenOnFailureTotoTop() {
        presenter.onFailureTotoTop(Throwable())
        assertFalse(view.isProgressing)
    }

    @Test
    fun progressBarStopsWhenOnFailureTotoInfo() {
        presenter.onFailureTotoInfo(Throwable())
        assertFalse(view.isProgressing)
    }

    @Test
    fun titleBecomesLatestTotoAfterReceivingTotoInfoResponse() {
        val response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoPage.text))
        val infoResponse = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoInfoPage.text))
        presenter.onResponseTotoTop(response)
        presenter.onResponseTotoInfo(infoResponse)
        assertThat(view.title, `is`("第0923回(~04/22 13:50)"))
    }

    @Test
    fun listIsSetWhenStoredListExists() {
        val testList = ArrayList<Game>()
        testList.add(Game("home", "away"))
        storage.store(Toto("0923", Date()), testList)
        val view = MockView()
        presenter = GameListPresenter(view, rakutenTotoRequestExecutor, jLeagueRequestExecutor, storage, alarm, settingStorage)
        val response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoPage.text))
        presenter.onResponseTotoTop(response)
        assertThat(presenter.gameAt(0).homeTeam, `is`("home"))
    }

    @Test
    fun progressBarStopsWhenStoredListExists() {
        val testList = ArrayList<Game>()
        testList.add(Game("home", "away"))
        storage.store(Toto("0923", Date()), testList)
        val view = MockView()
        presenter = GameListPresenter(view, rakutenTotoRequestExecutor, jLeagueRequestExecutor, storage, alarm, settingStorage)
        val response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoPage.text))
        presenter.onResponseTotoTop(response)
        assertFalse(view.isProgressing)
    }

    @Test
    fun titleDoesNotChangeAfterReceivingInvalidTotoTopResponse() {
        val response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), "<html><body>hoge</body></html>"))
        presenter.onResponseTotoTop(response)
        assertThat(view.title, `is`("toto予想"))
    }

    @Test
    fun gamesAreSetAfterReceivingTotoInfoResponse() {
        val response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoInfoPage.text))
        presenter.onResponseTotoInfo(response)
        val expectedGames = ArrayList<Game>()
        expectedGames.add(Game("浦和", "札幌"))
        expectedGames.add(Game("甲府", "Ｃ大阪"))
        expectedGames.add(Game("広島", "仙台"))
        expectedGames.add(Game("鹿島", "磐田"))
        expectedGames.add(Game("柏", "横浜Ｍ"))
        expectedGames.add(Game("新潟", "Ｆ東京"))
        expectedGames.add(Game("鳥栖", "神戸"))
        expectedGames.add(Game("名古屋", "山口"))
        expectedGames.add(Game("横浜Ｃ", "千葉"))
        expectedGames.add(Game("京都", "松本"))
        expectedGames.add(Game("愛媛", "長崎"))
        expectedGames.add(Game("東京Ｖ", "群馬"))
        expectedGames.add(Game("町田", "徳島"))
        for (i in 0 until presenter.gameSize()) {
            assertThat(presenter.gameAt(i).homeTeam, `is`(expectedGames[i].homeTeam))
            assertThat(presenter.gameAt(i).awayTeam, `is`(expectedGames[i].awayTeam))
        }
    }

    @Test
    fun gamesAreEmptyAfterReceivingInvalidTotoInfoResponse() {
        val response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), "<html><body>hoge</body></html>"))
        presenter.onResponseTotoInfo(response)
        assertThat(presenter.gameSize(), `is`(0))
    }

    @Test
    fun anticipationBecomesHomeWhenHomeRadioButtonIsClicked() {
        val response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoInfoPage.text))
        presenter.onResponseTotoInfo(response)
        presenter.onHomeRadioButtonClicked(0, true)
        assertThat<Anticipation>(presenter.gameAt(0).anticipation, `is`<Anticipation>(Anticipation.HOME))
    }

    @Test
    fun anticipationBecomesAwayWhenAwayRadioButtonIsClicked() {
        val response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoInfoPage.text))
        presenter.onResponseTotoInfo(response)
        presenter.onAwayRadioButtonClicked(0, true)
        assertThat<Anticipation>(presenter.gameAt(0).anticipation, `is`<Anticipation>(Anticipation.AWAY))
    }

    @Test
    fun anticipationBecomesDrawWhenDrawRadioButtonIsClicked() {
        val response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoInfoPage.text))
        presenter.onResponseTotoInfo(response)
        presenter.onDrawRadioButtonClicked(0, true)
        assertThat<Anticipation>(presenter.gameAt(0).anticipation, `is`<Anticipation>(Anticipation.DRAW))
    }

    @Test
    fun clickMinusPositionOfRadioButtonHasNoAffect() {
        val response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoInfoPage.text))
        presenter.onResponseTotoInfo(response)
        presenter.onAwayRadioButtonClicked(-1, true)
        // All of the anticipations are default, HOME
        for (i in 0 until presenter.gameSize()) {
            assertThat<Anticipation>(presenter.gameAt(i).anticipation, `is`<Anticipation>(Anticipation.HOME))
        }
    }

    @Test
    fun clickBiggerPositionThanGameSizeOfRadioButtonHasNoAffect() {
        val response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoInfoPage.text))
        presenter.onResponseTotoInfo(response)
        presenter.onAwayRadioButtonClicked(presenter.gameSize() + 1, true)
        // All of the anticipations are default, HOME
        for (i in 0 until presenter.gameSize()) {
            assertThat<Anticipation>(presenter.gameAt(i).anticipation, `is`<Anticipation>(Anticipation.HOME))
        }
    }

    @Test
    fun WhenFabIsClickedTotoAnticipationActivityStarts() {
        var response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoPage.text))
        presenter.onResponseTotoTop(response)
        response = Response.success(
                ResponseBody.create(MediaType.parse("application/text"), TestRakutenTotoInfoPage.text))
        presenter.onResponseTotoInfo(response)
        presenter.onFabClicked()
        assertTrue(view.isTotoAnticipationActivityStarted)
    }

    @Test
    fun startProgressBarWhenAutoAnticipationMenuIsClicked() {
        presenter.onOptionsAutoAnticipationSelected()
        assertTrue(view.isProgressing)
    }

    @Test
    fun showStartSnakeBarWhenAutoAnticipationMenuIsClicked() {
        presenter.onOptionsAutoAnticipationSelected()
        assertTrue(view.showStartSnakeBar)
    }

    @Test
    fun progressBarStopsWhenAutoAnticipationIsFinished() {
        presenter.finishAnticipation()
        assertFalse(view.isProgressing)
    }

    @Test
    fun showFinishSnakeBarWhenAutoAnticipationIsFinished() {
        presenter.finishAnticipation()
        assertTrue(view.showFinishSnakeBar)
    }

    @Test
    fun dataIsStoredWhenAutoAnticipationIsFinished() {
        presenter.finishAnticipation()
        assertNotNull(storage.totoNum())
    }

    private inner class MockView : GameListView {
        var title = "toto予想"
        var isProgressing = false
        var showStartSnakeBar = false
        var showFinishSnakeBar = false
        var isTotoAnticipationActivityStarted = false

        override fun initList() {}

        override fun setTitleFrom(xxTh: String, deadline: String) {
            title = "第" + xxTh + "回(~" + deadline + ")"
        }

        override fun startProgress() {
            isProgressing = true
        }

        override fun stopProgress() {
            isProgressing = false
        }

        override fun startTotoAnticipationActivity(totoNum: String) {
            isTotoAnticipationActivityStarted = true
        }

        override fun goToSetting() {}

        override fun showAnticipationStart() {
            showStartSnakeBar = true
        }

        override fun showAnticipationFinish() {
            showFinishSnakeBar = true
        }

        override fun notifyDataSetChanged() {}

        override fun showAnticipationNotSupport() {}

        override fun showPrivacyPolicyDialog() {}

        override fun hideList() {
        }

        override fun hideFab() {
        }

        override fun hideAnticipationMenu() {
        }

        override fun showEmptyView() {
        }
    }

    private inner class MockStorage : GameListStorage {

        private lateinit var totoNum: String
        private var games: List<Game> = ArrayList()

        override fun totoNum(): String {
            return totoNum
        }

        override fun totoDeadline(): Date {
            return Date()
        }

        override fun list(totoNum: String): List<Game> {
            return games
        }

        override fun store(toto: Toto, list: List<Game>) {
            this.totoNum = toto.number
            games = list
        }
    }

    private inner class MockSettingStorage : SettingStorage {

        override var isDeadlineNotify = false
        override var isPrivacyPolicyAccepted = false
    }
}
