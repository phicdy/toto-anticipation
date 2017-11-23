package com.phicdy.totoanticipation.model

class Game(val homeTeam: String, val awayTeam: String) {
    var homeRanking: String? = null
    var awayRanking: String? = null
    var anticipation = Anticipation.HOME

    enum class Anticipation {
        HOME, AWAY, DRAW
    }
}
