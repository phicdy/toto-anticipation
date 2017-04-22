package com.phicdy.totoanticipation.model;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RakutenTotoTopParser {

    /**
     *
     * Parse rakuten top page and return latest toto number like "0923".
     *
     * e.g. 0922 and 0924 does not have toto, 0925 is not opened, 0923 is latest.
     *
     * <ul class="table">
     * <li>
     * <dl>
     *     <dt><a href="/toto/schedule/0922/">第922回</a></dt>
     *     <dd class="type">
     *         <img src="/toto/schedule/images/icon_toto2.gif" width="35" height="24" align="toto Goal2">
     *     </dd>
     *     <dd class="span">2017年04月14日(金)～2017年04月21日(金)</dd>
     *     <dd class="date">04月24日(月)</dd>
     *     <dd class="status"><a href="/toto/schedule/0922/">販売中</a></dd>
     * </dl>
     * </li>
     * <li>
     * <dl>
     *     <dt><a href="/toto/schedule/0923/">第923回</a></dt>
     *     <dd class="type">
     *     <img src="/toto/schedule/images/icon_toto.gif" width="35" height="24" align="toto"> <img src="/toto/schedule/images/icon_minitoto.gif" width="35" height="24" align="mini toto"> <img src="/toto/schedule/images/icon_toto3.gif" width="35" height="24" align="toto Goal3"></dd>
     *     <dd class="span">2017年04月15日(土)～2017年04月22日(土)</dd>
     *     <dd class="date">04月24日(月)</dd>
     *     <dd class="status"><a href="/toto/schedule/0923/">販売中</a></dd>
     * </dl>
     * </li>
     * <li>
     * <dl>
     *     <dt><a href="/toto/schedule/0924/">第924回</a></dt>
     *     <dd class="type">
     *     <img src="/toto/schedule/images/icon_minitoto.gif" width="35" height="24" align="mini toto"> <img src="/toto/schedule/images/icon_toto3.gif" width="35" height="24" align="toto Goal3"></dd>
     *     <dd class="span">2017年04月19日(水)～2017年04月26日(水)</dd>
     *     <dd class="date">04月27日(木)</dd>
     *     <dd class="status"><a href="/toto/schedule/0924/">販売中</a></dd>
     * </dl>
     * </li>
     * <li>
     * <dl>
     * <dt><a href="/toto/schedule/0925/">第925回</a></dt>
     *     <dd class="type">
     *     <img src="/toto/schedule/images/icon_toto.gif" width="35" height="24" align="toto"> <img src="/toto/schedule/images/icon_minitoto.gif" width="35" height="24" align="mini toto"> <img src="/toto/schedule/images/icon_toto3.gif" width="35" height="24" align="toto Goal3"></dd>
     *     <dd class="span">2017年04月22日(土)～2017年04月29日(土)</dd>
     *     <dd class="date">05月01日(月)</dd>
     *     <dd class="status">販売前</dd>
     * </dl>
     * </li>
     * ...
     * </ul>
     *
     * @param body HTML string of rakuten toto top page
     * @return Latest number of toto like "0923" or empty string
     */
    public String latestTotoNumber(@NonNull String body) {
        if (TextUtils.isEmpty(body)) return "";
        Document bodyDoc = Jsoup.parse(body);
        Elements scheduleTable = bodyDoc.getElementsByClass("table");
        Elements lis = scheduleTable.select("li");
        String latestNumber = "";
        for (int i = 1; i < lis.size(); i++) {
            Element li = lis.get(i);
            // Check hold on now or not
            Element statusEl = li.getElementsByClass("status").first();
            if (statusEl == null) continue;
            String totoStatus = statusEl.text();
            if (!totoStatus.equals("販売中")) continue;

            // Toto icon gif indicates toto is held on
            Elements totoImages = li.getElementsByTag("img");
            for (Element totoImage : totoImages) {
                if (totoImage.attr("src").contains("icon_toto.gif")) {
                    // Link is like "/toto/schedule/0923/", split number only
                    String link = statusEl.select("a[href]").attr("href");
                    latestNumber = link.replaceAll("[^0-9]", "");
                    break;
                }
            }
        }
        return latestNumber;
    }
}