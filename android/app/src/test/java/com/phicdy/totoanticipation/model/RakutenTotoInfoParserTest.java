package com.phicdy.totoanticipation.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RakutenTotoInfoParserTest {

    @Test
    public void gameListReturns() {
        ArrayList<Game> expectedGames = new ArrayList<>();
        expectedGames.add(new Game("浦和レッズ", "北海道コンサドーレ札幌"));
        expectedGames.add(new Game("ヴァンフォーレ甲府", "セレッソ大阪"));
        expectedGames.add(new Game("サンフレッチェ広島", "ベガルタ仙台"));
        expectedGames.add(new Game("鹿島アントラーズ", "ジュビロ磐田"));
        expectedGames.add(new Game("柏レイソル", "横浜F・マリノス"));
        expectedGames.add(new Game("アルビレックス新潟", "FC東京"));
        expectedGames.add(new Game("サガン鳥栖", "ヴィッセル神戸"));
        expectedGames.add(new Game("名古屋グランパス", "レノファ山口FC"));
        expectedGames.add(new Game("横浜FC", "ジェフユナイテッド千葉"));
        expectedGames.add(new Game("京都サンガF.C.", "松本山雅FC"));
        expectedGames.add(new Game("愛媛FC", "V・ファーレン長崎"));
        expectedGames.add(new Game("東京ヴェルディ", "ザスパクサツ群馬"));
        expectedGames.add(new Game("FC町田ゼルビア", "徳島ヴォルティス"));
        RakutenTotoInfoParser parser = new RakutenTotoInfoParser();
        ArrayList<Game> actualGames = parser.games(TestRakutenTotoInfoPage.text);
        for (int i = 0; i < actualGames.size(); i++) {
            assertThat(actualGames.get(i).homeTeam, is(expectedGames.get(i).homeTeam));
            assertThat(actualGames.get(i).awayTeam, is(expectedGames.get(i).awayTeam));
        }
    }

    @Test
    public void EmptyListReturnsWhenEmptyHtml() {
        RakutenTotoInfoParser parser = new RakutenTotoInfoParser();
        assertThat(parser.games("").size(), is(0));
    }

    @Test
    public void EmptyListReturnsWhenInvalidHtml() {
        RakutenTotoInfoParser parser = new RakutenTotoInfoParser();
        assertThat(parser.games("<html><body>hoge</body></html>").size(), is(0));
    }
}
