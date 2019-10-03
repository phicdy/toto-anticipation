package com.phicdy.totoanticipation.legacy.model

import com.phicdy.totoanticipation.domain.Game
import com.phicdy.totoanticipation.domain.Game.Anticipation.AWAY
import com.phicdy.totoanticipation.domain.Game.Anticipation.DRAW
import com.phicdy.totoanticipation.domain.Game.Anticipation.HOME
import junit.framework.Assert.assertEquals
import org.junit.experimental.theories.DataPoints
import org.junit.experimental.theories.Theories
import org.junit.experimental.theories.Theory
import org.junit.runner.RunWith

@RunWith(Theories::class)
class AutoAnticipationTest {

    companion object {
        @DataPoints
        @JvmField
        var gameAndResult = listOf(
                Game(1, 2) to DRAW,
                Game(1, 3) to DRAW,
                Game(1, 4) to HOME,
                Game(1, 5) to HOME,
                Game(2, 1) to HOME,
                Game(3, 1) to HOME,
                Game(4, 1) to DRAW,
                Game(5, 1) to DRAW,
                Game(6, 1) to AWAY,
                Game(7, 1) to AWAY,
                Game(8, 1) to AWAY
        )
    }

    @Theory
    fun testExec(gameAndResult: Pair<Game, Game.Anticipation>) {
        val game = gameAndResult.first
        val games = listOf(game)
        AutoAnticipation().exec(games)
        assertEquals("expected:" + gameAndResult.second + ", home rank: " + game.homeRanking + ", away rank: " + game.awayRanking,
                gameAndResult.second, games[0].anticipation)
    }
}