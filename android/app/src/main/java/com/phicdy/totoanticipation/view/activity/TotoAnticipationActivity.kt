package com.phicdy.totoanticipation.view.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.phicdy.totoanticipation.BuildConfig
import com.phicdy.totoanticipation.R
import com.phicdy.totoanticipation.legacy.model.storage.GameListStorageImpl
import com.phicdy.totoanticipation.legacy.presenter.TotoAnticipationPresenter
import com.phicdy.totoanticipation.legacy.view.TotoAnticipationView

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
        if (BuildConfig.DEBUG) {
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
        webView.evaluateJavascript(javaScript, null)
    }

    companion object {
        const val KEY_TOTO_NUM = "keyTotoNum"
    }
}
