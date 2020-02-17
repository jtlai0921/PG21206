package com.uppowerstudio.chapter7.recorder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.uppowerstudio.chapter7.recorder.utils.CommonUtils;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

/**
 * ²���������D�����{���X
 * @author UPPower Studio
 *
 */
public class MainActivity extends ListActivity {
	
	// �w�q�����ɦs���m
	private static final String FOLDER_PATH="/sdcard/Sample7-2/";

	// �w�qSD�d�L�Ĺ�ܤ��ID
	private static final int DIALOG_SDCARD_UNAVAILABLE=1;

	// �ŧi��������ܼ�
	private TextView txtStatus;
	private ImageButton buttonRecord;
	private ImageButton buttonStop;

	// �w�qMediaRecorder�ܼ�
	private MediaRecorder mediaRecorder;

	// �w�q�{���x�s��
	private File audioRecFile;

	// �w�q�Ω�M����ܪ��ܼ�
	private List<File> recordList=new ArrayList<File>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// ��l�Ƥ������
		txtStatus=(TextView) findViewById(R.id.txt_status);
		buttonRecord=(ImageButton) findViewById(R.id.button_start_record);
		buttonStop=(ImageButton) findViewById(R.id.button_stop_record);

		// ���U���s�ƥ��ť��
		registerButtonHandler();
		
		// ��R�C��
		renderListView();
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		// �pSD�d�L�Į���ܹ�ܤ��
		case DIALOG_SDCARD_UNAVAILABLE:
			return CommonUtils.genDialog(MainActivity.this,
					getString(R.string.app_name),
					getString(R.string.error_msg_sdcard_unavailable),
					android.R.drawable.ic_dialog_alert, R.string.button_ok, 0,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					}, null);
		default:
			return null;
		}
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// �I�s�t��Activity���������
		Intent intent=new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(recordList.get(position)), "audio");
		startActivity(intent);
	}

	/**
	 * ���U���s�ƥ��ť��
	 */
	private void registerButtonHandler() {
		buttonRecord.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �}�l�����ާ@
				startRecording();
			}
		});

		buttonStop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �������
				stopRecording();
			}
		});
	}

	/**
	 * �}�l����
	 */
	private void startRecording() {
		// �ˬdSD�d�O�_���ĨæP�ɳB��i�g���A
		if (CommonUtils.isSdCardAvailable() && !CommonUtils.isSdCardReadOnly()) {
			// �P�_�x�s���T����Ƨ��O�_�s�b�A�p�G���s�b�h�i��s�W�ާ@
			File audioRecPath=new File(FOLDER_PATH);
			if (!audioRecPath.exists()) {
				audioRecPath.mkdir();
			}

			try {
				// �p�G�s���ɸ��|���T�BSD�d�Ŷ����ġA�h�i������ާ@
				if (audioRecPath != null
						&& CommonUtils.isSdCardStorageAvailable()) {
					
					// �s�W�Ȧs�ɮסA�HRecord_�}�Y
					audioRecFile=File.createTempFile("Record_", ".amr",
							audioRecPath);

					// �غcMediaRecorder��H
					mediaRecorder=new MediaRecorder();

					// �]�m�ļ˪����T�������J��
					mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
					
					// �]�m��X�ɮ׮榡
					mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.
					DEFAULT);
					
					// �]�m���T���s�X�覡
					mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.
					DEFAULT);
					
					// �]�m��X��
					mediaRecorder.setOutputFile(audioRecFile.getAbsolutePath());
					
					// �ǳƿ����ާ@
					mediaRecorder.prepare();
					
					// �}�l����
					mediaRecorder.start();

					// �]�m���A���
					txtStatus.setText(getString(R.string.prefix_status)
							+getString(R.string.msg_recording));
					
					// �]�m�������s���i��
					buttonRecord.setEnabled(false);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			// ��ܿ��~��ܤ��
			showDialog(DIALOG_SDCARD_UNAVAILABLE);
		}
	}

	/**
	 * �������
	 */
	private void stopRecording() {
		// �p�G���b����
		if (audioRecFile !=null) {
			// ��������ާ@
			mediaRecorder.stop();
			
			// ����MediaRecorder��H�Ҧ��θ귽
			mediaRecorder.release();

			// �]�m���A���
			txtStatus.setText(getString(R.string.prefix_status)
					+getString(R.string.msg_stopped));
			
			// �]�m�������s�i��
			buttonRecord.setEnabled(true);

			// �N��e�������K�[��C��
			recordList.add(audioRecFile);

			// �غc�����ɲM���ưt����
			RecordArrayAdapter adapter=new RecordArrayAdapter(
					MainActivity.this, R.layout.item, recordList);
			setListAdapter(adapter);
		}
	}

	/**
	 * ��ܿ����ɲM��
	 */
	private void renderListView() {
		// �غc�x�s�ɦ�m
		File path=new File(FOLDER_PATH);
		
		// Ū�����C��
		if (path != null&& path.listFiles() !=null
				&& path.listFiles().length> 0) {
			for (File file : path.listFiles()) {
				recordList.add(file);
			}
		}

		// �غc�����ɲM���ưt����
		RecordArrayAdapter adapter=new RecordArrayAdapter(MainActivity.this,
				R.layout.item, recordList);
		setListAdapter(adapter);
	}
}
