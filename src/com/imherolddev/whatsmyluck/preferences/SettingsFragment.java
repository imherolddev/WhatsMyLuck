package com.imherolddev.whatsmyluck.preferences;

import com.imherolddev.whatsmyluck.R;

import android.os.Bundle;
import android.preference.PreferenceFragment;

/**
 * 
 * @author imherolddev
 *
 */
public class SettingsFragment extends PreferenceFragment {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		
		addPreferencesFromResource(R.xml.fragment_preference);
		
	}

}
