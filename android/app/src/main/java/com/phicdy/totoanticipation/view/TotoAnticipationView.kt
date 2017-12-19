package com.phicdy.totoanticipation.view

interface TotoAnticipationView {
    fun initListener()
    fun loadUrl(url: String)
    fun exec(javaScript: String)
}