package com.phicdy.totoanticipation.feature.totoanticipation

interface TotoAnticipationView {
    fun initListener()
    fun loadUrl(url: String)
    fun exec(javaScript: String)
}