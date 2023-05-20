package com.phicdy.totoanticipation.api

import com.phicdy.totoanticipation.domain.League
import kotlinx.coroutines.test.runTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import javax.inject.Inject

class JLeagueRankingApiTest {

    @Inject
    lateinit var jLeagueRankingApi: JLeagueRankingApi

    @Before
    fun setup() {
        jLeagueRankingApi = JLeagueRankingApi(
            api = Retrofit.Builder()
                .baseUrl("https://www.jleague.jp/")
                .build()
                .create(JLeagueService::class.java),
            parser = JLeagueRankingParser()
        )
    }

    @Test
    fun testJ1() = runTest {
        val teams = jLeagueRankingApi.fetchJ1Ranking()
        assertThat(teams).hasSizeGreaterThan(0)
        for (team in teams) {
            assertThat(team.league).isEqualTo(League.J1)
            assertThat(team.name).isNotBlank()
            assertThat(team.ranking).isGreaterThan(0)
        }
    }

    @Test
    fun testJ2() = runTest {
        val teams = jLeagueRankingApi.fetchJ2Ranking()
        for (team in teams) {
            assertThat(team.league).isEqualTo(League.J2)
            assertThat(team.name).isNotBlank()
            assertThat(team.ranking).isGreaterThan(0)
        }
    }

    @Test
    fun testJ3() = runTest {
        val teams = jLeagueRankingApi.fetchJ3Ranking()
        assertThat(teams).hasSizeGreaterThan(0)
        for (team in teams) {
            assertThat(team.league).isEqualTo(League.J3)
            assertThat(team.name).isNotBlank()
            assertThat(team.ranking).isGreaterThan(0)
        }
    }
}