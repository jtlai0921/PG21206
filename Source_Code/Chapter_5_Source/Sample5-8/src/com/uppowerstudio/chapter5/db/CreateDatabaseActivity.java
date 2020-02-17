package com.uppowerstudio.chapter5.db;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class CreateDatabaseActivity extends Activity {
    // �w�q��Ʈw�W
	public static final String DATABASE_NAME="db_1";
	
	// �ŧiSQLiteDatabase��H
	private SQLiteDatabase sqliteDatabase;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // ��l�Ʊ��
        Button createButton=(Button)findViewById(R.id.button_create_db);
        Button closeButton=(Button)findViewById(R.id.button_close_db);
        Button deleteButton=(Button)findViewById(R.id.button_delete_db);
        
        // ���U�s�W��Ʈw���s�ƥ��ť��
        createButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// �I�s�R�A��k�s�W��Ʈw
				sqliteDatabase=openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
				
				// ��ܴ��ܮ����A�p�GsqliteDatabase���󤣬�null�A�h����s�W�Υ��}��Ʈw���\
				if (sqliteDatabase != null){
					Toast.makeText(getBaseContext(), getString(R.string.msg_create_success)+DATABASE_NAME, Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(getBaseContext(), getString(R.string.msg_create_failure)+DATABASE_NAME, Toast.LENGTH_LONG).show();
				}
			}
        });
        
        // ���U������Ʈw���s�ƥ��ť��
        closeButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// �I�sSQLiteDatabase����close��k�����w���}����Ʈw
				sqliteDatabase.close();
				
				// ��ܴ��ܮ���
				Toast.makeText(getBaseContext(),getString(R.string.msg_close_success)+DATABASE_NAME, Toast.LENGTH_LONG).show();
			}
        });
        
        // ���U�R����Ʈw���s�ƥ��ť��
        deleteButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// �I�sContext������kdeleteDatabase�R����Ʈw
				boolean isDeleted=deleteDatabase(DATABASE_NAME);
				
				// ��ܴ��ܮ����A�R�����\���^true�A�_�h��false
				if (isDeleted){
					Toast.makeText(getBaseContext(), 
							getString(R.string.msg_delete_success)+DATABASE_NAME, 
							Toast.LENGTH_LONG).show();
				}else{
					Toast.makeText(getBaseContext(),getString(R.string. msg_delete_failure), Toast.LENGTH_LONG).show();
				}
			}
        });
    }
}
