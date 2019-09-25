package com.phicdy.totoanticipation.repository

import com.phicdy.totoanticipation.domain.Toto
import com.phicdy.totoanticipation.domain.TotoInfo
import com.phicdy.totoanticipation.domain.TotoNumber

interface RakutenTotoRepository {
    fun fetchLatestToto(): Toto?
    fun fetchTotoInfo(number: TotoNumber): TotoInfo?
}