package com.phicdy.totoanticipation.repository

import com.phicdy.totoanticipation.domain.Team

interface JLeagueRepository {
    fun fetchJ1Ranking(): List<Team>
    fun fetchJ2Ranking(): List<Team>
    fun fetchJ3Ranking(): List<Team>
}