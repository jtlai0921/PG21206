package com.uppowerstudio.chapter5.phonebook;

import com.uppowerstudio.chapter5.phonebook.database.Constants;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

/**
 * 使用ContentResolver方式獲取聯絡人清單
 * @author UPPower Studio
 *
 */
@SuppressWarnings("unused")
public class ListContactsActivity extends ListActivity implements Constants{
	
	// 宣告按鈕變數
	private Button addContactButton;
	
	// 宣告ContentResolver變數
	private ContentResolver contentResolver;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// 載入佈局檔list.xml
		setContentView(R.layout.list);
		
		// 獲取ContentResolver對象
		contentResolver=getContentResolver();
		
		// 初始化添加聯絡人按鈕
		addContactButton=(Button) findViewById(R.id.button_add);
		
		// 註冊事件監聽器
		addContactButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// 建構action為action_add_contact的Intent對象
				Intent addIntent=new Intent("action_add_contact");
				// 使用帶回傳值的方式啟用添加聯絡人Activity
				startActivityForResult(addIntent, 1);
			}
		});

		// 向ListView填充數據
		renderContactList();

		// 註冊上下文選單，用於長按ListView中聯絡人記錄後彈出刪除功能表
		registerForContextMenu(getListView());
	}

	/**
	 * 在呼叫的目標群組件結束後將執行此方法
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		/**
		 * 在"添加聯絡人"、"更新聯絡人"介面完成操作關閉後，需要重新讀取資料並填充ListView
		 */
		renderContactList();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// 獲取當前選擇的聯絡人ID
		long uid=l.getItemIdAtPosition(position);
		
		// 建構更新聯絡人介面的Intent物件
		Intent updateIntent=new Intent("action_update_contact");
		// 將聯絡人ID儲存，並傳給更新介面使用
		updateIntent.putExtra("uid", uid);
		// 使用帶回傳值的方式啟用更新聯絡人Activity
		startActivityForResult(updateIntent, 1);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
				
		// 獲得當前選擇的position
		int selectedPosition=((AdapterContextMenuInfo) menuInfo).position;
		
		// 獲取當前選中聯絡人的ID
		long uid=getListView().getItemIdAtPosition(selectedPosition);
		Uri uri=Uri.withAppendedPath(CONTENT_URI, String.valueOf(uid));
		Cursor c=contentResolver.query(uri, null, null, null, null);
		if (c.moveToFirst()){
			// 從返回的游標記錄中獲取當前選擇的聯絡人姓名
			String contactName=c.getString(c.getColumnIndex("contact_name")); 
			
			// 設置上下文選單為聯絡人姓名
			menu.setHeaderTitle(contactName);
		}
		// 添加上下文選單
		menu.add(0, 0, 0, this.getResources().getText(R.string.ctx_menu_delete));
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// 獲得當前選擇的position
		int selectedPosition=((AdapterContextMenuInfo) item.getMenuInfo()).position;
		// 顯示刪除確認對話方塊
		showDialog(selectedPosition);
		return true;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		
		String cmsg=getString(R.string.msg_delete_confirm);
		
		// 通過傳入的position得到選擇的聯絡人ID值用於刪除操作
		final long uid=getListView().getItemIdAtPosition(id);
		
		// 呼叫genDialog方法建構顯示刪除確認對話方塊
		return genDialog(getString(R.string.dialog_title), cmsg,
				android.R.drawable.ic_dialog_alert,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						deleteContact(uid);
					}
				}, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						renderContactList();
					}
				});
	}

	/**
	 * 完成向ListView填充資料操作
	 */
	private void renderContactList() {
		// 呼叫ContentProvider提供的查詢獲取所有存在的聯絡人資料
		Cursor c=contentResolver.query(Constants.CONTENT_URI, null, null, null, COLUMN_ID);
		
		// 使用返回的游標Cursor物件建構ListView的資料配接器
		SimpleCursorAdapter adapter=new SimpleCursorAdapter(this,
				R.layout.list_row, c, new String[] { "contact_name",
						"phone_number" }, new int[] {
						R.id.list_item_contact_name,
						R.id.list_item_contact_phone });
		
		// 設置ListView的數據配接器
		setListAdapter(adapter);
	}
	
	/**
	 * 刪除選擇的聯絡人
	 * @param uid 選擇的聯絡人ID
	 */
	private void deleteContact(long uid) {
		// 呼叫ContentProvider提供的方法刪除指定的聯絡人
		int result=contentResolver.delete(Constants.CONTENT_URI, COLUMN_ID+"=?", new String[]{String.valueOf(uid)});
		
		// 以Toast消息方式顯示刪除是否成功
		if (result > 0) {
			Toast.makeText(getBaseContext(),
					getString(R.string.msg_delete_success), Toast.LENGTH_LONG)
					.show();
		} else {
			Toast.makeText(getBaseContext(),
					getString(R.string.msg_delete_failure), Toast.LENGTH_LONG)
					.show();
		}

		// 重新填充ListView;
		renderContactList();
		
	}

	/**
	 * 生成對話方塊
	 * @param title
	 * @param msg
	 * @param title_icon
	 * @param positiveEvent
	 * @param negativeEvent
	 * @return
	 */
	private Dialog genDialog(String title, String msg, int title_icon,
			DialogInterface.OnClickListener positiveEvent,
			DialogInterface.OnClickListener negativeEvent) {
		// 設置對話方塊的圖示、標題及消息
		Builder dialog=new AlertDialog.Builder(ListContactsActivity.this)
				.setIcon(title_icon).setTitle(title).setMessage(msg);
		// 添加確認按鈕事件監聽器
		if (positiveEvent != null) {
			dialog.setPositiveButton(getString(R.string.button_submit),
					positiveEvent);
		}
		
		// 添加取消按鈕事件監聽器
		if (negativeEvent != null) {
			dialog.setNegativeButton(getString(R.string.button_cancel),
					negativeEvent);
		}
		
		// 設置不能使用返回鍵取消彈出對話方塊
		dialog.setCancelable(false);
		
		// 生成並返回對話方塊實例
		return dialog.create();
	}
}
