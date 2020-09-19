package com.phicdy.totoanticipation.setting

interface SettingView {
    fun initView(isDeadlineNotify: Boolean)
    fun initListener()
    fun goToLicenseActivity()
    fun openPrivacyPolicy()
}
