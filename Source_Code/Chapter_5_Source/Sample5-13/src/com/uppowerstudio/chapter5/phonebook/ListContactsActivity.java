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
 * �ϥ�ContentResolver�覡����p���H�M��
 * @author UPPower Studio
 *
 */
@SuppressWarnings("unused")
public class ListContactsActivity extends ListActivity implements Constants{
	
	// �ŧi���s�ܼ�
	private Button addContactButton;
	
	// �ŧiContentResolver�ܼ�
	private ContentResolver contentResolver;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// ���J�G����list.xml
		setContentView(R.layout.list);
		
		// ���ContentResolver��H
		contentResolver=getContentResolver();
		
		// ��l�ƲK�[�p���H���s
		addContactButton=(Button) findViewById(R.id.button_add);
		
		// ���U�ƥ��ť��
		addContactButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// �غcaction��action_add_contact��Intent��H
				Intent addIntent=new Intent("action_add_contact");
				// �ϥαa�^�ǭȪ��覡�ҥβK�[�p���HActivity
				startActivityForResult(addIntent, 1);
			}
		});

		// �VListView��R�ƾ�
		renderContactList();

		// ���U�W�U����A�Ω����ListView���p���H�O����u�X�R���\���
		registerForContextMenu(getListView());
	}

	/**
	 * �b�I�s���ؼиs�ե󵲧���N���榹��k
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		/**
		 * �b"�K�[�p���H"�B"��s�p���H"���������ާ@������A�ݭn���sŪ����ƨö�RListView
		 */
		renderContactList();
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// �����e��ܪ��p���HID
		long uid=l.getItemIdAtPosition(position);
		
		// �غc��s�p���H������Intent����
		Intent updateIntent=new Intent("action_update_contact");
		// �N�p���HID�x�s�A�öǵ���s�����ϥ�
		updateIntent.putExtra("uid", uid);
		// �ϥαa�^�ǭȪ��覡�ҥΧ�s�p���HActivity
		startActivityForResult(updateIntent, 1);
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
				
		// ��o��e��ܪ�position
		int selectedPosition=((AdapterContextMenuInfo) menuInfo).position;
		
		// �����e�襤�p���H��ID
		long uid=getListView().getItemIdAtPosition(selectedPosition);
		Uri uri=Uri.withAppendedPath(CONTENT_URI, String.valueOf(uid));
		Cursor c=contentResolver.query(uri, null, null, null, null);
		if (c.moveToFirst()){
			// �q��^����аO���������e��ܪ��p���H�m�W
			String contactName=c.getString(c.getColumnIndex("contact_name")); 
			
			// �]�m�W�U���欰�p���H�m�W
			menu.setHeaderTitle(contactName);
		}
		// �K�[�W�U����
		menu.add(0, 0, 0, this.getResources().getText(R.string.ctx_menu_delete));
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// ��o��e��ܪ�position
		int selectedPosition=((AdapterContextMenuInfo) item.getMenuInfo()).position;
		// ��ܧR���T�{��ܤ��
		showDialog(selectedPosition);
		return true;
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		
		String cmsg=getString(R.string.msg_delete_confirm);
		
		// �q�L�ǤJ��position�o���ܪ��p���HID�ȥΩ�R���ާ@
		final long uid=getListView().getItemIdAtPosition(id);
		
		// �I�sgenDialog��k�غc��ܧR���T�{��ܤ��
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
	 * �����VListView��R��ƾާ@
	 */
	private void renderContactList() {
		// �I�sContentProvider���Ѫ��d������Ҧ��s�b���p���H���
		Cursor c=contentResolver.query(Constants.CONTENT_URI, null, null, null, COLUMN_ID);
		
		// �ϥΪ�^�����Cursor����غcListView����ưt����
		SimpleCursorAdapter adapter=new SimpleCursorAdapter(this,
				R.layout.list_row, c, new String[] { "contact_name",
						"phone_number" }, new int[] {
						R.id.list_item_contact_name,
						R.id.list_item_contact_phone });
		
		// �]�mListView���ƾڰt����
		setListAdapter(adapter);
	}
	
	/**
	 * �R����ܪ��p���H
	 * @param uid ��ܪ��p���HID
	 */
	private void deleteContact(long uid) {
		// �I�sContentProvider���Ѫ���k�R�����w���p���H
		int result=contentResolver.delete(Constants.CONTENT_URI, COLUMN_ID+"=?", new String[]{String.valueOf(uid)});
		
		// �HToast�����覡��ܧR���O�_���\
		if (result > 0) {
			Toast.makeText(getBaseContext(),
					getString(R.string.msg_delete_success), Toast.LENGTH_LONG)
					.show();
		} else {
			Toast.makeText(getBaseContext(),
					getString(R.string.msg_delete_failure), Toast.LENGTH_LONG)
					.show();
		}

		// ���s��RListView;
		renderContactList();
		
	}

	/**
	 * �ͦ���ܤ��
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
		// �]�m��ܤ�����ϥܡB���D�ή���
		Builder dialog=new AlertDialog.Builder(ListContactsActivity.this)
				.setIcon(title_icon).setTitle(title).setMessage(msg);
		// �K�[�T�{���s�ƥ��ť��
		if (positiveEvent != null) {
			dialog.setPositiveButton(getString(R.string.button_submit),
					positiveEvent);
		}
		
		// �K�[�������s�ƥ��ť��
		if (negativeEvent != null) {
			dialog.setNegativeButton(getString(R.string.button_cancel),
					negativeEvent);
		}
		
		// �]�m����ϥΪ�^������u�X��ܤ��
		dialog.setCancelable(false);
		
		// �ͦ��ê�^��ܤ�����
		return dialog.create();
	}
}
