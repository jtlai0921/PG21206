package com.uppowerstudio.chapter7.graphics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	// 宣告介面控制項變數
	private Button buttonGeometry;
	private Button buttonText;
	private Button buttonBitmap;
	private Button buttonMatrixZoom;
	private Button buttonShader;
	
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

// 初始化介面控制項變數
buttonGeometry=(Button)findViewById(R.id.button_geometry);
buttonText=(Button)findViewById(R.id.button_text);
buttonBitmap=(Button)findViewById(R.id.button_bitmap);
buttonMatrixZoom=(Button)findViewById(R.id.button_matrix_zoom);
buttonShader=(Button)findViewById(R.id.button_shader);

// 註冊按鈕事件監聽器
        registerButtonHandler();
    }

/**
 * 註冊按鈕事件監聽器
 */
private void registerButtonHandler(){
	// 繪製幾何圖形
	buttonGeometry.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent=new Intent("action_geometry");
				startActivity(intent);				
			}
	});
	
	// 繪製字串
	buttonText.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent=new Intent("action_text");
				startActivity(intent);	
			}
	});
	
	// 繪製圖像
	buttonBitmap.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent=new Intent("action_bitmap");
				startActivity(intent);	
			}
	});
	
	// 圖像的旋轉與縮放
	buttonMatrixZoom.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent=new Intent("action_matrix_zoom");
				startActivity(intent);	
			}
	});
	
	// 圖像渲染
	buttonShader.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				Intent intent=new Intent("action_shader");
				startActivity(intent);	
			}
	});
    }
}
