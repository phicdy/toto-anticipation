package com.phicdy.totoanticipation.legacy.view

interface GameListView {
    fun initList()
    fun hideList()
    fun hideFab()
    fun hideAnticipationMenu()
    fun setTitleFrom(xxTh: String, deadline: String)
    fun startProgress()
    fun stopProgress()
    fun showEmptyView()
    fun startTotoAnticipationActivity(totoNum: String)
    fun goToSetting()
    fun showAnticipationStart()
    fun showAnticipationFinish()
    fun notifyDataSetChanged()
    fun showAnticipationNotSupport()
    fun showPrivacyPolicyDialog()
}
