package com.uppowerstudio.chapter5.preftype;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PrefXMLTypeActivity extends PreferenceActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState); 
		
		// �I�s��k���J�HXML�覡�w�q��Preferences
		addPreferencesFromResource(R.xml.pref_type_sample); 		
	}
}
