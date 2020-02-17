package com.uppowerstudio.chapter5.dbhelper;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {
    // �w�q��l��Ʈw�������`�q
	private static final int INIT_DB_VERSION=1;
	// �w�q��s��Ʈw�������`�q
	private static final int UPGRADE_DB_VERSION=2;
	
	// �ŧiMyDbHelper�����ܼ�
	private MyDbHelper dbHelper;
	
	// �ŧi��Ʈw����SQLiteDatabase�ܼ�
	private SQLiteDatabase sqliteDatabase;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // ��l�ƫ��s
        Button createButton=(Button)findViewById(R.id.button_create_db);
        Button upgradeButton=(Button)findViewById(R.id.button_upgrade_db);
        
        // ���U�s�W��Ʈw���s�ƥ��ť��
        createButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// �s�W��Ʈw
				dbHelper=new MyDbHelper(getBaseContext(), INIT_DB_VERSION);
				// ��^�iŪ������Ʈw����
				sqliteDatabase=dbHelper.getWritableDatabase();
				// ��ܴ��ܮ���
				Toast.makeText(getBaseContext(), getString(R.string.msg_create_success), Toast.LENGTH_LONG).show();
			}
        });
        
        // ���U��s��Ʈw���c���s�ƥ��ť��
        upgradeButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// ��s��Ʈw���c�A�ǤJ���������P�s�W�ɪ����P�Y�iĲ�o��onUpgrade���I�s
				dbHelper=new MyDbHelper(getBaseContext(), UPGRADE_DB_VERSION);
				// ��^�iŪ������Ʈw����
				sqliteDatabase=dbHelper.getWritableDatabase();
				// ��ܴ��ܮ���
				Toast.makeText(getBaseContext(), getString(R.string.msg_upgrade_success), Toast.LENGTH_LONG).show();
			}
        });
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		// ������Ʈw
		dbHelper.close();
	}
    
    
}
