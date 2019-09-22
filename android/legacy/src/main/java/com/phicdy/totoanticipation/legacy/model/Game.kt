package com.phicdy.totoanticipation.legacy.model

class Game(val homeTeam: String, val awayTeam: String) {
    constructor(homeRanking: Int, awayRanking: Int) : this("", "") {
        this.homeRanking = homeRanking
        this.awayRanking = awayRanking
    }

    companion object {
        const val defaultRank: Int = 0
    }

    var homeRanking: Int = defaultRank
    var awayRanking: Int = defaultRank
    var anticipation = Anticipation.HOME

    enum class Anticipation {
        HOME, AWAY, DRAW
    }
}
