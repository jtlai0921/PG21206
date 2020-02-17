package com.uppowerstudio.chapter4.clipboard;

import android.app.Activity;
import android.os.Bundle;
import android.text.ClipboardManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
/**
 * �ŶK�O�d��
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private EditText src;
	private EditText target;
	private Button copy;
	private Button paste;
	private ClipboardManager manager;
	
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

// ��oClipboardManager��H
manager=(ClipboardManager) getSystemService(CLIPBOARD_SERVICE);

src=(EditText) findViewById(R.id.src);
target=(EditText) findViewById(R.id.target);

// ���@�U���s�i��ƻs
copy=(Button) findViewById(R.id.copy);
copy.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				manager.setText(src.getText());
				paste.setEnabled(true);
			}
        });

// ���@�U���s�i��K�W
paste=(Button) findViewById(R.id.paste);
paste.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				target.setText(manager.getText());
			}
        });
    }
}
