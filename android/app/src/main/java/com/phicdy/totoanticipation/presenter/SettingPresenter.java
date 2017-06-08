package com.phicdy.totoanticipation.presenter;

import android.support.annotation.NonNull;

import com.phicdy.totoanticipation.model.scheduler.DeadlineAlarm;
import com.phicdy.totoanticipation.model.storage.SettingStorage;
import com.phicdy.totoanticipation.view.SettingView;

import java.util.Date;


public class SettingPresenter implements Presenter {
    private final DeadlineAlarm alarm;
    private SettingView view;
    private Date deadline;
    private SettingStorage storage;

    public SettingPresenter(@NonNull DeadlineAlarm alarm, @NonNull Date deadline,
                            @NonNull SettingStorage storage) {
        this.alarm = alarm;
        this.deadline = deadline;
        this.storage = storage;
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
        storage.setDeadlineNotify(checked);
        if (checked) {
            if (deadline.getTime() == 0) return;
            alarm.set5hoursBefore(deadline);
        } else {
            alarm.cancel();
        }
    }
}
