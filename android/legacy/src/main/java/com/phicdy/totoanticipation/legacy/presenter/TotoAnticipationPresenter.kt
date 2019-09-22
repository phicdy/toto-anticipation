package com.phicdy.totoanticipation.legacy.presenter

import android.util.Log
import com.phicdy.totoanticipation.legacy.model.Game
import com.phicdy.totoanticipation.legacy.view.TotoAnticipationView

class TotoAnticipationPresenter(private val totoNum: String) : Presenter {
    lateinit var view: TotoAnticipationView
    val totoTopUrl = "http://sp.toto-dream.com/dci/sp/I/IMA/IMA01.do?op=inittotoSP&holdCntId="
    val TAG = "TotoAnticipate"

    override fun onCreate() {
        view.initListener()
    }

    override fun onResume() {
        view.loadUrl(totoTopUrl + totoNum)
    }

    override fun onPause() {

    }

    fun onPageFinished(url: String, games: List<Game>) {
        Log.d(TAG, "URL: " + url)
        if (url == totoTopUrl + totoNum) {
            // Buy page URL is dynamic in toto top page, click buy now <a> tag
            val script = "var aTags = document.getElementsByTagName('a');\n" +
                    "var searchText = '今すぐ購入する';\n" +
                    "for (var i = 0; i < aTags.length; i++) {\n" +
                    "  if (aTags[i].textContent == searchText) {\n" +
                    "    aTags[i].click();\n" +
                    "    break;\n" +
                    "  }\n" +
                    "}"
            view.exec(script)
        } else {
            // Click checkbox from user anticipation
            for (i in games.indices) {
                val builder = StringBuilder()
                // Class of each checkbox is like "chkbox_1_0".
                // First num is index, it starts from 1, second num is home or draw or away.
                // home: 0, draw: 1, away: 2
                builder.append("var nodeList").append(i + 1)
                        .append(" = document.getElementsByName('chkbox_").append(i + 1)
                when (games[i].anticipation) {
                    Game.Anticipation.HOME -> builder.append("_0');")
                    Game.Anticipation.DRAW -> builder.append("_1');")
                    Game.Anticipation.AWAY -> builder.append("_2');")
                }
                builder.append("nodeList").append(i + 1).append("[0].checked = true;")
                Log.d(TAG, "script:" + builder.toString())
                view.exec(builder.toString())
            }
        }
    }
}
