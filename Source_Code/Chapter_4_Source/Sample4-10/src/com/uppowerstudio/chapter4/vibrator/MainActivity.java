package com.uppowerstudio.chapter4.vibrator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
/**
 * 振動範例
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private Button shortButton;
	private Button longButton;
	private Button stopButton;
	private Vibrator vibrator;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // 獲取振動服務
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        
        shortButton = (Button) findViewById(R.id.short_vibrate);
        shortButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// long陣列裡的參數含義
				// 第一個參數表示等待100毫秒後開始振動
				// 第二個參數表示振動100毫秒後停止振動
				// 0表示按照此頻率迴圈振動
				vibrator.vibrate(new long[]{100,100}, 0);
			}
        });
        
        longButton = (Button) findViewById(R.id.long_vibrate);
        longButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// Long陣列裡的參數含義
				// 第一個參數表示等待1000毫秒後開始振動
				// 第二個參數表示振動3000毫秒後停止振動
				// 第三個參數表示等待1000毫秒後開始振動
				// 第四個參數表示振動3000毫秒後停止振動
				// -1表示此振動頻率不再迴圈

				vibrator.vibrate(new long[]{1000,3000,1000,3000}, -1);
			}
        });
        
        stopButton = (Button) findViewById(R.id.stop);
        stopButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 停止振動
				vibrator.cancel();
			}
        });
        
    }
}