package com.phicdy.totoanticipation.legacy.presenter

import com.phicdy.totoanticipation.legacy.model.storage.SettingStorage
import com.phicdy.totoanticipation.legacy.view.SettingView
import com.phicdy.totoanticipation.scheduler.DeadlineAlarm
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import java.util.Calendar

class SettingPresenterTest {

    private lateinit var presenter: SettingPresenter
    private lateinit var settingStorage: SettingStorage
    private lateinit var view: MockView

    @Before
    fun setup() {
        val alarm = Mockito.mock(DeadlineAlarm::class.java)
        val calendar = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        settingStorage = MockSettingStorage()
        presenter = SettingPresenter(alarm, calendar.time, settingStorage)
        view = MockView()
        presenter.setView(view)
    }

    @Test
    fun testOnCreate() {
        // For coverage
        presenter.onCreate()
    }

    @Test
    fun testOnResume() {
        // For coverage
        presenter.onResume()
    }

    @Test
    fun testOnPause() {
        // For coverage
        presenter.onPause()
    }

    @Test
    fun whenActivityCreatedViewIsInited() {
        presenter.activityCreate()
        assertTrue(view.isViewInited)
    }

    @Test
    fun whenActivityCreatedListenerIsInited() {
        presenter.activityCreate()
        assertTrue(view.isListenerInited)
    }

    @Test
    fun whenActivityCreatedDeadlineNotifyCheckIsCheckedIfEnabled() {
        settingStorage.isDeadlineNotify = true
        presenter.activityCreate()
        assertTrue(view.isDeadLineNotifyChecked)
    }

    @Test
    fun whenActivityCreatedDeadlineNotifyCheckIsNotCheckedIfDisabled() {
        settingStorage.isDeadlineNotify = false
        presenter.activityCreate()
        assertFalse(view.isDeadLineNotifyChecked)
    }

    @Test
    fun whenLicenseIsClickedLicenseActivityStarts() {
        presenter.onLicenseClicked()
        assertTrue(view.isLicenseActivityOpened)
    }

    @Test
    fun whenDeadlineNotifyIsEnabledEnabledIsStored() {
        presenter.onDeadlineNotificationSettingClicked(true)
        assertTrue(settingStorage.isDeadlineNotify)
    }

    @Test
    fun whenDeadlineNotifyIsDisabledIsStored() {
        presenter.onDeadlineNotificationSettingClicked(false)
        assertFalse(settingStorage.isDeadlineNotify)
    }

    private inner class MockView : SettingView {

        var isViewInited = false
        var isListenerInited = false
        var isDeadLineNotifyChecked = false
        var isLicenseActivityOpened = false

        override fun initView(isDeadlineNotify: Boolean) {
            this.isDeadLineNotifyChecked = isDeadlineNotify
            isViewInited = true
        }

        override fun initListener() {
            isListenerInited = true
        }

        override fun goToLicenseActivity() {
            isLicenseActivityOpened = true
        }

        override fun openPrivacyPolicy() {}
    }

    private inner class MockSettingStorage : SettingStorage {

        override var isDeadlineNotify = false
        override var isPrivacyPolicyAccepted = false
    }
}
