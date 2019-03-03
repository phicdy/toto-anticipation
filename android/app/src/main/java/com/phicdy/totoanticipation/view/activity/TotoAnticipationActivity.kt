package com.phicdy.totoanticipation.view.activity

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.webkit.WebView
import android.webkit.WebViewClient
import com.phicdy.totoanticipation.BuildConfig
import com.phicdy.totoanticipation.R
import com.phicdy.totoanticipation.model.storage.GameListStorageImpl
import com.phicdy.totoanticipation.presenter.TotoAnticipationPresenter
import com.phicdy.totoanticipation.view.TotoAnticipationView

class TotoAnticipationActivity : AppCompatActivity(), TotoAnticipationView {

    private lateinit var webView: WebView
    private lateinit var totoNum: String
    private lateinit var presenter: TotoAnticipationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toto_anticipation)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title
        totoNum = intent.getStringExtra(KEY_TOTO_NUM)
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
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true)
        }
        val webSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {

            override fun onPageFinished(view: WebView, url: String) {
                val storage = GameListStorageImpl(this@TotoAnticipationActivity)
                presenter.onPageFinished(url, storage.list(totoNum))
            }
        }
    }

    override fun loadUrl(url: String) {
        webView.loadUrl(url)
    }

    override fun exec(javaScript: String) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript(javaScript, null)
        } else {
            webView.loadUrl("javascript:$javaScript")
        }
    }

    companion object {
        const val KEY_TOTO_NUM = "keyTotoNum"
    }
}
