package com.phicdy.totoanticipation.view.fragment

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.preference.Preference
import android.preference.PreferenceFragment
import android.preference.SwitchPreference
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.phicdy.totoanticipation.R
import com.phicdy.totoanticipation.legacy.view.SettingView
import com.phicdy.totoanticipation.model.scheduler.DeadlineAlarm
import com.phicdy.totoanticipation.model.storage.GameListStorageImpl
import com.phicdy.totoanticipation.model.storage.SettingStorageImpl
import com.phicdy.totoanticipation.presenter.SettingPresenter
import com.phicdy.totoanticipation.view.activity.LicenseActivity


class SettingFragment : PreferenceFragment(), SettingView {
    private lateinit var presenter: SettingPresenter
    private lateinit var prefDeadlineNotification: SwitchPreference
    private lateinit var prefLicense: Preference
    private lateinit var prefPrivacyPolicy: Preference
    private lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.setting_fragment)
        val storage = GameListStorageImpl(activity)
        val settingStorage = SettingStorageImpl(activity)
        presenter = SettingPresenter(DeadlineAlarm(activity),
                storage.totoDeadline(), settingStorage)
        presenter.setView(this)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        presenter.activityCreate()
    }

    override fun onResume() {
        super.onResume()
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onPause() {
        super.onPause()
        preferenceScreen.sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener)
    }

    override fun initView(isDeadlineNotify: Boolean) {
        prefDeadlineNotification = findPreference(getString(R.string.key_deadline_notification)) as SwitchPreference
        prefDeadlineNotification.isChecked = isDeadlineNotify
        prefLicense = findPreference(getString(R.string.key_license))
        prefPrivacyPolicy = findPreference(getString(R.string.key_privacy_policy))
    }

    override fun initListener() {
        listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
            if (key == getString(R.string.key_deadline_notification)) {
                presenter.onDeadlineNotificationSettingClicked(prefDeadlineNotification.isChecked)
            }
        }

        prefLicense.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            presenter.onLicenseClicked()
            true
        }
        prefPrivacyPolicy.onPreferenceClickListener = Preference.OnPreferenceClickListener {
            presenter.onPrivacyPolicyClicked()
            true
        }
    }

    override fun goToLicenseActivity() {
        startActivity(Intent(activity, LicenseActivity::class.java))
    }

    override fun openPrivacyPolicy() {
        val uri = Uri.parse("https://toto-anticipation.netlify.com/privacy-policy.html")
        val intent = Intent(Intent.ACTION_VIEW, uri)
        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            val view = activity?.findViewById<View>(android.R.id.content) ?: return
            Snackbar.make(view, R.string.error_no_browser, Snackbar.LENGTH_SHORT).show()
        }
    }
}
