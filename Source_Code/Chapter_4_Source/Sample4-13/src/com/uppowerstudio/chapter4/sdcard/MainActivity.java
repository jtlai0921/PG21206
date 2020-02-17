package com.uppowerstudio.chapter4.sdcard;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * SD�d�d��
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private Button btn;
	private TextView displayText;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		btn=(Button) findViewById(R.id.btn);
		displayText=(TextView) findViewById(R.id.display);

		// ���SD�d���A
		String sdState=Environment.getExternalStorageState();
		// �p�GSD�d���˸��A�h�]�m���s�L�ġA�_�h�����s���U���@�U��ť��
		if (Environment.MEDIA_MOUNTED.equals(sdState)
				|| Environment.MEDIA_MOUNTED_READ_ONLY.equals(sdState)) {
			btn.setOnClickListener(new OnClickListener() {
				public void onClick(View v) {
					// ��oSD�d��File��H
					File file=Environment.getExternalStorageDirectory();
					// �ھ�SD�d�����|���StatFs����
					StatFs sf=new StatFs(file.getPath());
					// �����ư϶��j�p
					long blockSize=sf.getBlockSize();
					// ����`��ư϶���
					long totalBlocks=sf.getBlockCount();
					// �p���`�e�q�A�åHMB�����i�����
					long totalSize=blockSize * totalBlocks/(1024 * 1024);
					displayText.setText(totalSize+"MB");
				}
			});
		} else {
			btn.setEnabled(false);
		}
	}
}
