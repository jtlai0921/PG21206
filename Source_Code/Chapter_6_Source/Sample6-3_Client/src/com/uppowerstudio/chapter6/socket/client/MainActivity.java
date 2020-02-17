package com.uppowerstudio.chapter6.socket.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * ��������ͻ���
 * 
 * @author UPPower Studio
 *
 */
public class MainActivity extends Activity {

	// ��������ַ
	private final String SERVER_HOST_IP = "10.0.0.5";

	// �������˿�
	private final int SERVER_HOST_PORT = 9000;

	// �����ؼ�����
	private Button buttonConnect;
	private Button buttonSend;
	private Button buttonClear;
	private EditText txtNickName;
	private EditText txtChatLog;
	private EditText txtMessage;

	// �û��Ƿ�ע���ǳ�
	private boolean registerNickName;

	// ��������
	private Socket socket;
	private InputStream serverReader;
	private BufferedReader br;
	private PrintStream writer;
	private String mLogMessage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// ��ʼ������ؼ�
		buttonConnect = (Button) findViewById(R.id.button_connect);
		buttonSend = (Button) findViewById(R.id.button_send);
		buttonClear = (Button) findViewById(R.id.button_clear);
		txtNickName = (EditText) findViewById(R.id.txt_nick_name);
		txtChatLog = (EditText) findViewById(R.id.txt_chat_log);
		txtMessage = (EditText) findViewById(R.id.txt_message);
		
		// ��δ���ӵ�������ǰ��ʹ�÷��ͣ������ť������
		buttonSend.setEnabled(false);
		buttonClear.setEnabled(false);
		
		// ������ʾ�����¼��ı�����ǰ��ɫ��
		txtChatLog.setEnabled(false);
		txtChatLog.setTextColor(Color.WHITE);
		txtChatLog.setBackgroundColor(Color.BLACK);
		
		// ע�����Ӱ�ť�����¼�������
		buttonConnect.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				initClientSocket();
			}
			
		});
		
		// ע�ᷢ�Ͱ�ť�����¼�������
		buttonSend.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// ��ȡ�û��������Ϣ����
				String sendMsg = txtMessage.getText().toString().trim() + "\n";
				
				// ������Ϣ
				sendMessage(sendMsg);
				
				// �����Ϣ���Ϳ�׼���´η���
				txtMessage.setText("");
			}
			
		});
		
		// ע�������ť�����¼�������
		buttonClear.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// ��������¼����Ϣ���Ϳ�����
				txtChatLog.setText("");
				txtMessage.setText("");
			}
		});
	}

	/**
	 * ��ʼ���ͻ�������
	 */
	private void initClientSocket() {
		
		try {
			// ���ӷ�����
			socket = new Socket(SERVER_HOST_IP, SERVER_HOST_PORT);
			
			// ��ȡ������
			serverReader = socket.getInputStream();
			br = new BufferedReader(new InputStreamReader(serverReader, "utf-8"));
			
			// ��ȡ�����
			writer = new PrintStream(socket.getOutputStream(), true, "utf-8");
			
			// ע���û��ǳ�
			if (!registerNickName) {
				register(txtNickName.getText().toString().trim());
				registerNickName = true;
			}		
			
			// ���ð�ť״̬Ϊ����
			buttonConnect.setEnabled(false);
			buttonClear.setEnabled(true);
			buttonSend.setEnabled(true);
			
			// �����ǳ������Ϊ����д״̬
			txtNickName.setEnabled(false);
			
			// ������̨�����̣߳����ڽ��ա�������Ϣ
			Thread thread = new Thread(task);
			thread.start();
		} catch (UnknownHostException e) {
			Toast.makeText(MainActivity.this,
					getString(R.string.error_msg_unknow_host),
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		} catch (IOException e) {
			Toast.makeText(MainActivity.this,
					getString(R.string.error_msg_cannot_connect),
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
	}

	/**
	 * �����̨�̣߳����ڽ��ա�������Ϣ
	 */
	private Runnable task = new Runnable() {
		@Override
		public void run() {
			String message = "";
			while (true) {
				try {
					// ��ȡ����������������Ϣ
					message = br.readLine();
					// �û�û��ע���ǳ�
					if (message.equals("REG")) { 
						registerNickName = false;
					} else {
						mLogMessage = message + "\n";
						// ֪ͨAndroid UI�̸߳��½���
						handler.sendMessage(handler.obtainMessage());
					}
				} catch (SocketException ex) {
					Toast.makeText(MainActivity.this,
							getString(R.string.error_msg_lost_connect),
							Toast.LENGTH_LONG).show();
					break;
				} catch (Exception ex) {
					ex.printStackTrace();
					break;
				}
			}
		}

	};
	
	/**
	 * Android��Ϣ������
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// ���������¼��ʾ��
			txtChatLog.append(mLogMessage);
		}
	};
	
	/**
	 * ������Ϣ
	 * @param msg
	 */
	private void sendMessage(String msg) {
		writer.println("MSG-" + msg + "\n");
	}

	/**
	 * ע���ǳ�
	 * @param name
	 */
	private void register(String name) {
		writer.println("NN-" + name);
	}
}