package com.phicdy.totoanticipation.api

import com.phicdy.totoanticipation.domain.Toto
import com.phicdy.totoanticipation.domain.TotoInfo
import com.phicdy.totoanticipation.domain.TotoNumber
import com.phicdy.totoanticipation.repository.RakutenTotoRepository
import javax.inject.Inject

class RakutenTotoApi @Inject constructor(
        private val service: RakutenTotoService,
        private val rakutenTotoTopParser: RakutenTotoTopParser,
        private val rakutenTotoInfoParser: RakutenTotoInfoParser
) : RakutenTotoRepository {
    override fun fetchLatestToto(): Toto? {
        val schedule = service.schedule().execute()
        schedule.body()?.string()?.let {
            return rakutenTotoTopParser.latestToto(it)
        } ?: return null
    }

    override fun fetchTotoInfo(number: TotoNumber): TotoInfo? {
        val totoInfo = service.totoInfo(number.toString()).execute()
        totoInfo.body()?.string()?.let {
            val games = rakutenTotoInfoParser.games(it)
            val deadline = rakutenTotoInfoParser.deadlineTime(it)
            deadline?.let {
                return TotoInfo(games, deadline)
            }
        }
        return null
    }
}