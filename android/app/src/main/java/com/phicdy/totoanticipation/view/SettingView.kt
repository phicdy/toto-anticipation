package com.phicdy.totoanticipation.view

interface SettingView {
    fun initView(isDeadlineNotify: Boolean)
    fun initListener()
    fun goToLicenseActivity()
    fun openPrivacyPolicy()
}
