package com.phicdy.totoanticipation.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class RakutenTotoInfoParser {

    /**
     *
     * Parse rakuten toto info page and return game list.
     *
     * e.g.
     * <table class="tbl-result" border="1" cellspacing="0">
     * <thead>
     * <tr>
     * <th class="date">開催日</th>
     * <th class="place">競技場</th>
     * <th class="num">No</th>
     * <th colspan="3" class="game">指定試合（ホームvsアウェイ）</th>
     * </tr>
     * </thead>
     * <tbody>
     * <tr>
     * <td class="date">04/22</td>
     * <td class="place">埼玉</td>
     * <td class="num">1</td>
     * <td class="home">浦和</td>
     * <td class="vs">VS</td>
     * <td class="away">札幌</td>
     * </tr>
     * <tr>
     * <td class="date">04/22</td>
     * <td class="place">中銀スタ</td>
     * <td class="num">2</td>
     * <td class="home">甲府</td>
     * <td class="vs">VS</td>
     * <td class="away">Ｃ大阪</td>
     * </tr>
     * ...
     * </tbody>
     * </table>
     *
     * @param body HTML string of rakuten toto info page
     * @return Game list of toto or empty list
     */
    public ArrayList<Game> games(@NonNull String body) {
        ArrayList<Game> games = new ArrayList<>();
        if (TextUtils.isEmpty(body)) return games;
        Document bodyDoc = Jsoup.parse(body);
        Element gameTable = bodyDoc.getElementsByClass("tbl-result").first();
        if (gameTable == null) return games;
        Elements trs = gameTable.getElementsByTag("tr");
        for (int i = 1; i < trs.size(); i++) {
            Element tr = trs.get(i);
            Elements tds = tr.getElementsByTag("td");
            String homeTeam = tds.get(3).text();
            String awayTeam = tds.get(5).text();
            games.add(new Game(homeTeam, awayTeam));
        }
        return games;
    }
}
