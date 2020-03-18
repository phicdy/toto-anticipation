package com.phicdy.totoanticipation.legacy.view.activity

import android.os.Bundle
import android.webkit.WebView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.phicdy.totoanticipation.legacy.R

class LicenseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_license)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.title = title
        val webView = findViewById<WebView>(R.id.license_web_view)
        webView.loadUrl("file:///android_asset/license.html")
    }
}
