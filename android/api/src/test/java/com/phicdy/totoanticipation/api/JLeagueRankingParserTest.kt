package com.phicdy.totoanticipation.api

import com.phicdy.totoanticipation.api.TestJLeaguePage.text
import com.phicdy.totoanticipation.domain.League
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class JLeagueRankingParserTest {

    @Test
    fun whenParsingTestDataFirstIsKashima() {
        val parser = JLeagueRankingParser()
        val list = parser.ranking(text, League.J1)
        assertThat(list[0].name, `is`("鹿島アントラーズ"))
        assertThat(list[1].name, `is`("浦和レッズ"))
        assertThat(list[2].name, `is`("ＦＣ東京"))
        assertThat(list[3].name, `is`("ガンバ大阪"))
        assertThat(list[4].name, `is`("柏レイソル"))
        assertThat(list[5].name, `is`("川崎フロンターレ"))
        assertThat(list[6].name, `is`("セレッソ大阪"))
        assertThat(list[7].name, `is`("ヴィッセル神戸"))
        assertThat(list[8].name, `is`("ジュビロ磐田"))
        assertThat(list[9].name, `is`("サガン鳥栖"))
        assertThat(list[10].name, `is`("ベガルタ仙台"))
        assertThat(list[11].name, `is`("横浜Ｆ・マリノス"))
        assertThat(list[12].name, `is`("ヴァンフォーレ甲府"))
        assertThat(list[13].name, `is`("北海道コンサドーレ札幌"))
        assertThat(list[14].name, `is`("清水エスパルス"))
        assertThat(list[15].name, `is`("サンフレッチェ広島"))
        assertThat(list[16].name, `is`("アルビレックス新潟"))
        assertThat(list[17].name, `is`("大宮アルディージャ"))
    }

    @Test
    fun whenParsingEmptyReturnsEmptyMap() {
        val parser = JLeagueRankingParser()
        val list = parser.ranking("", League.J1)
        assertThat(list.size, `is`(0))
    }

    @Test
    fun whenParsingInvalidDataReturnsEmptyMap() {
        val parser = JLeagueRankingParser()
        val list = parser.ranking("<html><body>hoge</body></html>", League.J1)
        assertThat(list.size, `is`(0))
    }
}
