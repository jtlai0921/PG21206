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
 * 简易聊天客户端
 * 
 * @author UPPower Studio
 *
 */
public class MainActivity extends Activity {

	// 服务器地址
	private final String SERVER_HOST_IP = "10.0.0.5";

	// 服务器端口
	private final int SERVER_HOST_PORT = 9000;

	// 声明控件变量
	private Button buttonConnect;
	private Button buttonSend;
	private Button buttonClear;
	private EditText txtNickName;
	private EditText txtChatLog;
	private EditText txtMessage;

	// 用户是否注册昵称
	private boolean registerNickName;

	// 声明变量
	private Socket socket;
	private InputStream serverReader;
	private BufferedReader br;
	private PrintStream writer;
	private String mLogMessage;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		// 初始化界面控件
		buttonConnect = (Button) findViewById(R.id.button_connect);
		buttonSend = (Button) findViewById(R.id.button_send);
		buttonClear = (Button) findViewById(R.id.button_clear);
		txtNickName = (EditText) findViewById(R.id.txt_nick_name);
		txtChatLog = (EditText) findViewById(R.id.txt_chat_log);
		txtMessage = (EditText) findViewById(R.id.txt_message);
		
		// 在未连接到服务器前，使得发送，清除按钮不可用
		buttonSend.setEnabled(false);
		buttonClear.setEnabled(false);
		
		// 设置显示聊天记录框的背景、前景色等
		txtChatLog.setEnabled(false);
		txtChatLog.setTextColor(Color.WHITE);
		txtChatLog.setBackgroundColor(Color.BLACK);
		
		// 注册连接按钮单击事件监听器
		buttonConnect.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				initClientSocket();
			}
			
		});
		
		// 注册发送按钮单击事件监听器
		buttonSend.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 获取用户输入的消息内容
				String sendMsg = txtMessage.getText().toString().trim() + "\n";
				
				// 发送消息
				sendMessage(sendMsg);
				
				// 清空消息发送框，准备下次发送
				txtMessage.setText("");
			}
			
		});
		
		// 注册清除按钮单击事件监听器
		buttonClear.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// 清除聊天记录和消息发送框内容
				txtChatLog.setText("");
				txtMessage.setText("");
			}
		});
	}

	/**
	 * 初始化客户端连接
	 */
	private void initClientSocket() {
		
		try {
			// 连接服务器
			socket = new Socket(SERVER_HOST_IP, SERVER_HOST_PORT);
			
			// 获取输入流
			serverReader = socket.getInputStream();
			br = new BufferedReader(new InputStreamReader(serverReader, "utf-8"));
			
			// 获取输出流
			writer = new PrintStream(socket.getOutputStream(), true, "utf-8");
			
			// 注册用户昵称
			if (!registerNickName) {
				register(txtNickName.getText().toString().trim());
				registerNickName = true;
			}		
			
			// 设置按钮状态为可用
			buttonConnect.setEnabled(false);
			buttonClear.setEnabled(true);
			buttonSend.setEnabled(true);
			
			// 设置昵称输入框为不可写状态
			txtNickName.setEnabled(false);
			
			// 开启后台监听线程，用于接收、发送消息
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
	 * 定义后台线程，用于接收、发送消息
	 */
	private Runnable task = new Runnable() {
		@Override
		public void run() {
			String message = "";
			while (true) {
				try {
					// 读取服务器发送来的消息
					message = br.readLine();
					// 用户没有注册昵称
					if (message.equals("REG")) { 
						registerNickName = false;
					} else {
						mLogMessage = message + "\n";
						// 通知Android UI线程更新界面
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
	 * Android消息处理器
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// 更新聊天记录显示框
			txtChatLog.append(mLogMessage);
		}
	};
	
	/**
	 * 发送消息
	 * @param msg
	 */
	private void sendMessage(String msg) {
		writer.println("MSG-" + msg + "\n");
	}

	/**
	 * 注册昵称
	 * @param name
	 */
	private void register(String name) {
		writer.println("NN-" + name);
	}
}