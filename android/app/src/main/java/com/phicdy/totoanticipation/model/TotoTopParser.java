package com.phicdy.totoanticipation.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class TotoTopParser {

    /**
     *
     * Parse top page of Toto and return latest link of lottery.
     * First value of <td class="pattern2"> indicates toto is hold on or not.
     * If it is "○", toto is hold on, return the link in <td class="pattern1">.
     *
     * e.g. No.922 does not have toto, latest link is No.923.
     * <table class="totoschedule" cellspacing="0" cellpadding="0" width="274" border="0">
     *     <tbody>
     *         <tr>
     *             <th><img src="img/index/img_table_now.gif" width="89" height="23"></th>
     *             <th><img src="img/index/img_table_toto.gif" alt="toto" width="38" height="23"></th>
     *             <th><img src="img/index/img_table_minitotoa.gif" alt="minitotoA" width="55" height="23"></th>
     *             <th><img src="img/index/img_table_minitotob.gif" alt="minitotoB" width="53" height="23"></th>
     *             <th><img id="totogoal3_table_img" src="img/index/img_table_totogoal3-2.gif" alt="totogoal3" width="39" height="23"></th>
     *         </tr>
     *         <tr class="first">
     *             <td class="pattern1">
     *                 <a href="http://www.toto-dream.com/dci/I/IPA/IPA01.do?op=disptotoLotInfo&amp;holdCntId=0922">第922回</a>
     *             </td>
     *             <td class="pattern2"><span class="line">-</span></td>
     *             <td class="pattern2"><span class="line">-</span></td>
     *             <td class="pattern2"><span class="line">-</span></td>
     *             <td class="pattern2"><span class="circle">○</span></td>
     *         </tr>
     *         <tr>
     *             <td class="pattern1">
     *                 <a href="http://www.toto-dream.com/dci/I/IPA/IPA01.do?op=disptotoLotInfo&amp;holdCntId=0923">第923回</a>
     *             </td>
     *             <td class="pattern2"><span class="circle">○</span></td>
     *             <td class="pattern2"><span class="circle">○</span></td>
     *             <td class="pattern2"><span class="circle">○</span></td>
     *             <td class="pattern2"><span class="circle">○</span></td>
     *         </tr>
     *     </tbody>
     * </table>
     *
     * @param body HTML string of toto top page
     * @return Latest link of toto or empty string
     */
    public String latestLotteryLink(@NonNull String body) {
        if (TextUtils.isEmpty(body)) return "";
        Document bodyDoc = Jsoup.parse(body);
        Elements scheduleTable = bodyDoc.getElementsByClass("totoschedule");
        Elements trs = scheduleTable.select("tr");
        String latestLink = "";
        for (int i = 1; i < trs.size(); i++) {
            Element tr = trs.get(i);
            Elements pattern2 = tr.getElementsByClass("pattern2");
            String totoStatus = pattern2
                    .first()
                    .getElementsByTag("span")
                    .first()
                    .text();
            if (totoStatus.equals("○")) {
                latestLink = tr.select("a[href]").attr("href");
            }
        }
        return latestLink;
    }
}
