package com.phicdy.totoanticipation.view.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.phicdy.totoanticipation.BuildConfig;
import com.phicdy.totoanticipation.R;
import com.phicdy.totoanticipation.model.storage.GameListStorageImpl;
import com.phicdy.totoanticipation.presenter.TotoAnticipationPresenter;
import com.phicdy.totoanticipation.view.TotoAnticipationView;

import org.jetbrains.annotations.NotNull;

public class TotoAnticipationActivity extends AppCompatActivity implements TotoAnticipationView {

    private WebView webView;
    private String totoNum;
    private TotoAnticipationPresenter presenter;
    public static final String KEY_TOTO_NUM = "keyTotoNum";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toto_anticipation);
        totoNum = getIntent().getStringExtra(KEY_TOTO_NUM);
        presenter = new TotoAnticipationPresenter(totoNum);
        presenter.setView(this);
        presenter.onCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
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

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initListener() {
        webView = (WebView) findViewById(R.id.wb_toto_web);
        if (BuildConfig.DEBUG && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                GameListStorageImpl storage = new GameListStorageImpl(TotoAnticipationActivity.this);
                presenter.onPageFinished(url, storage.list(totoNum));
            }
        });
    }

    @Override
    public void loadUrl(@NotNull String url) {
        webView.loadUrl(url);
    }

    @Override
    public void exec(@NotNull String javaScript) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            webView.evaluateJavascript(javaScript, null);
        } else {
            webView.loadUrl("javascript:" + javaScript);
        }
    }
}
