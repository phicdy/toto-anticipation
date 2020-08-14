package com.phicdy.totoanticipation.legacy.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.widget.Toolbar
import com.phicdy.totoanticipation.legacy.BuildConfig
import com.phicdy.totoanticipation.legacy.R
import com.phicdy.totoanticipation.legacy.presenter.TotoAnticipationPresenter
import com.phicdy.totoanticipation.legacy.view.TotoAnticipationView
import com.phicdy.totoanticipation.storage.GameListStorage
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class TotoAnticipationActivity : DaggerAppCompatActivity(), TotoAnticipationView {

    private lateinit var webView: WebView
    private lateinit var totoNum: String
    private lateinit var presenter: TotoAnticipationPresenter

    @Inject
    lateinit var storage: GameListStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toto_anticipation)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title
        totoNum = intent.getStringExtra(KEY_TOTO_NUM)
                ?: throw IllegalArgumentException("toto number is null")
        presenter = TotoAnticipationPresenter(totoNum)
        presenter.view = this
        presenter.onCreate()
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(KEY_TOTO_NUM, totoNum)
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        totoNum = savedInstanceState.getString(KEY_TOTO_NUM) ?: ""
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun initListener() {
        webView = findViewById(R.id.wb_toto_web)
        if (BuildConfig.DEBUG) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView, url: String) {
                presenter.onPageFinished(url, storage.list(totoNum))
            }
        }
    }

    override fun loadUrl(url: String) {
        webView.loadUrl(url)
    }

    override fun exec(javaScript: String) {
        webView.evaluateJavascript(javaScript, null)
    }

    companion object {
        const val KEY_TOTO_NUM = "keyTotoNum"
    }
}
