package com.phicdy.totoanticipation.view.activity;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.webkit.WebViewFragment;

import com.phicdy.totoanticipation.R;

public class TotoAnticipationActivity extends AppCompatActivity {

    private WebView webView;
    private String totoNum;
    public static final String KEY_TOTO_NUM = "keyTotoNum";

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toto_anticipation);

        totoNum = getIntent().getStringExtra(KEY_TOTO_NUM);

        webView = (WebView) findViewById(R.id.wb_toto_web);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        webView.loadUrl("http://sp.toto-dream.com/dci/sp/I/IMA/IMA01.do?op=inittotoSP&holdCntId=" + totoNum);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(KEY_TOTO_NUM, totoNum);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        totoNum = savedInstanceState.getString(KEY_TOTO_NUM);
    }
}
