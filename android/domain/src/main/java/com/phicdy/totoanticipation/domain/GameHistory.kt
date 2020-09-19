package com.phicdy.totoanticipation.domain

class GameHistory {
    fun url(homeTeam: String, awayTeam: String) =
            "http://footballgeist.com/team/$homeTeam&id=condition&gegener=$awayTeam#byteam"
}
