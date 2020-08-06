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
import com.phicdy.totoanticipation.legacy.model.TeamInfoMapper

class TeamInfoDetailFragment : Fragment() {

    private lateinit var webView: WebView
    private lateinit var team: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            if (it.containsKey(ARG_TEAM)) {
                team = it.getString(ARG_TEAM, "")
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
        webView.loadUrl(TeamInfoMapper().yahooNewsUrl(team))
    }

    companion object {

        const val ARG_TEAM = "team"
    }
}
