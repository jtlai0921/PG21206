package com.uppowerstudio.chapter7.graphics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	// �ŧi��������ܼ�
	private Button buttonGeometry;
	private Button buttonText;
	private Button buttonBitmap;
	private Button buttonMatrixZoom;
	private Button buttonShader;
	
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

// ��l�Ƥ�������ܼ�
buttonGeometry=(Button)findViewById(R.id.button_geometry);
buttonText=(Button)findViewById(R.id.button_text);
buttonBitmap=(Button)findViewById(R.id.button_bitmap);
buttonMatrixZoom=(Button)findViewById(R.id.button_matrix_zoom);
buttonShader=(Button)findViewById(R.id.button_shader);

// ���U���s�ƥ��ť��
        registerButtonHandler();
    }

/**
 * ���U���s�ƥ��ť��
 */
private void registerButtonHandler(){
	// ø�s�X��ϧ�
	buttonGeometry.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent=new Intent("action_geometry");
				startActivity(intent);				
			}
	});
	
	// ø�s�r��
	buttonText.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent=new Intent("action_text");
				startActivity(intent);	
			}
	});
	
	// ø�s�Ϲ�
	buttonBitmap.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent=new Intent("action_bitmap");
				startActivity(intent);	
			}
	});
	
	// �Ϲ�������P�Y��
	buttonMatrixZoom.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent=new Intent("action_matrix_zoom");
				startActivity(intent);	
			}
	});
	
	// �Ϲ���V
	buttonShader.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent=new Intent("action_shader");
				startActivity(intent);	
			}
	});
    }
}
