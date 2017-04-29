package com.phicdy.totoanticipation.model;

import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class RakutenTotoInfoParserTest {

    @Test
    public void gameListReturns() {
        ArrayList<Game> expectedGames = new ArrayList<>();
        expectedGames.add(new Game("浦和", "札幌"));
        expectedGames.add(new Game("甲府", "Ｃ大阪"));
        expectedGames.add(new Game("広島", "仙台"));
        expectedGames.add(new Game("鹿島", "磐田"));
        expectedGames.add(new Game("柏", "横浜Ｍ"));
        expectedGames.add(new Game("新潟", "Ｆ東京"));
        expectedGames.add(new Game("鳥栖", "神戸"));
        expectedGames.add(new Game("名古屋", "山口"));
        expectedGames.add(new Game("横浜Ｃ", "千葉"));
        expectedGames.add(new Game("京都", "松本"));
        expectedGames.add(new Game("愛媛", "長崎"));
        expectedGames.add(new Game("東京Ｖ", "群馬"));
        expectedGames.add(new Game("町田", "徳島"));
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
