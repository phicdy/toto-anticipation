package com.phicdy.totoanticipation.repository

import com.phicdy.totoanticipation.domain.Toto
import com.phicdy.totoanticipation.domain.TotoInfo
import com.phicdy.totoanticipation.domain.TotoNumber

interface RakutenTotoRepository {
    suspend fun fetchLatestToto(): Toto
    suspend fun fetchTotoInfo(number: TotoNumber): TotoInfo?
}