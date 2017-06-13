package com.phicdy.totoanticipation.presenter;

import com.phicdy.totoanticipation.model.scheduler.DeadlineAlarm;
import com.phicdy.totoanticipation.model.storage.SettingStorage;
import com.phicdy.totoanticipation.view.SettingView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Calendar;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class SettingPresenterTest {

    private SettingPresenter presenter;
    private SettingStorage settingStorage;
    private MockView view;

    @Before
    public void setup() {
        DeadlineAlarm alarm = Mockito.mock(DeadlineAlarm.class);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        settingStorage = new MockSettingStorage();
        presenter = new SettingPresenter(alarm, calendar.getTime(), settingStorage);
        view = new MockView();
        presenter.setView(view);
    }

    @Test
    public void testOnCreate() {
        // For coverage
        presenter.onCreate();
    }

    @Test
    public void testOnResume() {
        // For coverage
        presenter.onResume();
    }

    @Test
    public void testOnPause() {
        // For coverage
        presenter.onPause();
    }

    @Test
    public void WhenActivityCreatedViewIsInited() {
        presenter.activityCreate();
        assertTrue(view.isViewInited);
    }

    @Test
    public void WhenActivityCreatedListenerIsInited() {
        presenter.activityCreate();
        assertTrue(view.isListenerInited);
    }

    @Test
    public void WhenActivityCreatedDeadlineNotifyCheckIsCheckedIfEnabled() {
        settingStorage.setDeadlineNotify(true);
        presenter.activityCreate();
        assertTrue(view.isDeadLineNotifyChecked);
    }

    @Test
    public void WhenActivityCreatedDeadlineNotifyCheckIsNotCheckedIfDisabled() {
        settingStorage.setDeadlineNotify(false);
        presenter.activityCreate();
        assertFalse(view.isDeadLineNotifyChecked);
    }

    @Test
    public void WhenLicenseIsClickedLicenseActivityStarts() {
        presenter.onLicenseClicked();
        assertTrue(view.isLicenseActivityOpened);
    }

    @Test
    public void WhenDeadlineNotifyIsEnabledEnabledIsStored() {
        presenter.onDeadlineNotificationSettingClicked(true);
        assertTrue(settingStorage.isDeadlineNotify());
    }

    @Test
    public void WhenDeadlineNotifyIsDisabledIsStored() {
        presenter.onDeadlineNotificationSettingClicked(false);
        assertFalse(settingStorage.isDeadlineNotify());
    }

    private class MockView implements SettingView {

        private boolean isViewInited = false;
        private boolean isListenerInited = false;
        private boolean isDeadLineNotifyChecked = false;
        private boolean isLicenseActivityOpened = false;

        @Override
        public void initView(boolean isDeadlineNotify) {
            this.isDeadLineNotifyChecked = isDeadlineNotify;
            isViewInited = true;
        }

        @Override
        public void initListener() {
            isListenerInited = true;
        }

        @Override
        public void goToLicenseActivity() {
            isLicenseActivityOpened = true;
        }
    }

    private class MockSettingStorage implements SettingStorage {

        private boolean isDeadlineNotify = false;

        @Override
        public boolean isDeadlineNotify() {
            return isDeadlineNotify;
        }

        @Override
        public void setDeadlineNotify(boolean isEnabled) {
            isDeadlineNotify = isEnabled;

        }
    }
}
