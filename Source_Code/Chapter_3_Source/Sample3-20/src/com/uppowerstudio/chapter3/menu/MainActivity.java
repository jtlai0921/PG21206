package com.uppowerstudio.chapter3.menu;

import android.app.Activity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 選單範例
 * 
 * @author UPPower Studio
 * 
 */
public class MainActivity extends Activity {
	// 定義功能表標識
	private static final int MENU_OPTION=0;
	private static final int MENU_HELP=1;
	private static final int MENU_ABOUT=2;
	private static final int MENU_QUIT=3;

	private static final int SUB_MENU_ADD=101;
	private static final int SUB_MENU_DEL=102;

	private static final int CONTEXT_MENU_ADD=201;
	private static final int CONTEXT_MENU_DEL=202;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		// 對TextView元件註冊ContextMenu
		registerForContextMenu((TextView) findViewById(R.id.txt));
	}

	// 新增選單
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// 新增一個包含子功能表的功能表，其中包含“添加”和“刪除”兩個子功能表
		SubMenu sub=menu.addSubMenu(0, MENU_OPTION, 0, R.string.menu_option);
		sub.setIcon(R.drawable.icon_menu_option);
		sub.add(0, SUB_MENU_ADD, 1, R.string.menu_add);
		sub.add(0, SUB_MENU_DEL, 2, R.string.menu_del);
		// 新增3個普通選單
		menu.add(0, MENU_HELP, 1, R.string.menu_help).setIcon(
				R.drawable.icon_menu_help);
		menu.add(0, MENU_ABOUT, 2, R.string.menu_about).setIcon(
				R.drawable.icon_menu_about);
		menu.add(0, MENU_QUIT, 3, R.string.menu_exit).setIcon(
				R.drawable.icon_menu_exit);
		// 返回true，選單可見，否則不可見
		return true;
	}

	// 定義功能表時單次點擊功能表的操作
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// 根據被單次點擊功能表的標識，執行對應的輸出
		switch (item.getItemId()) {
		case SUB_MENU_ADD:
			Toast.makeText(MainActivity.this, R.string.menu_add, 1).show();
			break;
		case SUB_MENU_DEL:
			Toast.makeText(MainActivity.this, R.string.menu_del, 1).show();
			break;
		case MENU_HELP:
			Toast.makeText(MainActivity.this, R.string.menu_help, 1).show();
			break;
		case MENU_ABOUT:
			Toast.makeText(MainActivity.this, R.string.menu_about, 1).show();
			break;
		case MENU_QUIT:
			Toast.makeText(MainActivity.this, R.string.menu_exit, 1).show();
			break;
		}
		return true;
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		// 新增上下文選單
		menu.add(0, CONTEXT_MENU_ADD, 1, R.string.context_menu_add);
		menu.add(0, CONTEXT_MENU_DEL, 2, R.string.context_menu_del);
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// 根據被單次點擊上下文功能表的標識，執行對應的輸出
		switch (item.getItemId()) {
		case CONTEXT_MENU_ADD:
			Toast.makeText(MainActivity.this, R.string.context_menu_add, 1)
					.show();
			break;
		case CONTEXT_MENU_DEL:
			Toast.makeText(MainActivity.this, R.string.context_menu_del, 1)
					.show();
			break;
		}
		return true;
	}
}
