package com.phicdy.totoanticipation.feature.gamelist

import com.phicdy.totoanticipation.domain.Deadline
import com.phicdy.totoanticipation.domain.Game
import com.phicdy.totoanticipation.domain.League
import com.phicdy.totoanticipation.domain.Team
import com.phicdy.totoanticipation.domain.Toto
import com.phicdy.totoanticipation.domain.TotoInfo
import com.phicdy.totoanticipation.domain.TotoNumber
import com.phicdy.totoanticipation.repository.JLeagueRepository
import com.phicdy.totoanticipation.repository.RakutenTotoRepository
import com.phicdy.totoanticipation.storage.GameListStorage
import com.phicdy.totoanticipation.storage.SettingStorage
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.experimental.theories.DataPoints
import org.junit.experimental.theories.FromDataPoints
import org.junit.experimental.theories.Theories
import org.junit.experimental.theories.Theory
import org.junit.runner.RunWith
import java.util.Calendar
import java.util.Date

@RunWith(Theories::class)
class GameListPresenterTest {

    private lateinit var presenter: GameListPresenter
    private lateinit var view: GameListView
    private lateinit var storage: GameListStorage
    private lateinit var settingStorage: SettingStorage
    private lateinit var alarm: com.phicdy.totoanticipation.scheduler.DeadlineAlarm
    private lateinit var jLeagueRepository: JLeagueRepository
    private lateinit var rakutenTotoRepository: RakutenTotoRepository

    companion object {
        @DataPoints("outOfIndex")
        @JvmField
        var outOfIndex = listOf(-1, 2)
    }

    @Before
    fun setup() {
        storage = mockk()
        settingStorage = mockk()
        alarm = mockk()
        view = mockk(relaxed = true)
        jLeagueRepository = mockk()
        rakutenTotoRepository = mockk()
        presenter = GameListPresenter(view, jLeagueRepository, rakutenTotoRepository, storage, alarm, settingStorage)
    }

    @Test
    fun `when fetch result is null then stop progress`() = runBlocking {
        coEvery { rakutenTotoRepository.fetchLatestToto() } returns null
        every { settingStorage.isPrivacyPolicyAccepted } returns true
        presenter.onCreate()
        coVerify {
            view.stopProgress()
        }
    }

    @Test
    fun `when privacy policy is not accepted then stop the dialog`() = runBlocking {
        coEvery { rakutenTotoRepository.fetchLatestToto() } returns null
        every { settingStorage.isPrivacyPolicyAccepted } returns false
        presenter.onCreate()
        coVerify {
            view.showPrivacyPolicyDialog()
        }
    }

    @Test
    fun `when fetch result is default toto then show empty`() = runBlocking {
        coEvery { rakutenTotoRepository.fetchLatestToto() } returns Toto(Toto.DEFAULT_NUMBER, Date())
        presenter.onCreate()
        coVerify {
            view.stopProgress()
            view.hideList()
            view.hideFab()
            view.hideAnticipationMenu()
            view.showEmptyView()
        }
    }

    @Test
    fun `when notify setting is enabled then set alarm`() = runBlocking {
        val now = Date()
        val toto = Toto("1", now)
        val games = listOf(Game("home", "away"))
        coEvery { rakutenTotoRepository.fetchLatestToto() } returns toto
        every { alarm.setAtNoonOf(now) } just Runs
        every { storage.list("1") } returns games
        every { storage.store(toto, games) } just Runs
        every { settingStorage.isPrivacyPolicyAccepted } returns true

        every { settingStorage.isDeadlineNotify } returns true

        presenter.onCreate()
        verify {
            alarm.setAtNoonOf(now)
        }
    }

    @Test
    fun `when games are stored then stop and init`() = runBlocking {
        val now = Date()
        val toto = Toto("1", now)
        val games = listOf(Game("home", "away"))
        coEvery { rakutenTotoRepository.fetchLatestToto() } returns toto
        every { settingStorage.isDeadlineNotify } returns false
        every { storage.store(toto, games) } just Runs
        every { settingStorage.isPrivacyPolicyAccepted } returns true

        every { storage.list("1") } returns games

        presenter.onCreate()
        verify {
            view.stopProgress()
            view.initList()
        }
    }

    @Test
    fun `when games are not stored and fetch result is null then show empty`() = runBlocking {
        val now = Date()
        val toto = Toto("1", now)
        coEvery { rakutenTotoRepository.fetchLatestToto() } returns toto
        every { settingStorage.isDeadlineNotify } returns false
        every { settingStorage.isPrivacyPolicyAccepted } returns true
        every { storage.list("1") } returns emptyList()
        coEvery { jLeagueRepository.fetchJ1Ranking() } returns emptyList()
        coEvery { jLeagueRepository.fetchJ2Ranking() } returns emptyList()
        coEvery { jLeagueRepository.fetchJ3Ranking() } returns emptyList()

        coEvery { rakutenTotoRepository.fetchTotoInfo(TotoNumber("1")) } returns null

        presenter.onCreate()
        verify {
            view.stopProgress()
            view.showEmptyView()
        }
    }

    @Test
    fun `when games are not stored and toto number is not default then set the title`() = runBlocking {
        val now = Calendar.getInstance().apply {
            set(2019, 0, 1, 12, 34)
        }.time
        val toto = Toto("1", now)
        val games = listOf(Game("home", "away"))
        coEvery { rakutenTotoRepository.fetchLatestToto() } returns toto
        every { settingStorage.isDeadlineNotify } returns false
        every { storage.store(toto, games) } just Runs
        every { settingStorage.isPrivacyPolicyAccepted } returns true
        every { storage.list("1") } returns emptyList()
        coEvery { jLeagueRepository.fetchJ1Ranking() } returns emptyList()
        coEvery { jLeagueRepository.fetchJ2Ranking() } returns emptyList()
        coEvery { jLeagueRepository.fetchJ3Ranking() } returns emptyList()

        coEvery { rakutenTotoRepository.fetchTotoInfo(TotoNumber("1")) } returns TotoInfo(games, Deadline("12:34"))

        presenter.onCreate()
        verify {
            view.setTitleFrom(toto.number, "01/01 12:34")
        }
    }

    @Test
    fun `when games are not stored and fetch succeeds then store the games`() = runBlocking {
        val now = Calendar.getInstance().apply {
            set(2019, 0, 1, 12, 34)
        }.time
        val toto = Toto("1", now)
        val games = listOf(Game("home", "away"))
        coEvery { rakutenTotoRepository.fetchLatestToto() } returns toto
        every { settingStorage.isDeadlineNotify } returns false
        every { storage.store(toto, games) } just Runs
        every { settingStorage.isPrivacyPolicyAccepted } returns true
        every { storage.list("1") } returns emptyList()
        coEvery { jLeagueRepository.fetchJ1Ranking() } returns emptyList()
        coEvery { jLeagueRepository.fetchJ2Ranking() } returns emptyList()
        coEvery { jLeagueRepository.fetchJ3Ranking() } returns emptyList()

        coEvery { rakutenTotoRepository.fetchTotoInfo(TotoNumber("1")) } returns TotoInfo(games, Deadline("12:34"))

        presenter.onCreate()
        verify {
            view.initList()
            storage.store(toto, games)
            view.stopProgress()
        }
    }

    @Test
    fun `when games are J1 then ranking are set`() = runBlocking {
        val now = Calendar.getInstance().apply {
            set(2019, 0, 1, 12, 34)
        }.time
        val toto = Toto("1", now)
        val games = listOf(Game("Ｆ東京", "横浜Ｍ"))
        coEvery { rakutenTotoRepository.fetchLatestToto() } returns toto
        every { settingStorage.isDeadlineNotify } returns false
        every { storage.store(toto, games) } just Runs
        every { settingStorage.isPrivacyPolicyAccepted } returns true
        every { storage.list("1") } returns emptyList()
        coEvery { jLeagueRepository.fetchJ2Ranking() } returns emptyList()
        coEvery { jLeagueRepository.fetchJ3Ranking() } returns emptyList()
        coEvery { rakutenTotoRepository.fetchTotoInfo(TotoNumber("1")) } returns TotoInfo(games, Deadline("12:34"))

        val j1ranking = listOf(
                Team("ＦＣ東京", League.J1, 1),
                Team("横浜Ｆ・マリノス", League.J1, 2)
        )
        coEvery { jLeagueRepository.fetchJ1Ranking() } returns j1ranking

        presenter.onCreate()
        assertEquals(1, games[0].homeRanking)
        assertEquals(2, games[0].awayRanking)
    }

    @Test
    fun `when games are J2 then ranking are set`() = runBlocking {
        val now = Calendar.getInstance().apply {
            set(2019, 0, 1, 12, 34)
        }.time
        val toto = Toto("1", now)
        val games = listOf(Game("Ｆ東京", "横浜Ｍ"))
        coEvery { rakutenTotoRepository.fetchLatestToto() } returns toto
        every { settingStorage.isDeadlineNotify } returns false
        every { storage.store(toto, games) } just Runs
        every { settingStorage.isPrivacyPolicyAccepted } returns true
        every { storage.list("1") } returns emptyList()
        coEvery { jLeagueRepository.fetchJ1Ranking() } returns emptyList()
        coEvery { jLeagueRepository.fetchJ3Ranking() } returns emptyList()
        coEvery { rakutenTotoRepository.fetchTotoInfo(TotoNumber("1")) } returns TotoInfo(games, Deadline("12:34"))

        val j2ranking = listOf(
                Team("ＦＣ東京", League.J1, 1),
                Team("横浜Ｆ・マリノス", League.J1, 2)
        )
        coEvery { jLeagueRepository.fetchJ2Ranking() } returns j2ranking

        presenter.onCreate()
        assertEquals(1, games[0].homeRanking)
        assertEquals(2, games[0].awayRanking)
    }

    @Test
    fun `when games are J3 then ranking are set`() = runBlocking {
        val now = Calendar.getInstance().apply {
            set(2019, 0, 1, 12, 34)
        }.time
        val toto = Toto("1", now)
        val games = listOf(Game("Ｆ東京", "横浜Ｍ"))
        coEvery { rakutenTotoRepository.fetchLatestToto() } returns toto
        every { settingStorage.isDeadlineNotify } returns false
        every { storage.store(toto, games) } just Runs
        every { settingStorage.isPrivacyPolicyAccepted } returns true
        every { storage.list("1") } returns emptyList()
        coEvery { jLeagueRepository.fetchJ1Ranking() } returns emptyList()
        coEvery { jLeagueRepository.fetchJ2Ranking() } returns emptyList()
        coEvery { rakutenTotoRepository.fetchTotoInfo(TotoNumber("1")) } returns TotoInfo(games, Deadline("12:34"))

        val j3ranking = listOf(
                Team("ＦＣ東京", League.J1, 1),
                Team("横浜Ｆ・マリノス", League.J1, 2)
        )
        coEvery { jLeagueRepository.fetchJ3Ranking() } returns j3ranking

        presenter.onCreate()
        assertEquals(1, games[0].homeRanking)
        assertEquals(2, games[0].awayRanking)
    }

    @Test
    fun `when home radio button is clicked then change anticipation and store`() = runBlocking {
        val now = Calendar.getInstance().apply {
            set(2019, 0, 1, 12, 34)
        }.time
        val toto = Toto("1", now)
        val games = listOf(Game("home", "away"))
        mockTotoReturns(toto, games)

        presenter.onHomeRadioButtonClicked(0, true)
        verifyOrder {
            // In setup
            storage.list("1")
            storage.store(toto, games)

            // Actual verification
            storage.store(toto, games)
        }
        assertEquals(Game.Anticipation.HOME, games[0].anticipation)
    }

    @Theory
    fun `when out of index home radio button is clicked then do nothing`(
            @FromDataPoints("outOfIndex") outOfIndex: Int
    ) = runBlocking {
        val now = Calendar.getInstance().apply {
            set(2019, 0, 1, 12, 34)
        }.time
        val toto = Toto("1", now)
        val games = listOf(Game("home", "away"))
        mockTotoReturns(toto, games)

        presenter.onHomeRadioButtonClicked(outOfIndex, true)
        // Do not call store() twice, setup only
        verifyOrder {
            storage.list("1")
            storage.store(toto, games)
        }
    }

    @Test
    fun `when away radio button is clicked then change anticipation and store`() = runBlocking {
        val now = Calendar.getInstance().apply {
            set(2019, 0, 1, 12, 34)
        }.time
        val toto = Toto("1", now)
        val games = listOf(Game("home", "away"))
        mockTotoReturns(toto, games)

        presenter.onAwayRadioButtonClicked(0, true)
        verifyOrder {
            // In setup
            storage.list("1")
            storage.store(toto, games)

            // Actual verification
            storage.store(toto, games)
        }
        assertEquals(Game.Anticipation.AWAY, games[0].anticipation)
    }

    @Test
    fun `when draw radio button is clicked then change anticipation and store`() = runBlocking {
        val now = Calendar.getInstance().apply {
            set(2019, 0, 1, 12, 34)
        }.time
        val toto = Toto("1", now)
        val games = listOf(Game("home", "away"))
        mockTotoReturns(toto, games)

        presenter.onDrawRadioButtonClicked(0, true)
        verifyOrder {
            // In setup
            storage.list("1")
            storage.store(toto, games)

            // Actual verification
            storage.store(toto, games)
        }
        assertEquals(Game.Anticipation.DRAW, games[0].anticipation)
    }

    @Test
    fun `when get game at the position then return the game`() = runBlocking {
        val now = Calendar.getInstance().apply {
            set(2019, 0, 1, 12, 34)
        }.time
        val toto = Toto("1", now)
        val game = Game("home", "away")
        val games = listOf(game)
        mockTotoReturns(toto, games)

        val firstGame = presenter.gameAt(0)
        assertEquals(game, firstGame)
    }

    @Test(expected = IndexOutOfBoundsException::class)
    fun `when get out of index game then throw IndexOutOfBoundsException`() = runBlocking {
        val now = Calendar.getInstance().apply {
            set(2019, 0, 1, 12, 34)
        }.time
        val toto = Toto("1", now)
        val game = Game("home", "away")
        val games = listOf(game)
        mockTotoReturns(toto, games)

        presenter.gameAt(-1)
        Unit
    }

    @Test
    fun `when get game size then return the size`() = runBlocking {
        val now = Calendar.getInstance().apply {
            set(2019, 0, 1, 12, 34)
        }.time
        val toto = Toto("1", now)
        val game = Game("home", "away")
        val games = listOf(game)
        mockTotoReturns(toto, games)

        assertEquals(1, presenter.gameSize())
    }

    @Test
    fun `when fab is clicked then store and go to anticipation activity`() = runBlocking {
        val now = Calendar.getInstance().apply {
            set(2019, 0, 1, 12, 34)
        }.time
        val toto = Toto("1", now)
        val game = Game("home", "away")
        val games = listOf(game)
        mockTotoReturns(toto, games)

        presenter.onFabClicked()
        verify {
            storage.store(toto, games)
            view.startTotoAnticipationActivity("1")
        }
    }

    @Test
    fun `when option setting is clicked then go to option`() {
        presenter.onOptionsSettingSelected()
        verify { view.goToSetting() }
    }

    @Test
    fun `when finish anticipation then stop and store`() = runBlocking {
        val now = Calendar.getInstance().apply {
            set(2019, 0, 1, 12, 34)
        }.time
        val toto = Toto("1", now)
        val game = Game("home", "away")
        val games = listOf(game)
        mockTotoReturns(toto, games)

        presenter.finishAnticipation()
        verify {
            view.stopProgress()
            view.showAnticipationFinish()
            view.notifyDataSetChanged()
            storage.store(toto, games)
        }
    }

    private suspend fun mockTotoReturns(toto: Toto, games: List<Game>) {
        coEvery { rakutenTotoRepository.fetchLatestToto() } returns toto
        every { settingStorage.isDeadlineNotify } returns false
        every { storage.store(toto, games) } just Runs
        every { settingStorage.isPrivacyPolicyAccepted } returns true
        every { storage.list("1") } returns emptyList()
        coEvery { jLeagueRepository.fetchJ1Ranking() } returns emptyList()
        coEvery { jLeagueRepository.fetchJ2Ranking() } returns emptyList()
        coEvery { jLeagueRepository.fetchJ3Ranking() } returns emptyList()
        coEvery { rakutenTotoRepository.fetchTotoInfo(TotoNumber("1")) } returns TotoInfo(games, Deadline("12:34"))
        presenter.onCreate()
    }
}
