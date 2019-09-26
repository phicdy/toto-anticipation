package com.phicdy.totoanticipation.api

import com.phicdy.totoanticipation.domain.Toto
import com.phicdy.totoanticipation.domain.TotoInfo
import com.phicdy.totoanticipation.domain.TotoNumber
import com.phicdy.totoanticipation.repository.RakutenTotoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RakutenTotoApi @Inject constructor(
        private val service: RakutenTotoService,
        private val rakutenTotoTopParser: RakutenTotoTopParser,
        private val rakutenTotoInfoParser: RakutenTotoInfoParser
) : RakutenTotoRepository {
    override suspend fun fetchLatestToto(): Toto? = withContext(Dispatchers.IO) {
        val schedule = service.schedule()
        return@withContext rakutenTotoTopParser.latestToto(schedule.string())
    }

    override suspend fun fetchTotoInfo(number: TotoNumber): TotoInfo? = withContext(Dispatchers.IO) {
        val totoInfo = service.totoInfo(number.toString()).string()
        val games = rakutenTotoInfoParser.games(totoInfo)
        val deadline = rakutenTotoInfoParser.deadlineTime(totoInfo)
        deadline?.let {
            return@withContext TotoInfo(games, deadline)
        }
        return@withContext null
    }
}