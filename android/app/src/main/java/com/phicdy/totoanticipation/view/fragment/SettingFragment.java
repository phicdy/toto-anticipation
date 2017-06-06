package com.phicdy.totoanticipation.view.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.SwitchPreference;

import com.phicdy.totoanticipation.R;
import com.phicdy.totoanticipation.model.scheduler.DeadlineAlarm;
import com.phicdy.totoanticipation.presenter.SettingPresenter;
import com.phicdy.totoanticipation.view.SettingView;
import com.phicdy.totoanticipation.view.activity.LicenseActivity;


public class SettingFragment extends PreferenceFragment implements SettingView {
    private SettingPresenter presenter;
    private SwitchPreference prefDeadlineNotification;
    private Preference prefLicense;

    private SharedPreferences.OnSharedPreferenceChangeListener listener;

    public SettingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.setting_fragment);
        presenter = new SettingPresenter(new DeadlineAlarm(getActivity()));
        presenter.setView(this);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.activityCreate();
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(listener);
    }

    @Override
    public void initView() {
        prefDeadlineNotification = (SwitchPreference)findPreference(getString(R.string.key_deadline_notification));
        prefLicense = findPreference(getString(R.string.key_license));
    }

    @Override
    public void initListener() {
        listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (key.equals(getString(R.string.key_deadline_notification))) {
                    presenter.onDeadlineNotificationSettingClicked(prefDeadlineNotification.isChecked());
                }
            }
        };

        prefLicense.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                presenter.onLicenseClicked();
                return true;
            }
        });
    }

    @Override
    public void goToLicenseActivity() {
        startActivity(new Intent(getActivity(), LicenseActivity.class));
    }
}
