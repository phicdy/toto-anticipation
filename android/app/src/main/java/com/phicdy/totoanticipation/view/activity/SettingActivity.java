package com.phicdy.totoanticipation.view.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.phicdy.totoanticipation.view.fragment.SettingFragment;


public class SettingActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
		final SettingFragment fragment = new SettingFragment();
		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, fragment)
				.commit();
	}
}
