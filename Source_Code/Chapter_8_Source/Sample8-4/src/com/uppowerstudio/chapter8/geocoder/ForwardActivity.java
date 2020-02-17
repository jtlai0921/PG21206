package com.uppowerstudio.chapter8.geocoder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * �q�L��m�M��g�n�פ����{���X
 * 
 * @author UPPower Studio
 * 
 */
public class ForwardActivity extends Activity {

	// �ŧi�������
	private EditText inputLocation;
	private Button buttonForwardSearch;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forward);

		// ��l�Ƥ������
		inputLocation=(EditText) findViewById(R.id.txt_location);
		buttonForwardSearch=(Button) findViewById(R.id.button_forward_search);

		// ���U���s�I���ƥ�
		buttonForwardSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (validate()) {
					Intent intent=new Intent();
					// �]�m�^�Ǹ������
					intent.putExtra("return_type", "forward");
					// �]�m�����m���
					intent.putExtra("input_location", inputLocation.getText()
							.toString().trim());
					// �]�m��^�ȵ{���X
					setResult(RESULT_OK, intent);
					finish();
				}
			}
		});
	}

	/**
	 * ���Ҧ�}��J���ର��
	 * 
	 * @return
	 */
	private boolean validate() {
		boolean valid=true;

		String location=inputLocation.getText().toString().trim();

		if (location.length()==0) {
			inputLocation
					.setError(getString(R.string.common_error_msg_required));
			valid=false;
		}

		return valid;
	}
}
