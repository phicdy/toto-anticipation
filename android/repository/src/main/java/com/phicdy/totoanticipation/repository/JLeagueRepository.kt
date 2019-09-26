package com.phicdy.totoanticipation.repository

import com.phicdy.totoanticipation.domain.Team

interface JLeagueRepository {
    suspend fun fetchJ1Ranking(): List<Team>
    suspend fun fetchJ2Ranking(): List<Team>
    suspend fun fetchJ3Ranking(): List<Team>
}