package com.phicdy.totoanticipation.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    /**
     *
     * Parse rakuten toto info page and return deadline time.
     *
     * e.g. Return 13:50 from below
     * <table class=\"tbl-result-day\border=\"1\cellspacing=\"0\">
     * <tr>
     * <th rowspan=\"2\class=\"title\">販売予定<br>結果発表</th>
     * <th>販売開始日</th>
     * <th>販売終了日</th>
     * <th>結果発表確定日</th>
     * </tr>
     * <tr>
     * <td>2017年4月15日(土)</td>
     * <td>2017年4月22日(土)<br>(ネット13:50)</td>
     * <td>2017年4月24日(月)</td>
     * </tr>
     * </table>
     *
     * @param body HTML string of rakuten toto info page
     * @return Deadline time string like "13:50" or empty string
     */
    public String deadlineTime(@NonNull String body) {
        if (TextUtils.isEmpty(body)) return "";
        Document bodyDoc = Jsoup.parse(body);
        Element gameTable = bodyDoc.getElementsByClass("tbl-result-day").first();
        if (gameTable == null) return "";
        Elements trs = gameTable.getElementsByTag("tr");
        if (trs.size() != 2) return "";
        Element dayElement = trs.get(1);
        Elements tds = dayElement.getElementsByTag("td");
        if (tds.size() != 3) return "";
        Element deadlineDayElement = tds.get(1);
        String deadlineDayStr = deadlineDayElement.text();
        Pattern pattern = Pattern.compile("[0-9]+:[0-9]+");
        Matcher matcher = pattern.matcher(deadlineDayStr);
        if (matcher.find()) return matcher.group();
        return "";
    }
}
