package com.uppowerstudio.chapter8.geocoder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * �q�L�g�n�״M���m�{���X
 * @author UPPower Studio
 *
 */
public class ReverseActivity extends Activity {
	
	// �ˬd�g�n�צX�k�ʪ��W�h�B�⦡
	private static final String LNG_LAT_REGEX="-?\\d+(\\.\\d+)?";

	// �ŧi��������ܼ�
	private EditText longitude;
	private EditText latitude;
	private Button buttonReverseSearch;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reverse);

		// ��l�Ƥ������
		longitude=(EditText) findViewById(R.id.txt_longitude);
		latitude=(EditText) findViewById(R.id.txt_latitude);
		buttonReverseSearch=(Button) findViewById(R.id.button_reverse_search);

		// ���U���s�I���ƥ�
		buttonReverseSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				forwardSearch();
			}
		});
	}

	/**
	 * �^�Ǽƾ�
	 */
	private void forwardSearch() {
		if (validate()) {
			Intent intent=new Intent();
			// �]�m�^�Ǹ������
			intent.putExtra("return_type", "reverse");
			// �]�m�g�׼ƾ�
			intent.putExtra("longitude", longitude.getText().toString().trim());
			// �]�m�n�׼ƾ�
			intent.putExtra("latitude", latitude.getText().toString().trim());
			// �]�m��^�ȵ{���X
			setResult(RESULT_OK, intent);
			finish();
		}
	}

	/**
	 * ���ҿ�J�g�n�ת��X�k��
	 * @return
	 */
	private boolean validate() {
		boolean valid=true;
		
		// ����ƭ�
		String strLng=longitude.getText().toString().trim();
		String strLat=latitude.getText().toString().trim();
		
		// ���Ҥ��ର��
		if (strLng.length() == 0) {
			longitude.setError(getString(R.string.common_error_msg_required));
			valid=false;
		} else if (strLng.matches(LNG_LAT_REGEX)) { // ���Ҹ�ƪ����ĩ�
			if (Double.parseDouble(strLng) < -180
					|| Double.parseDouble(strLng) > 180) {
				longitude
						.setError(getString(R.string.common_error_msg_wrong_longtitude));
				valid=false;
			}
		} else {
			longitude
					.setError(getString(R.string.common_error_msg_wrong_longtitude));
			valid=false;
		}

		if (strLat.length() == 0) {
			latitude.setError(getString(R.string.common_error_msg_required));
			valid=false;
		} else if (strLat.matches(LNG_LAT_REGEX)) {
			if (Double.parseDouble(strLat) < -90
					|| Double.parseDouble(strLat) > 90) {
				latitude.setError(getString(R.string.common_error_msg_wrong_latitude));
				valid=false;
			}
		} else {
			latitude.setError(getString(R.string.common_error_msg_wrong_latitude));
			valid=false;
		}

		return valid;
	}
}
