package com.phicdy.totoanticipation.view.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.phicdy.totoanticipation.R;
import com.phicdy.totoanticipation.model.TeamInfoMapper;

public class TeamInfoFragment extends Fragment {

    public static final String ARG_TEAM = "team";

    private WebView webView;
    private String team;

    public TeamInfoFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(ARG_TEAM)) {
            team = getArguments().getString(ARG_TEAM);
        }
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);
        webView = ((WebView) rootView.findViewById(R.id.item_detail));
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
             @Override
             public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        webView.loadUrl(TeamInfoMapper.yahooNewsUrl(team));
    }
}
