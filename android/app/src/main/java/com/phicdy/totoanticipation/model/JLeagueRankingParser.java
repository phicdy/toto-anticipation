package com.phicdy.totoanticipation.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;
import java.util.Map;

public class JLeagueRankingParser {

    /**
     *
     * Parse J League ranking page and return ranking map.
     *
     * e.g.
     *
     * <tbody>
     * <tr class="row ">
     * <td><i class="fa fa-minus"></i></td>
     * <td>1</td>
     * <td class="tdTeam"><a href="/club/kashima/day/" class="embTxt"><span class="embS embKashima">鹿島アントラーズ</span>鹿島アントラーズ</a></td>
     * <td>21</td>
     * <td>10</td>
     * <td>7</td>
     * <td>0</td>
     * <td>3</td>
     * <td>13</td>
     * <td>9</td>
     * <td>4</td>
     * <td nowrap><img width="10" height="10" alt="" src="/img/common/ico_match02.png"><img width="10" height="10" alt="" src="/img/common/ico_match01.png"><img width="10" height="10" alt="" src="/img/common/ico_match02.png"><img width="10" height="10" alt="" src="/img/common/ico_match01.png"><img width="10" height="10" alt="" src="/img/common/ico_match01.png"></td>
     * </tr>
     * <tr class="row ">
     * <td><i class="fa fa-minus"></i></td>
     * <td>2</td>
     * <td class="tdTeam"><a href="/club/urawa/day/" class="embTxt"><span class="embS embUrawa">浦和レッズ</span>浦和レッズ</a></td>
     * <td>19</td>
     * <td>10</td>
     * <td>6</td>
     * <td>1</td>
     * <td>3</td>
     * <td>24</td>
     * <td>11</td>
     * <td>13</td>
     * <td nowrap><img width="10" height="10" alt="" src="/img/common/ico_match01.png"><img width="10" height="10" alt="" src="/img/common/ico_match01.png"><img width="10" height="10" alt="" src="/img/common/ico_match01.png"><img width="10" height="10" alt="" src="/img/common/ico_match02.png"><img width="10" height="10" alt="" src="/img/common/ico_match02.png"></td>
     * </tr>
     * ...
     * </tbody>
     *
     * @param body HTML body of J League ranking page
     * @return Ranking map or empty map. The key is team name and the value is ranking integer.
     */
    public Map<String, Integer> ranking(@NonNull String body) {
        if (TextUtils.isEmpty(body)) return new HashMap<>();
        Document bodyDoc = Jsoup.parse(body);
        Elements rankingTbody = bodyDoc.getElementsByTag("tbody");
        Elements trs = rankingTbody.select("tr");
        Map<String, Integer> result = new HashMap<>();
        for (int i = 0; i < trs.size(); i++) {
            Element tr = trs.get(i);
            Elements tds = tr.getElementsByTag("td");
            if (tds == null || tds.size() < 3) continue;

            // Second <td> is ranking
            // <td><i class="fa fa-minus"></i></td>
            // <td>1</td>
            // <td class="tdTeam">
            // <a href="/club/kashima/day/" class="embTxt">
            // <span class="embS embKashima">鹿島アントラーズ</span>鹿島アントラーズ</a>
            // </td>
            // ...
            Element rankingTd = tds.get(1);
            if (rankingTd == null) continue;
            int ranking;
            try {
                ranking = Integer.valueOf(rankingTd.text());
            } catch (NumberFormatException e) {
                e.printStackTrace();
                continue;
            }

            // Third <td> includes team name
            Elements spans = tds.get(2).getElementsByTag("span");
            if (spans == null || spans.size() == 0) continue;
            String fullTeamName = spans.first().text();
            result.put(fullTeamName, ranking);
        }
        return result;
    }
}
