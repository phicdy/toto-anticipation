package com.phicdy.totoanticipation.model

import com.phicdy.totoanticipation.model.Game.Anticipation.*
class AutoAnticipation {
    fun exec(games: List<Game>) {
        for (game in games) {
            if (game.awayRanking >= game.homeRanking) {
                // Home ranking is smaller, means looks to be stronger than away team
                if (game.awayRanking - game.homeRanking <= 2) {
                    game.anticipation = DRAW
                } else {
                    game.anticipation = HOME
                }
                continue
            }
            // Away ranking is smaller, means looks to be stronger than home team
            val diff = game.homeRanking - game.awayRanking
            if (diff <= 2) {
                game.anticipation = HOME
                continue
            } else if (diff <= 4) {
                game.anticipation = DRAW
                continue
            }
            game.anticipation = AWAY
        }

    }
}