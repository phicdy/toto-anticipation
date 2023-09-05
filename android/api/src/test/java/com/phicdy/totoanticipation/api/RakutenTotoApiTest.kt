package com.phicdy.totoanticipation.api

import com.phicdy.totoanticipation.domain.TeamInfoMapper
import com.phicdy.totoanticipation.domain.Toto
import com.phicdy.totoanticipation.domain.TotoNumber
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit


class RakutenTotoApiTest {

    private lateinit var rakutenTotoApi: RakutenTotoApi

    @Before
    fun setup() {
        rakutenTotoApi = RakutenTotoApi(
            service = Retrofit.Builder()
                .baseUrl("https://toto.rakuten.co.jp/")
                .build()
                .create(RakutenTotoService::class.java),
            rakutenTotoInfoParser = RakutenTotoInfoParser(),
            rakutenTotoTopParser = RakutenTotoTopParser()
        )
    }

    @Test
    fun testLatestToto() = runTest {
        val latestToto = rakutenTotoApi.fetchLatestToto()
        if (latestToto.number == Toto.DEFAULT_NUMBER) {
            return@runTest
        }
        assertThat(latestToto.number.toInt()).isGreaterThan(0)
        val totoInfo = rakutenTotoApi.fetchTotoInfo(TotoNumber(latestToto.number))
        assertNotNull(totoInfo)
        assertThat(totoInfo!!.deadline.toString()).contains(":")
        val mapper = TeamInfoMapper()
        for (game in totoInfo.games) {
            assertThat(mapper.fullNameForJLeagueRanking(game.homeTeam))
                .withFailMessage("Failed to map ${game.homeTeam}")
                .isNotBlank()
            assertThat(mapper.fullNameForJLeagueRanking(game.awayTeam))
                .withFailMessage("Failed to map ${game.awayTeam}")
                .isNotBlank()
            assertThat(game.homeRanking).isEqualTo(0)
            assertThat(game.awayRanking).isEqualTo(0)
        }
    }
}