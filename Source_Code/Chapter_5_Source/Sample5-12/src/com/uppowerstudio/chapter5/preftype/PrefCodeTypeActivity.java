package com.uppowerstudio.chapter5.preftype;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.preference.RingtonePreference;

public class PrefCodeTypeActivity extends PreferenceActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// 呼叫generatePreferences方法生成需要顯示的Preference，並呼叫setPreferencesScreen		// 方法進行顯示
		setPreferenceScreen(generatePreferences());

		// 設置依賴性
		getPreferenceManager().findPreference("pref_ringtone_key")
				.setDependency("pref_use_ringtone");

	}

	/**
	 * 生成用於顯示的Preference
	 * @return
	 */
	private PreferenceScreen generatePreferences() {
		// 新增PreferenceScreen物件，等同於XML中的<PreferenceScreen>標籤
		PreferenceScreen prefScreen=getPreferenceManager()
				.createPreferenceScreen(this);
		
		// 新增“一般設置”項的PreferenceCategory對象
		PreferenceCategory normalCategory=new PreferenceCategory(this);
		// 設置標題
		normalCategory.setTitle(R.string.normal);
		// 將新增的PreferenceCategory對象添加到PreferenceScreen中
		prefScreen.addPreference(normalCategory);

		// 新增核取方塊類型的Preference
		CheckBoxPreference checkboxPref=new CheckBoxPreference(this);
		// 設置Key
		checkboxPref.setKey("pref_checkbox_key");
		// 設置標題
		checkboxPref.setTitle(R.string.pref_checkbox_title);
		// 設置處於選擇狀態時顯示的文字
		checkboxPref.setSummaryOn(R.string.pref_checkbox_on);
		// 設置處於未選擇狀態時顯示的文字
		checkboxPref.setSummaryOff(R.string.pref_checkbox_off);
		// 設置預設值為選中狀態
		checkboxPref.setDefaultValue(true);
		// 將新增好的物件添加到分類物件中
		normalCategory.addPreference(checkboxPref);

		// 新增列表類型的Preference
		ListPreference listPref=new ListPreference(this);
		listPref.setKey("pref_list_key");
		listPref.setTitle(R.string.pref_list_title);
		listPref.setSummary(R.string.pref_list_summary);
		listPref.setDialogTitle(R.string.pref_list_dialog_title);
		// 設置用於顯示的值，從preference_array.xml中讀取
		listPref.setEntries(R.array.pref_list_label);
		// 設置顯示資料的實際值
		listPref.setEntryValues(R.array.pref_list_value);
		listPref.setDefaultValue("1");
		normalCategory.addPreference(listPref);

		// 新增文字方塊類型的Preference
		EditTextPreference editPref=new EditTextPreference(this);
		editPref.setKey("pref_edit_key");
		editPref.setTitle(R.string.pref_edit_title);
		editPref.setSummary(R.string.pref_edit_summary);
		editPref.setDialogTitle(R.string.pref_edit_dialog_title);
		editPref.setDefaultValue("");

		// 新增“進階設置”項的PreferenceCategory物件
		PreferenceCategory advCategory=new PreferenceCategory(this);
		advCategory.setTitle(R.string.advanced);
		prefScreen.addPreference(advCategory);

		// 新增核取方塊類型的Preference，用於示範依賴關係
		CheckBoxPreference chkEnableRingtonePref=new CheckBoxPreference(this);
		chkEnableRingtonePref.setKey("pref_use_ringtone");
		chkEnableRingtonePref.setTitle(R.string.pref_use_ringtone_title);
		chkEnableRingtonePref.setSummaryOn(R.string.pref_use_ringtone_on);
		chkEnableRingtonePref.setSummaryOff(R.string.pref_use_ringtone_off);
		chkEnableRingtonePref.setDefaultValue(true);
		advCategory.addPreference(chkEnableRingtonePref);
		
		// 新增選擇鈴聲類型的Preference
		RingtonePreference ringtonePref=new RingtonePreference(this);
		ringtonePref.setKey("pref_ringtone_key");
		ringtonePref.setTitle(R.string.pref_ringtone_title);
		advCategory.addPreference(ringtonePref);
		
		// 返回新增完成的PreferenceScreen物件
		return prefScreen;
	}
}
