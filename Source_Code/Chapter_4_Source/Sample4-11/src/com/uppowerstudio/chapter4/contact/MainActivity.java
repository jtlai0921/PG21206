package com.uppowerstudio.chapter4.contact;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;
/**
 * 通訊錄範例
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
        // 獲取ContactsContract.CommonDataKinds對象
        ContentResolver resolver=getContentResolver();
        // 指定需要獲取的內容，這裡獲取的是通訊錄中顯示的名字和電話號碼
        String[] columns=new String[] {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER};
        // 獲取存有指定內容（名字和號碼）的Cursor物件
        Cursor cursor=resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, columns, null, null, null);
        // 按照一定格式讀取每一條通訊錄記錄
while(cursor.moveToNext()){
	result += "\n"+cursor.getString(0)+": "+cursor.getString(1) +"\n";
        }
        // 釋放Cursor資源
        cursor.close();
        contacts.setText(result);
    }
}
