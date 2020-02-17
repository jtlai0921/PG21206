package com.uppowerstudio.chapter3.radio;

import android.app.Activity;
import android.os.Bundle;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

/**
 * 選項按鈕
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private TextView text;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 根據ID得到RadioGroup
		RadioGroup radioGroup=(RadioGroup) findViewById(R.id.agreeGroup);
		// 根據ID得到TextView
		text=(TextView) findViewById(R.id.display);

		// 註冊狀態改變監聽器，此監聽器在選項改變時被觸發
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			// group指狀態發生改變的RadioGroup
			// checkedId指改變後，被選取的RadioButton的ID
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if (R.id.radio_agree==checkedId) {
					// 在TextView尾部追加文字，追加之前恢復TextView的原始狀態
					text.setText(R.string.display_agree);
					text.append(getString(R.string.radio_agree));
				} else {
					text.setText(R.string.display_agree);
					text.append(getString(R.string.radio_disagree));
				}
			}
		});
	}
}
