package com.phicdy.totoanticipation.legacy.model

import android.text.TextUtils
import org.jsoup.Jsoup
import java.util.ArrayList
import java.util.regex.Pattern

class RakutenTotoInfoParser {

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
    </tr> *
    </thead> *
     * <tbody>
     * <tr>
     * <td class="date">04/22</td>
     * <td class="place">埼玉</td>
     * <td class="num">1</td>
     * <td class="home">浦和</td>
     * <td class="vs">VS</td>
     * <td class="away">札幌</td>
    </tr> *
     * <tr>
     * <td class="date">04/22</td>
     * <td class="place">中銀スタ</td>
     * <td class="num">2</td>
     * <td class="home">甲府</td>
     * <td class="vs">VS</td>
     * <td class="away">Ｃ大阪</td>
    </tr> *
     * ...
    </tbody> *
    </table> *
     *
     * @param body HTML string of rakuten toto info page
     * @return Game list of toto or empty list
     */
    fun games(body: String): ArrayList<Game> {
        val games = ArrayList<Game>()
        if (TextUtils.isEmpty(body)) return games
        val bodyDoc = Jsoup.parse(body)
        val gameTable = bodyDoc.getElementsByClass("tbl-result").first() ?: return games
        val trs = gameTable.getElementsByTag("tr")
        for (i in 1 until trs.size) {
            val tr = trs[i]
            val tds = tr.getElementsByTag("td")
            val homeTeam = tds[3].text()
            val awayTeam = tds[5].text()
            games.add(Game(homeTeam, awayTeam))
        }
        return games
    }

    /**
     *
     * Parse rakuten toto info page and return deadline time.
     *
     * e.g. Return 13:50 from below
     * <table class=\"tbl-result-day\border=\"1\cellspacing=\"0\">
     * <tr>
     * <th rowspan=\"2\class=\"title\">販売予定<br></br>結果発表</th>
     * <th>販売開始日</th>
     * <th>販売終了日</th>
     * <th>結果発表確定日</th>
    </tr> *
     * <tr>
     * <td>2017年4月15日(土)</td>
     * <td>2017年4月22日(土)<br></br>(ネット13:50)</td>
     * <td>2017年4月24日(月)</td>
    </tr> *
    </table> *
     *
     * @param body HTML string of rakuten toto info page
     * @return Deadline time string like "13:50" or empty string
     */
    fun deadlineTime(body: String): String {
        if (body.isEmpty()) return ""
        val bodyDoc = Jsoup.parse(body)
        val gameTable = bodyDoc.getElementsByClass("tbl-result-day").first() ?: return ""
        val trs = gameTable.getElementsByTag("tr")
        if (trs.size != 2) return ""
        val dayElement = trs[1]
        val tds = dayElement.getElementsByTag("td")
        if (tds.size != 3) return ""
        val deadlineDayElement = tds[1]
        val deadlineDayStr = deadlineDayElement.text()
        val pattern = Pattern.compile("[0-9]+:[0-9]+")
        val matcher = pattern.matcher(deadlineDayStr)
        return if (matcher.find()) matcher.group() else ""
    }
}
