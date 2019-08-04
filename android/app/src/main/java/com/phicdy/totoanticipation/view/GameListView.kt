package com.phicdy.totoanticipation.view

interface GameListView {
    fun initList()
    fun setTitleFrom(xxTh: String, deadline: String)
    fun startProgress()
    fun stopProgress()
    fun startTotoAnticipationActivity(totoNum: String)
    fun goToSetting()
    fun showAnticipationStart()
    fun showAnticipationFinish()
    fun notifyDataSetChanged()
    fun showAnticipationNotSupport()
    fun showPrivacyPolicyDialog()
}
