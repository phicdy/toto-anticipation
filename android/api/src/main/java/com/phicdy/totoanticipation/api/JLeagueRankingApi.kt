package com.phicdy.totoanticipation.api

import com.phicdy.totoanticipation.domain.League
import com.phicdy.totoanticipation.domain.Team
import com.phicdy.totoanticipation.repository.JLeagueRepository
import javax.inject.Inject

class JLeagueRankingApi @Inject constructor(
        private val api: JLeagueService,
        private val parser: JLeagueRankingParser
) : JLeagueRepository {
    override fun fetchJ1Ranking(): List<Team> {
        val call = api.j1ranking()
        val response = call.execute()
        response.body()?.string()?.let {
            return parser.ranking(it, League.J1)
        } ?: return emptyList()
    }

    override fun fetchJ2Ranking(): List<Team> {
        val call = api.j2ranking()
        val response = call.execute()
        response.body()?.string()?.let {
            return parser.ranking(it, League.J2)
        } ?: return emptyList()
    }

    override fun fetchJ3Ranking(): List<Team> {
        val call = api.j3ranking()
        val response = call.execute()
        response.body()?.string()?.let {
            return parser.ranking(it, League.J3)
        } ?: return emptyList()
    }
}