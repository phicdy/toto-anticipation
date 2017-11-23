package com.phicdy.totoanticipation.presenter

import com.phicdy.totoanticipation.view.TotoAnticipationView

class TotoAnticipationPresenter(private val totoNum: String) : Presenter {
    lateinit var view: TotoAnticipationView
    val totoTopUrl = "http://sp.toto-dream.com/dci/sp/I/IMA/IMA01.do?op=inittotoSP&holdCntId="

    override fun onCreate() {
        view.initListener()
    }

    override fun onResume() {
        view.loadUrl(totoTopUrl + totoNum)
    }

    override fun onPause() {

    }
}
