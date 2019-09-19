package com.phicdy.totoanticipation.legacy.view.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import com.phicdy.totoanticipation.legacy.R
import com.phicdy.totoanticipation.legacy.model.GameHistory

class GameHistoryFragment : Fragment() {

    private lateinit var webView: WebView
    private lateinit var homeTeam: String
    private lateinit var awayTeam: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_HOME_TEAM) && it.containsKey(ARG_AWAY_TEAM)) {
                homeTeam = it.getString(ARG_HOME_TEAM, "")
                awayTeam = it.getString(ARG_AWAY_TEAM, "")
            }
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        webView = rootView.findViewById<View>(R.id.item_detail) as WebView
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient()
        return rootView
    }

    override fun onResume() {
        super.onResume()
        webView.loadUrl(GameHistory().url(homeTeam, awayTeam))
    }

    companion object {
        const val ARG_HOME_TEAM = "hogeTeam"
        const val ARG_AWAY_TEAM = "awayTeam"
    }
}
