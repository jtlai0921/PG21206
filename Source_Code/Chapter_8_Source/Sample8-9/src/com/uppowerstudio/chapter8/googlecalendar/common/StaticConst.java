package com.uppowerstudio.chapter8.googlecalendar.common;

/**
 * �R�A����
 * @author UPPower Studio
 *
 */
public interface StaticConst {
	static final String TAG="Sample8-8";

	// �w�q���Τ���Action
	static final String ACTION_LIST_CALENDAR="action_list_calendar";
	static final String ACTION_LIST_CALENDAR_EVENTS="action_list_calendar_events";

	// Google���A�Ȯڦ�}
	static final String METAFEED_URL_BASE="https://www.google.com/calendar/feeds";

	// �@�ά�����Τ��q�{���
	static final String DEFAULT_FEED_URL_SUFFIX="default";

	// �����X�Ω�K�[��Τ�Feed URL�a�}����A�@�ά�����Τ�Ҧ������
	static final String ALLCALENDARS_FEED_URL_SUFFIX="allcalendars";

	// �����X�Ω�K�[��Τ�Feed URL�a�}����A�@�ά�����Τ�Ҿ֦������
	static final String OWNCALENDARS_FEED_URL_SUFFIX="owncalendars";
	
	// �w�q��䬡�ʨƥ�Feed������Project�ݩʭ�
	static final String PROJECT_FULL_SUFFIX="full";

	// ����������X�M�鸨���ID
	static final String CHENGDU_SUNRISE_CALENDAR_ID="i_125.69.29.149#sunrise@group.v.calendar.google.com";

	// �w�q�����s�X�榡
	static final String ENCODING="UTF-8";

	// �w�qBundle Keys
	static final String BUNDLE_KEY_USERNAME="USER_NAME";
	static final String BUNDLE_KEY_PASSWORD="PASSWORD";

	// �w�q�Ω���v�ɨϥΪ����A�ȵ{���X
	static final String AUTH_SERVICE_TYPE="cl";

	// �w�q�\���ϥέ�
	static final int CONTEXT_EDIT_CALENDAR=0;
	static final int CONTEXT_DELETE_CALENDAR=1;
}
