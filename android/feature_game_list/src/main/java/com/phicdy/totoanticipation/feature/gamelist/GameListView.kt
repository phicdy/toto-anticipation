package com.phicdy.totoanticipation.feature.gamelist

interface GameListView {
    fun initList()
    fun hideList()
    fun hideAnticipationMenu()
    fun setTitleFrom(xxTh: String, deadline: String)
    fun startProgress()
    fun stopProgress()
    fun showEmptyView()
    fun goToSetting()
    fun showAnticipationStart()
    fun showAnticipationFinish()
    fun notifyDataSetChanged()
    fun showAnticipationNotSupport()
    fun showPrivacyPolicyDialog()
}
