package com.phicdy.totoanticipation.presenter;

import android.support.annotation.NonNull;

import com.phicdy.totoanticipation.model.scheduler.DeadlineAlarm;
import com.phicdy.totoanticipation.view.SettingView;

import java.util.Date;


public class SettingPresenter implements Presenter {
    private final DeadlineAlarm alarm;
    private SettingView view;

    public SettingPresenter(@NonNull DeadlineAlarm alarm) {
        this.alarm = alarm;
    }

    public void setView(SettingView view) {
        this.view = view;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onResume() {
    }

    @Override
    public void onPause() {
    }

    public void activityCreate() {
        view.initView();
        view.initListener();
    }

    public void onLicenseClicked() {
        view.goToLicenseActivity();
    }

    public void onDeadlineNotificationSettingClicked(boolean checked) {
        if (checked) {
            // TODO set correct date
            alarm.set(new Date());
        } else {
            alarm.cancel();
        }
    }
}
