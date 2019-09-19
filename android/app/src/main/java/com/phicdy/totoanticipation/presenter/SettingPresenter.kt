package com.phicdy.totoanticipation.presenter

import com.phicdy.totoanticipation.legacy.model.storage.SettingStorage
import com.phicdy.totoanticipation.legacy.presenter.Presenter
import com.phicdy.totoanticipation.legacy.view.SettingView
import com.phicdy.totoanticipation.model.scheduler.DeadlineAlarm
import java.util.Date


class SettingPresenter(private val alarm: DeadlineAlarm, private val deadline: Date,
                       private val storage: SettingStorage) : Presenter {
    private lateinit var view: SettingView

    fun setView(view: SettingView) {
        this.view = view
    }

    override fun onCreate() {}

    override fun onResume() {}

    override fun onPause() {}

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
