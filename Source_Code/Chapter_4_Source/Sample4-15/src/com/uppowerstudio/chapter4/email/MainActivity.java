package com.uppowerstudio.chapter4.email;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * �o�eE-mail�d��
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private Button sendBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		sendBtn=(Button) findViewById(R.id.send);
		// ���U�o�e���s���@�U�ƥ�
		sendBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent=new Intent();

				intent.setAction(Intent.ACTION_SEND);
				// �]�m���e���O�����¤�r
				intent.setType("plain/text");
				// �]�m�o�e��}���r��}�C
				String[] toAddr={ "uppowerstudio@163.com",
						"uppowerstudio@163.com" };
				// �]�m�۰e��}���r��}�C
				String[] ccAddr={ "uppowerstudio@163.com" };
				// �]�mE-mail�D�D
				intent.putExtra(Intent.EXTRA_SUBJECT, "Email Demo");
				// �]�mE-mail�o�e��}
				intent.putExtra(Intent.EXTRA_EMAIL, toAddr);
				// �]�mE-mail�۰e��}
				intent.putExtra(Intent.EXTRA_CC, ccAddr);
				// �]�mE-mail���e
				intent.putExtra(Intent.EXTRA_TEXT, "This is email send demo");
				// �o�eE-mail
				startActivity(Intent.createChooser(intent, null));
			}
		});
	}
}
