package com.phicdy.totoanticipation.legacy.model.storage

import com.phicdy.totoanticipation.domain.Game
import com.phicdy.totoanticipation.domain.Toto
import java.util.Date

interface GameListStorage {
    fun totoNum(): String
    fun totoDeadline(): Date
    fun list(totoNum: String): List<Game>
    fun store(toto: Toto, list: List<Game>)
}
