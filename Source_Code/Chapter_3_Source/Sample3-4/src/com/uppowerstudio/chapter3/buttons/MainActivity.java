package com.uppowerstudio.chapter3.buttons;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * 按鈕範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private TextView txt;
	private Button btn;
	private ImageButton imageBtn;
	private ToggleButton toggleBtn;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		txt=(TextView) findViewById(R.id.txt);
		btn=(Button) findViewById(R.id.btn);
		// 新增單次點擊事件監聽器
		OnClickListener btnClickListener=new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 單次點擊時更新TextView
				txt.setText(R.string.display_btn_clicked);
			}
		};
		// 為按鈕註冊監聽器
		btn.setOnClickListener(btnClickListener);

		imageBtn=(ImageButton) findViewById(R.id.img_btn);
		OnClickListener imgBtnClickListener=new OnClickListener() {
			@Override
			public void onClick(View v) {
				txt.setText(R.string.display_imgbtn_clicked);
			}
		};
		imageBtn.setOnClickListener(imgBtnClickListener);
		// 以內部類別的形式為圖片按鈕註冊長按事件監聽器
		imageBtn.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
				// 長按時更新TextView
				txt.setText(R.string.display_btn_long_clicked);
				return false;
			}
		});

		toggleBtn=(ToggleButton) findViewById(R.id.toggle_btn);
		OnClickListener toggleBtnClickListener=new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 根據開關按鈕的狀態更新其狀態
				if (toggleBtn.isChecked()) {
					toggleBtn.setChecked(true);
				} else {
					toggleBtn.setChecked(false);
				}
			}
		};
		toggleBtn.setOnClickListener(toggleBtnClickListener);
	}
}
