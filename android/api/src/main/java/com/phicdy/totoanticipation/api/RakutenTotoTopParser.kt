package com.phicdy.totoanticipation.api

import com.phicdy.totoanticipation.domain.Toto
import org.jsoup.Jsoup
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class RakutenTotoTopParser @Inject constructor() {

    /**
     *
     * Parse rakuten top page and return latest toto number like "0923".
     *
     * e.g. 0922 and 0924 does not have toto, 0925 is not opened, 0923 is latest.
     *
     *
     *  *
     * <dl>
     * <dt>[第922回](/toto/schedule/0922/)</dt>
     * <dd class="type">
     * <img src="/toto/schedule/images/icon_toto2.gif" width="35" height="24" align="toto Goal2"></img>
    </dd> *
     * <dd class="span">2017年04月14日(金)～2017年04月21日(金)</dd>
     * <dd class="date">04月24日(月)</dd>
     * <dd class="status">[販売中](/toto/schedule/0922/)</dd>
    </dl> *
     *
     *  *
     * <dl>
     * <dt>[第923回](/toto/schedule/0923/)</dt>
     * <dd class="type">
     * <img src="/toto/schedule/images/icon_toto.gif" width="35" height="24" align="toto"></img> <img src="/toto/schedule/images/icon_minitoto.gif" width="35" height="24" align="mini toto"></img> <img src="/toto/schedule/images/icon_toto3.gif" width="35" height="24" align="toto Goal3"></img></dd>
     * <dd class="span">2017年04月15日(土)～2017年04月22日(土)</dd>
     * <dd class="date">04月24日(月)</dd>
     * <dd class="status">[販売中](/toto/schedule/0923/)</dd>
    </dl> *
     *
     *  *
     * <dl>
     * <dt>[第924回](/toto/schedule/0924/)</dt>
     * <dd class="type">
     * <img src="/toto/schedule/images/icon_minitoto.gif" width="35" height="24" align="mini toto"></img> <img src="/toto/schedule/images/icon_toto3.gif" width="35" height="24" align="toto Goal3"></img></dd>
     * <dd class="span">2017年04月19日(水)～2017年04月26日(水)</dd>
     * <dd class="date">04月27日(木)</dd>
     * <dd class="status">[販売中](/toto/schedule/0924/)</dd>
    </dl> *
     *
     *  *
     * <dl>
     * <dt>[第925回](/toto/schedule/0925/)</dt>
     * <dd class="type">
     * <img src="/toto/schedule/images/icon_toto.gif" width="35" height="24" align="toto"></img> <img src="/toto/schedule/images/icon_minitoto.gif" width="35" height="24" align="mini toto"></img> <img src="/toto/schedule/images/icon_toto3.gif" width="35" height="24" align="toto Goal3"></img></dd>
     * <dd class="span">2017年04月22日(土)～2017年04月29日(土)</dd>
     * <dd class="date">05月01日(月)</dd>
     * <dd class="status">販売前</dd>
    </dl> *
     *
     * ...
     *
     *
     * @param body HTML string of rakuten toto top page
     * @return Latest toto
     */
    fun latestToto(body: String): Toto {
        if (body.isEmpty()) return Toto(Toto.DEFAULT_NUMBER, Date())
        val bodyDoc = Jsoup.parse(body)
        val scheduleTable = bodyDoc.getElementsByClass("table")
        val lis = scheduleTable.select("li")
        var latestNumber = ""
        var deadline: Date
        for (i in lis.indices) {
            val li = lis[i]
            // Check hold on now or not
            val statusEl = li.getElementsByClass("status").first() ?: continue
            val totoStatus = statusEl.text()
            if (totoStatus != "販売中") continue

            // Toto icon gif indicates toto is held on
            val totoImages = li.getElementsByTag("img")
            for (totoImage in totoImages) {
                if (totoImage.attr("src").contains("icon_toto.gif")) {
                    // Link is like "/toto/schedule/0923/", split number only
                    val link = statusEl.select("a[href]").attr("href")
                    latestNumber = link.replace("[^0-9]".toRegex(), "")
                    break
                }
            }
            if (latestNumber == "") continue

            // Parse deadline date from "<dd class="span">2017年04月14日(金)～2017年04月21日(金)</dd>"
            val span = li.getElementsByClass("span").first() ?: continue
// Cut "2017年04月14日(金)～" and "(金)" at last
            val indexDeadlineStart = 15
            val indexDeadlineHi = 26
            var deadlineDateStr = span.text().substring(indexDeadlineStart, indexDeadlineHi)
            deadlineDateStr += " 00:00:00"
            val format = SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss", Locale.JAPAN)
            try {
                deadline = format.parse(deadlineDateStr)
                return Toto(latestNumber, deadline)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

        }
        return Toto(Toto.DEFAULT_NUMBER, Date())
    }
}
