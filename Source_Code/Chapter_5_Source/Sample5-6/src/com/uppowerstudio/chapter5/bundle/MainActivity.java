package com.uppowerstudio.chapter5.bundle;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * Bundle��Ʀ@�νd��
 * @author UPPower Studio
 *
 */
public class MainActivity extends Activity {
   
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // ���J�G����main.xml
        setContentView(R.layout.main);
        
        // ��l��EditText
        final EditText editText=(EditText)findViewById(R.id.edit_share_data);
        
        // ��l��Button
        Button button=(Button)findViewById(R.id.button_display_share_data);
        
        // ���UButton�ƥ��ť��
        button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// ����ϥΪ̿�J�����
				CharSequence inputData=editText.getText();
				
				// �s�WBundle��H
				Bundle bundle=new Bundle();
				// �O�s��ƨ�Bundle����
				bundle.putCharSequence("shared_data", inputData);
				
				// �غc�s��Intent����
				Intent intent=new Intent(MainActivity.this, DisplayDataActivity.class);
				// �O�sBundle�����Intent�����T��
				intent.putExtras(bundle);
				// �Ұʷs��Activity
				startActivity(intent);
			}
        });
        
    }
}
