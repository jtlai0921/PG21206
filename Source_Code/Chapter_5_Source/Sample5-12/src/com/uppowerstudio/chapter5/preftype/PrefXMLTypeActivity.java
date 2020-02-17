package com.uppowerstudio.chapter5.preftype;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class PrefXMLTypeActivity extends PreferenceActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState); 
		
		// 呼叫方法載入以XML方式定義的Preferences
		addPreferencesFromResource(R.xml.pref_type_sample); 		
	}
}
