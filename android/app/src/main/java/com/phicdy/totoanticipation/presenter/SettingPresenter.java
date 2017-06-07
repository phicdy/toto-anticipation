package com.phicdy.totoanticipation.presenter;

import android.support.annotation.NonNull;

import com.phicdy.totoanticipation.model.scheduler.DeadlineAlarm;
import com.phicdy.totoanticipation.model.storage.GameListStorage;
import com.phicdy.totoanticipation.view.SettingView;

import java.util.Calendar;
import java.util.Date;


public class SettingPresenter implements Presenter {
    private final DeadlineAlarm alarm;
    private final GameListStorage storage;
    private SettingView view;

    public SettingPresenter(@NonNull DeadlineAlarm alarm, @NonNull GameListStorage storage) {
        this.alarm = alarm;
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
        if (checked) {
            Date deadline = storage.totoDeadline();
            if (deadline.getTime() == 0) return;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(deadline);
            calendar.add(Calendar.HOUR_OF_DAY, -5);
            alarm.set(calendar.getTime());
        } else {
            alarm.cancel();
        }
    }
}
