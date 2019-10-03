package com.phicdy.totoanticipation.api

import android.text.TextUtils
import com.phicdy.totoanticipation.domain.League
import com.phicdy.totoanticipation.domain.Team
import org.jsoup.Jsoup
import javax.inject.Inject

class JLeagueRankingParser @Inject constructor() {

    /**
     *
     * Parse J League ranking page and return ranking map.
     *
     * e.g.
     *
     * <tbody>
     * <tr class="row ">
     * <td>**</td>
     * <td>1</td>
     * <td class="tdTeam">[<span class="embS embKashima">鹿島アントラーズ</span>鹿島アントラーズ](/club/kashima/day/)</td>
     * <td>21</td>
     * <td>10</td>
     * <td>7</td>
     * <td>0</td>
     * <td>3</td>
     * <td>13</td>
     * <td>9</td>
     * <td>4</td>
     * <td nowrap><img width="10" height="10" alt="" src="/img/common/ico_match02.png"></img><img width="10" height="10" alt="" src="/img/common/ico_match01.png"></img><img width="10" height="10" alt="" src="/img/common/ico_match02.png"></img><img width="10" height="10" alt="" src="/img/common/ico_match01.png"></img><img width="10" height="10" alt="" src="/img/common/ico_match01.png"></img></td>
    </tr> *
     * <tr class="row ">
     * <td>**</td>
     * <td>2</td>
     * <td class="tdTeam">[<span class="embS embUrawa">浦和レッズ</span>浦和レッズ](/club/urawa/day/)</td>
     * <td>19</td>
     * <td>10</td>
     * <td>6</td>
     * <td>1</td>
     * <td>3</td>
     * <td>24</td>
     * <td>11</td>
     * <td>13</td>
     * <td nowrap><img width="10" height="10" alt="" src="/img/common/ico_match01.png"></img><img width="10" height="10" alt="" src="/img/common/ico_match01.png"></img><img width="10" height="10" alt="" src="/img/common/ico_match01.png"></img><img width="10" height="10" alt="" src="/img/common/ico_match02.png"></img><img width="10" height="10" alt="" src="/img/common/ico_match02.png"></img></td>
    </tr> *
     * ...
    </tbody> *
     *
     * @param body HTML body of J League ranking page
     * @return Ranking map or empty map. The key is team name and the value is ranking integer.
     */
    fun ranking(body: String, league: League): List<Team> {
        if (TextUtils.isEmpty(body)) return emptyList()
        val bodyDoc = Jsoup.parse(body)
        val rankingTbody = bodyDoc.getElementsByTag("tbody")
        val trs = rankingTbody.select("tr")
        val result = mutableListOf<Team>()
        for (i in trs.indices) {
            val tr = trs[i]
            val tds = tr.getElementsByTag("td")
            if (tds == null || tds.size < 3) continue

            // Second <td> is ranking
            // <td><i class="fa fa-minus"></i></td>
            // <td>1</td>
            // <td class="tdTeam">
            // <a href="/club/kashima/day/" class="embTxt">
            // <span class="embS embKashima">鹿島アントラーズ</span>鹿島アントラーズ</a>
            // </td>
            // ...
            val rankingTd = tds[1] ?: continue
            val ranking: Int
            try {
                ranking = Integer.valueOf(rankingTd.text())
            } catch (e: NumberFormatException) {
                e.printStackTrace()
                continue
            }

            // Third <td> includes team name
            val spans = tds[2].getElementsByTag("span")
            if (spans == null || spans.size == 0) continue
            val fullTeamName = spans.first().text()
            result.add(Team(name = fullTeamName, league = league, ranking = ranking))
        }
        return result
    }
}
