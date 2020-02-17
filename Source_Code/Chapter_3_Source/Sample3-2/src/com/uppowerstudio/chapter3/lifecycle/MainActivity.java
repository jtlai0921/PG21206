package com.uppowerstudio.chapter3.lifecycle;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

/**
 * 资源引用
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	private static final String TAG = "lifecycle";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String content = getResources().getString(R.string.lifecycle);
		String[] indexs = getResources().getStringArray(R.array.arrays_content);
		Log.i(TAG, content + indexs[0]);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		String content = getResources().getString(R.string.lifecycle);
		String[] indexs = getResources().getStringArray(R.array.arrays_content);
		Log.i(TAG, content + indexs[1]);
	}

	@Override
	protected void onPause() {
		super.onPause();
		String content = getResources().getString(R.string.lifecycle);
		String[] indexs = getResources().getStringArray(R.array.arrays_content);
		Log.i(TAG, content + indexs[2]);
	}

	@Override
	protected void onResume() {
		super.onResume();
		String content = getResources().getString(R.string.lifecycle);
		String[] indexs = getResources().getStringArray(R.array.arrays_content);
		Log.i(TAG, content + indexs[3]);
	}

	@Override
	protected void onStart() {
		super.onStart();
		String content = getResources().getString(R.string.lifecycle);
		String[] indexs = getResources().getStringArray(R.array.arrays_content);
		Log.i(TAG, content + indexs[4]);
	}

	@Override
	protected void onStop() {
		super.onStop();
		String content = getResources().getString(R.string.lifecycle);
		String[] indexs = getResources().getStringArray(R.array.arrays_content);
		Log.i(TAG, content + indexs[5]);
	}
}