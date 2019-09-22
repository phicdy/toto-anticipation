package com.phicdy.totoanticipation.legacy.model

import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test

class RakutenTotoInfoParserTest {

    @Test
    fun gameListReturns() {
        val expectedGames = arrayListOf<Game>().apply {
            add(Game("浦和", "札幌"))
            add(Game("甲府", "Ｃ大阪"))
            add(Game("広島", "仙台"))
            add(Game("鹿島", "磐田"))
            add(Game("柏", "横浜Ｍ"))
            add(Game("新潟", "Ｆ東京"))
            add(Game("鳥栖", "神戸"))
            add(Game("名古屋", "山口"))
            add(Game("横浜Ｃ", "千葉"))
            add(Game("京都", "松本"))
            add(Game("愛媛", "長崎"))
            add(Game("東京Ｖ", "群馬"))
            add(Game("町田", "徳島"))
        }
        val parser = RakutenTotoInfoParser()
        val actualGames = parser.games(TestRakutenTotoInfoPage.text)
        for (i in actualGames.indices) {
            assertThat(actualGames[i].homeTeam, `is`(expectedGames[i].homeTeam))
            assertThat(actualGames[i].awayTeam, `is`(expectedGames[i].awayTeam))
        }
    }

    @Test
    fun emptyListReturnsWhenEmptyHtml() {
        val parser = RakutenTotoInfoParser()
        assertThat(parser.games("").size, `is`(0))
    }

    @Test
    fun emptyListReturnsWhenInvalidHtml() {
        val parser = RakutenTotoInfoParser()
        assertThat(parser.games("<html><body>hoge</body></html>").size, `is`(0))
    }

    @Test
    fun endTimeReturns() {
        val parser = RakutenTotoInfoParser()
        val deadlineTime = parser.deadlineTime(TestRakutenTotoInfoPage.text)
        assertThat(deadlineTime, `is`("13:50"))
    }

    @Test
    fun emptyTimeReturnsWhenEmptyHtml() {
        val parser = RakutenTotoInfoParser()
        assertThat(parser.deadlineTime(""), `is`(""))
    }

    @Test
    fun emptyTimeReturnsWhenInvalidHtml() {
        val parser = RakutenTotoInfoParser()
        assertThat(parser.deadlineTime("<html><body>hoge</body></html>"), `is`(""))
    }

}
