package com.phicdy.totoanticipation.legacy.view.fragment

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.google.android.material.snackbar.Snackbar
import com.phicdy.totoanticipation.legacy.R
import com.phicdy.totoanticipation.legacy.model.storage.SettingStorageImpl
import com.phicdy.totoanticipation.legacy.presenter.SettingPresenter
import com.phicdy.totoanticipation.legacy.view.SettingView
import com.phicdy.totoanticipation.scheduler.DeadlineAlarm
import com.phicdy.totoanticipation.storage.GameListStorage
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject


class SettingFragment : PreferenceFragmentCompat(), SettingView {
    private lateinit var presenter: SettingPresenter
    private lateinit var prefDeadlineNotification: SwitchPreference
    private lateinit var prefLicense: Preference
    private lateinit var prefPrivacyPolicy: Preference
    private lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener

    @Inject
    lateinit var storage: GameListStorage

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.setting_fragment, rootKey)
        context?.let {
            val settingStorage = SettingStorageImpl(it)
            presenter = SettingPresenter(DeadlineAlarm(it),
                    storage.totoDeadline(), settingStorage)
            presenter.setView(this)
            presenter.activityCreate()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
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
        prefDeadlineNotification = findPreference(getString(R.string.key_deadline_notification))
                ?: throw IllegalStateException("")
        prefDeadlineNotification.isChecked = isDeadlineNotify
        prefLicense = findPreference(getString(R.string.key_license))
                ?: throw IllegalStateException("")
        prefPrivacyPolicy = findPreference(getString(R.string.key_privacy_policy))
                ?: throw IllegalStateException("")
    }

    override fun initListener() {
        listener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
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
        findNavController().navigate(R.id.action_settingFragment_to_licenseFragment)
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
