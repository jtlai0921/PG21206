package com.uppowerstudio.chapter4.contact;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;
/**
 * �q�T���d��
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private String result="";
	
    @Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        TextView contacts=(TextView) findViewById(R.id.contacts);
        // ���ContactsContract.CommonDataKinds��H
        ContentResolver resolver=getContentResolver();
        // ���w�ݭn��������e�A�o��������O�q�T������ܪ��W�r�M�q�ܸ��X
        String[] columns=new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER};
        // ����s�����w���e�]�W�r�M���X�^��Cursor����
        Cursor cursor=resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, columns, null, null, null);
        // ���Ӥ@�w�榡Ū���C�@���q�T���O��
while(cursor.moveToNext()){
	result += "\n"+cursor.getString(0)+": "+cursor.getString(1) +"\n";
        }
        // ����Cursor�귽
        cursor.close();
        contacts.setText(result);
    }
}
