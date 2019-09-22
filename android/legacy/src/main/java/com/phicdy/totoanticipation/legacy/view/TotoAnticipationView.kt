package com.phicdy.totoanticipation.legacy.view

interface TotoAnticipationView {
    fun initListener()
    fun loadUrl(url: String)
    fun exec(javaScript: String)
}