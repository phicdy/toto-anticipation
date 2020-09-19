package com.phicdy.totoanticipation.setting

import com.phicdy.totoanticipation.scheduler.DeadlineAlarm
import com.phicdy.totoanticipation.storage.SettingStorage
import java.util.Date


class SettingPresenter(private val alarm: DeadlineAlarm, private val deadline: Date,
                       private val storage: SettingStorage) {
    private lateinit var view: SettingView

    fun setView(view: SettingView) {
        this.view = view
    }

    fun activityCreate() {
        view.initView(storage.isDeadlineNotify)
        view.initListener()
    }

    fun onLicenseClicked() {
        view.goToLicenseActivity()
    }

    fun onDeadlineNotificationSettingClicked(checked: Boolean) {
        storage.isDeadlineNotify = checked
        if (checked) {
            if (deadline.time == 0L) return
            alarm.setAtNoonOf(deadline)
        } else {
            alarm.cancel()
        }
    }

    fun onPrivacyPolicyClicked() {
        view.openPrivacyPolicy()
    }
}
