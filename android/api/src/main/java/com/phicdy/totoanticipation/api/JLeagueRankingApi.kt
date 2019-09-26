package com.phicdy.totoanticipation.api

import com.phicdy.totoanticipation.domain.League
import com.phicdy.totoanticipation.domain.Team
import com.phicdy.totoanticipation.repository.JLeagueRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class JLeagueRankingApi @Inject constructor(
        private val api: JLeagueService,
        private val parser: JLeagueRankingParser
) : JLeagueRepository {
    override suspend fun fetchJ1Ranking(): List<Team> = withContext(Dispatchers.IO) {
        val response = api.j1ranking()
        return@withContext parser.ranking(response.string(), League.J1)
    }

    override suspend fun fetchJ2Ranking(): List<Team> = withContext(Dispatchers.IO) {
        val response = api.j2ranking()
        return@withContext parser.ranking(response.string(), League.J2)
    }

    override suspend fun fetchJ3Ranking(): List<Team> = withContext(Dispatchers.IO) {
        val response = api.j3ranking()
        return@withContext parser.ranking(response.string(), League.J3)
    }
}