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

		// �I�sgeneratePreferences��k�ͦ��ݭn��ܪ�Preference�A�éI�ssetPreferencesScreen		// ��k�i�����
		setPreferenceScreen(generatePreferences());

		// �]�m�̿��
		getPreferenceManager().findPreference("pref_ringtone_key")
				.setDependency("pref_use_ringtone");

	}

	/**
	 * �ͦ��Ω���ܪ�Preference
	 * @return
	 */
	private PreferenceScreen generatePreferences() {
		// �s�WPreferenceScreen����A���P��XML����<PreferenceScreen>����
		PreferenceScreen prefScreen=getPreferenceManager()
				.createPreferenceScreen(this);
		
		// �s�W���@��]�m������PreferenceCategory��H
		PreferenceCategory normalCategory=new PreferenceCategory(this);
		// �]�m���D
		normalCategory.setTitle(R.string.normal);
		// �N�s�W��PreferenceCategory��H�K�[��PreferenceScreen��
		prefScreen.addPreference(normalCategory);

		// �s�W�֨����������Preference
		CheckBoxPreference checkboxPref=new CheckBoxPreference(this);
		// �]�mKey
		checkboxPref.setKey("pref_checkbox_key");
		// �]�m���D
		checkboxPref.setTitle(R.string.pref_checkbox_title);
		// �]�m�B���ܪ��A����ܪ���r
		checkboxPref.setSummaryOn(R.string.pref_checkbox_on);
		// �]�m�B�󥼿�ܪ��A����ܪ���r
		checkboxPref.setSummaryOff(R.string.pref_checkbox_off);
		// �]�m�w�]�Ȭ��襤���A
		checkboxPref.setDefaultValue(true);
		// �N�s�W�n������K�[���������
		normalCategory.addPreference(checkboxPref);

		// �s�W�C��������Preference
		ListPreference listPref=new ListPreference(this);
		listPref.setKey("pref_list_key");
		listPref.setTitle(R.string.pref_list_title);
		listPref.setSummary(R.string.pref_list_summary);
		listPref.setDialogTitle(R.string.pref_list_dialog_title);
		// �]�m�Ω���ܪ��ȡA�qpreference_array.xml��Ū��
		listPref.setEntries(R.array.pref_list_label);
		// �]�m��ܸ�ƪ���ڭ�
		listPref.setEntryValues(R.array.pref_list_value);
		listPref.setDefaultValue("1");
		normalCategory.addPreference(listPref);

		// �s�W��r���������Preference
		EditTextPreference editPref=new EditTextPreference(this);
		editPref.setKey("pref_edit_key");
		editPref.setTitle(R.string.pref_edit_title);
		editPref.setSummary(R.string.pref_edit_summary);
		editPref.setDialogTitle(R.string.pref_edit_dialog_title);
		editPref.setDefaultValue("");

		// �s�W���i���]�m������PreferenceCategory����
		PreferenceCategory advCategory=new PreferenceCategory(this);
		advCategory.setTitle(R.string.advanced);
		prefScreen.addPreference(advCategory);

		// �s�W�֨����������Preference�A�Ω�ܽd�̿����Y
		CheckBoxPreference chkEnableRingtonePref=new CheckBoxPreference(this);
		chkEnableRingtonePref.setKey("pref_use_ringtone");
		chkEnableRingtonePref.setTitle(R.string.pref_use_ringtone_title);
		chkEnableRingtonePref.setSummaryOn(R.string.pref_use_ringtone_on);
		chkEnableRingtonePref.setSummaryOff(R.string.pref_use_ringtone_off);
		chkEnableRingtonePref.setDefaultValue(true);
		advCategory.addPreference(chkEnableRingtonePref);
		
		// �s�W��ܹa�n������Preference
		RingtonePreference ringtonePref=new RingtonePreference(this);
		ringtonePref.setKey("pref_ringtone_key");
		ringtonePref.setTitle(R.string.pref_ringtone_title);
		advCategory.addPreference(ringtonePref);
		
		// ��^�s�W������PreferenceScreen����
		return prefScreen;
	}
}
