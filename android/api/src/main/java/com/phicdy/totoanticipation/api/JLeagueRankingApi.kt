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
        val call = api.j1ranking()
        val response = call.execute()
        response.body()?.string()?.let {
            return@withContext parser.ranking(it, League.J1)
        } ?: return@withContext emptyList<Team>()
    }

    override suspend fun fetchJ2Ranking(): List<Team> = withContext(Dispatchers.IO) {
        val call = api.j2ranking()
        val response = call.execute()
        response.body()?.string()?.let {
            return@withContext parser.ranking(it, League.J2)
        } ?: return@withContext emptyList<Team>()
    }

    override suspend fun fetchJ3Ranking(): List<Team> = withContext(Dispatchers.IO) {
        val call = api.j3ranking()
        val response = call.execute()
        response.body()?.string()?.let {
            return@withContext parser.ranking(it, League.J3)
        } ?: return@withContext emptyList<Team>()
    }
}