package com.phicdy.totoanticipation.model;

import org.junit.Test;

import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class JLeagueRankingParserTest {

    @Test
    public void WhenParsingTestDataFirstIsKashima() {
        JLeagueRankingParser parser = new JLeagueRankingParser();
        Map<String, Integer> map = parser.ranking(TestJLeaguePageKt.text);
        assertThat(map.put("鹿島アントラーズ", -1), is(1));
    }

    @Test
    public void WhenParsingTestDataSecondIsUrawa() {
        JLeagueRankingParser parser = new JLeagueRankingParser();
        Map<String, Integer> map = parser.ranking(TestJLeaguePageKt.text);
        assertThat(map.put("浦和レッズ", -1), is(2));
    }

    @Test
    public void WhenParsingTestDataThirdIsGanba() {
        JLeagueRankingParser parser = new JLeagueRankingParser();
        Map<String, Integer> map = parser.ranking(TestJLeaguePageKt.text);
        assertThat(map.put("ガンバ大阪", -1), is(3));
    }

    @Test
    public void WhenParsingTestDataFourthIsFCTokyo() {
        JLeagueRankingParser parser = new JLeagueRankingParser();
        Map<String, Integer> map = parser.ranking(TestJLeaguePageKt.text);
        assertThat(map.put("ＦＣ東京", -1), is(4));
    }

    @Test
    public void WhenParsingTestDataFifthIsKashiwa() {
        JLeagueRankingParser parser = new JLeagueRankingParser();
        Map<String, Integer> map = parser.ranking(TestJLeaguePageKt.text);
        assertThat(map.put("柏レイソル", -1), is(5));
    }

    @Test
    public void WhenParsingTestDataSixthIsKawasaki() {
        JLeagueRankingParser parser = new JLeagueRankingParser();
        Map<String, Integer> map = parser.ranking(TestJLeaguePageKt.text);
        assertThat(map.put("川崎フロンターレ", -1), is(6));
    }

    @Test
    public void WhenParsingTestDataSeventhIsSeresso() {
        JLeagueRankingParser parser = new JLeagueRankingParser();
        Map<String, Integer> map = parser.ranking(TestJLeaguePageKt.text);
        assertThat(map.put("セレッソ大阪", -1), is(7));
    }

    @Test
    public void WhenParsingTestDataEighthIsKobe() {
        JLeagueRankingParser parser = new JLeagueRankingParser();
        Map<String, Integer> map = parser.ranking(TestJLeaguePageKt.text);
        assertThat(map.put("ヴィッセル神戸", -1), is(8));
    }

    @Test
    public void WhenParsingTestDataNinthIsIwata() {
        JLeagueRankingParser parser = new JLeagueRankingParser();
        Map<String, Integer> map = parser.ranking(TestJLeaguePageKt.text);
        assertThat(map.put("ジュビロ磐田", -1), is(9));
    }

    @Test
    public void WhenParsingTestDataTenthIsTosu() {
        JLeagueRankingParser parser = new JLeagueRankingParser();
        Map<String, Integer> map = parser.ranking(TestJLeaguePageKt.text);
        assertThat(map.put("サガン鳥栖", -1), is(10));
    }

    @Test
    public void WhenParsingTestDataEleventhIsMarinosu() {
        JLeagueRankingParser parser = new JLeagueRankingParser();
        Map<String, Integer> map = parser.ranking(TestJLeaguePageKt.text);
        assertThat(map.put("横浜Ｆ・マリノス", -1), is(11));
    }

    @Test
    public void WhenParsingTestDataTwelfthIsKofu() {
        JLeagueRankingParser parser = new JLeagueRankingParser();
        Map<String, Integer> map = parser.ranking(TestJLeaguePageKt.text);
        assertThat(map.put("ヴァンフォーレ甲府", -1), is(12));
    }

    @Test
    public void WhenParsingTestDataThirteenthIsBegaruta() {
        JLeagueRankingParser parser = new JLeagueRankingParser();
        Map<String, Integer> map = parser.ranking(TestJLeaguePageKt.text);
        assertThat(map.put("ベガルタ仙台", -1), is(13));
    }

    @Test
    public void WhenParsingTestDataFourteenthIsShimizu() {
        JLeagueRankingParser parser = new JLeagueRankingParser();
        Map<String, Integer> map = parser.ranking(TestJLeaguePageKt.text);
        assertThat(map.put("清水エスパルス", -1), is(14));
    }

    @Test
    public void WhenParsingTestDataFifteenthIsSapporo() {
        JLeagueRankingParser parser = new JLeagueRankingParser();
        Map<String, Integer> map = parser.ranking(TestJLeaguePageKt.text);
        assertThat(map.put("北海道コンサドーレ札幌", -1), is(15));
    }

    @Test
    public void WhenParsingTestDataSixteenthIsHiroshima() {
        JLeagueRankingParser parser = new JLeagueRankingParser();
        Map<String, Integer> map = parser.ranking(TestJLeaguePageKt.text);
        assertThat(map.put("サンフレッチェ広島", -1), is(16));
    }

    @Test
    public void WhenParsingTestDataSeventeenthIsNiigata() {
        JLeagueRankingParser parser = new JLeagueRankingParser();
        Map<String, Integer> map = parser.ranking(TestJLeaguePageKt.text);
        assertThat(map.put("アルビレックス新潟", -1), is(17));
    }

    @Test
    public void WhenParsingTestDataEighteenthIsOmiya() {
        JLeagueRankingParser parser = new JLeagueRankingParser();
        Map<String, Integer> map = parser.ranking(TestJLeaguePageKt.text);
        assertThat(map.put("大宮アルディージャ", -1), is(18));
    }

    @Test
    public void WhenParsingEmptyReturnsEmptyMap() {
        JLeagueRankingParser parser = new JLeagueRankingParser();
        Map<String, Integer> map = parser.ranking("");
        assertThat(map.size(), is(0));
    }

    @Test
    public void WhenParsingInvalidDataReturnsEmptyMap() {
        JLeagueRankingParser parser = new JLeagueRankingParser();
        Map<String, Integer> map = parser.ranking("<html><body>hoge</body></html>");
        assertThat(map.size(), is(0));
    }
}
