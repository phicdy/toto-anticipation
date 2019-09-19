package com.phicdy.totoanticipation.model

import com.phicdy.totoanticipation.legacy.model.JLeagueRankingParser
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertThat
import org.junit.Test

class JLeagueRankingParserTest {

    @Test
    fun whenParsingTestDataFirstIsKashima() {
        val parser = JLeagueRankingParser()
        val map = parser.ranking(text)
        assertThat(map["鹿島アントラーズ"], `is`(1))
    }

    @Test
    fun whenParsingTestDataSecondIsUrawa() {
        val parser = JLeagueRankingParser()
        val map = parser.ranking(text)
        assertThat(map["浦和レッズ"], `is`(2))
    }

    @Test
    fun whenParsingTestDataThirdIsGanba() {
        val parser = JLeagueRankingParser()
        val map = parser.ranking(text)
        assertThat(map["ガンバ大阪"], `is`(3))
    }

    @Test
    fun whenParsingTestDataFourthIsFCTokyo() {
        val parser = JLeagueRankingParser()
        val map = parser.ranking(text)
        assertThat(map["ＦＣ東京"], `is`(4))
    }

    @Test
    fun whenParsingTestDataFifthIsKashiwa() {
        val parser = JLeagueRankingParser()
        val map = parser.ranking(text)
        assertThat(map["柏レイソル"], `is`(5))
    }

    @Test
    fun whenParsingTestDataSixthIsKawasaki() {
        val parser = JLeagueRankingParser()
        val map = parser.ranking(text)
        assertThat(map["川崎フロンターレ"], `is`(6))
    }

    @Test
    fun whenParsingTestDataSeventhIsSeresso() {
        val parser = JLeagueRankingParser()
        val map = parser.ranking(text)
        assertThat(map["セレッソ大阪"], `is`(7))
    }

    @Test
    fun whenParsingTestDataEighthIsKobe() {
        val parser = JLeagueRankingParser()
        val map = parser.ranking(text)
        assertThat(map["ヴィッセル神戸"], `is`(8))
    }

    @Test
    fun whenParsingTestDataNinthIsIwata() {
        val parser = JLeagueRankingParser()
        val map = parser.ranking(text)
        assertThat(map["ジュビロ磐田"], `is`(9))
    }

    @Test
    fun whenParsingTestDataTenthIsTosu() {
        val parser = JLeagueRankingParser()
        val map = parser.ranking(text)
        assertThat(map["サガン鳥栖"], `is`(10))
    }

    @Test
    fun whenParsingTestDataEleventhIsMarinosu() {
        val parser = JLeagueRankingParser()
        val map = parser.ranking(text)
        assertThat(map["横浜Ｆ・マリノス"], `is`(11))
    }

    @Test
    fun whenParsingTestDataTwelfthIsKofu() {
        val parser = JLeagueRankingParser()
        val map = parser.ranking(text)
        assertThat(map["ヴァンフォーレ甲府"], `is`(12))
    }

    @Test
    fun whenParsingTestDataThirteenthIsBegaruta() {
        val parser = JLeagueRankingParser()
        val map = parser.ranking(text)
        assertThat(map["ベガルタ仙台"], `is`(13))
    }

    @Test
    fun whenParsingTestDataFourteenthIsShimizu() {
        val parser = JLeagueRankingParser()
        val map = parser.ranking(text)
        assertThat(map["清水エスパルス"], `is`(14))
    }

    @Test
    fun whenParsingTestDataFifteenthIsSapporo() {
        val parser = JLeagueRankingParser()
        val map = parser.ranking(text)
        assertThat(map["北海道コンサドーレ札幌"], `is`(15))
    }

    @Test
    fun whenParsingTestDataSixteenthIsHiroshima() {
        val parser = JLeagueRankingParser()
        val map = parser.ranking(text)
        assertThat(map["サンフレッチェ広島"], `is`(16))
    }

    @Test
    fun whenParsingTestDataSeventeenthIsNiigata() {
        val parser = JLeagueRankingParser()
        val map = parser.ranking(text)
        assertThat(map["アルビレックス新潟"], `is`(17))
    }

    @Test
    fun whenParsingTestDataEighteenthIsOmiya() {
        val parser = JLeagueRankingParser()
        val map = parser.ranking(text)
        assertThat(map["大宮アルディージャ"], `is`(18))
    }

    @Test
    fun whenParsingEmptyReturnsEmptyMap() {
        val parser = JLeagueRankingParser()
        val map = parser.ranking("")
        assertThat(map.size, `is`(0))
    }

    @Test
    fun whenParsingInvalidDataReturnsEmptyMap() {
        val parser = JLeagueRankingParser()
        val map = parser.ranking("<html><body>hoge</body></html>")
        assertThat(map.size, `is`(0))
    }
}
