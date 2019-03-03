package com.phicdy.totoanticipation.model.storage

import com.phicdy.totoanticipation.model.Game
import com.phicdy.totoanticipation.model.Toto

import java.util.Date

interface GameListStorage {
    fun totoNum(): String
    fun totoDeadline(): Date
    fun list(totoNum: String): List<Game>
    fun store(toto: Toto, list: List<Game>)
}
