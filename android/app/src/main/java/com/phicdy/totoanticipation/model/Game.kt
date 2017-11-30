package com.phicdy.totoanticipation.model

class Game(val homeTeam: String, val awayTeam: String) {
    val defaultRank: Int = 0
    var homeRanking: Int = defaultRank
    var awayRanking: Int = defaultRank
    var anticipation = Anticipation.HOME

    enum class Anticipation {
        HOME, AWAY, DRAW
    }
}
