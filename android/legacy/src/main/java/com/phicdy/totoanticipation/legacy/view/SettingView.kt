package com.phicdy.totoanticipation.legacy.view

interface SettingView {
    fun initView(isDeadlineNotify: Boolean)
    fun initListener()
    fun goToLicenseActivity()
    fun openPrivacyPolicy()
}
