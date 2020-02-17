package com.uppowerstudio.chapter6.socket.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.BindException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Vector;

/**
 * 簡易聊天伺服器端
 * 
 * @author UPPower Studio
 * 
 */
public class ChatServer {
	// 伺服器埠號
	private final int SERVER_PORT=9000;

	// 伺服器地址
	private final String SERVER_ADDRESS="10.0.0.5";

	// 宣告存儲用戶端變數
	private Vector<SystemChatAssistor> clients;

	// 宣告服務端位址變數
	private InetAddress inetAddress;

	// 宣告服務端Socket變數
	private ServerSocket serverSocket;

	/**
	 * 初始化伺服器配置
	 */
	public ChatServer() {
		clients=new Vector<SystemChatAssistor>();
		try {
			// 獲取伺服器IP地址
			inetAddress=InetAddress.getByName(SERVER_ADDRESS);

			// 新增服務端Socket連結
			serverSocket=new ServerSocket(SERVER_PORT, Integer.MAX_VALUE,
					inetAddress);

			// 啟動後臺執行緒等待用戶端進行連結
			new Thread(runnable).start();

			// 向控制台輸出伺服器啟動成功狀態
			System.out.println("伺服器已經啟動...");
		} catch (UnknownHostException ex) {
			System.out.println("伺服器地址無效.");
		} catch (BindException ex) {
			System.out.println("伺服器網路配置衝突.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 定義後臺執行緒，為每個進行連結的用戶端開啟一個執行緒
	 */
	private Runnable runnable=new Runnable() {
		public void run() {
			while (true) {
				try {
					// 同步處理，防止用戶端被遺漏
					synchronized (this) {
						// 監聽並等待用戶端連結
						Socket socket=serverSocket.accept();

						// 添加一個客服用於發送系統消息
						clients.addElement(new SystemChatAssistor(socket));

						System.out.println(new StringBuffer(socket
								.getInetAddress().getHostAddress()).append("/")
								.append(socket.getPort()).append(" 連結.")
								.toString());

						// 統計線上人數
						countOnlineUsers();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					// 終止服務端
					System.exit(1);
				}
			}
		}
	};

	/**
	 * 統計線上用戶總數
	 */
	private void countOnlineUsers() {
		System.out.println("線上人數: "+clients.size());
	}

	/**
	 * 伺服器系統助手模組，用於處理系統消息操作
	 */
	class SystemChatAssistor {
		private String clientNickName="System";
		private Socket socket;
		private BufferedReader reader;
		private PrintStream writer;

		/**
		 * 初始化伺服器系統助手
		 * 
		 */
		SystemChatAssistor(Socket socket) {
			this.socket=socket;
			try {
				// 獲取輸入流
				reader=new BufferedReader(new InputStreamReader(
						socket.getInputStream(), "utf-8"));

				// 獲取輸出流
				writer=new PrintStream(socket.getOutputStream(),true,"utf-8");
				
				// 啟動系統助手執行緒進行監聽操作
				new Thread(sysAssistor).start();
			} catch (Exception ex) {
				ex.printStackTrace();

				// 如果出現異常，釋放所有已經開啟的資源
				releaseResouces();
			}
		}

		private Runnable sysAssistor=new Runnable() {
			public void run() {
				while (true) {
					try {
						String message=reader.readLine();
						
						// 收到的消息是註冊暱稱
						if (message.startsWith("NN-")) {
							message=message.substring(3, message.length());
							registerClient(message);
						} else if(message.startsWith("MSG-")) { // 收到的消息是普通消息
							message=message.substring(3, message.length());
							sendMessageToOnlineClients(clientNickName + ": "
									+message);
						}
					} catch (Exception ex) {
						ex.printStackTrace();

						// 如果出現異常，釋放所有已經開啟的資源
						releaseResouces();

						// 結束執行緒
						break;
					}
				}
			}
		};

		// 將消息發送給所有線上的用戶
		private void sendMessageToOnlineClients(String msg) {
			for (int i=0; i<clients.size(); i++) {
				SystemChatAssistor client=(SystemChatAssistor) clients.get(i);
				try {
					// 如果用戶端沒有註冊暱稱，則不對其發送消息
					if (!"".equals(client.clientNickName)) {
						client.writer.println(msg);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		// 用戶端暱稱註冊
		private void registerClient(String name) {
			try {
				if (!"".equals(name)) {
					sendMessageToOnlineClients("系統消息: " + name + " 進入聊天室.");
					clientNickName=name;
				} else {
					writer.println("REG");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		/**
		 * 釋放客戶所佔用的資源
		 */
		public void releaseResouces() {
			try {
				System.out.println(new StringBuffer(socket.getInetAddress()
						.getHostAddress()).append("/").append(socket.getPort())
						.append(" 切斷.").toString());
				sendMessageToOnlineClients("系統消息: "+clientNickName+"已經離開.");
				socket.close();
				reader.close();
				writer.close();
			} catch (Exception ex) {
			} finally {
				socket=null;
				reader=null;
				writer=null;
				clients.remove(this);
				countOnlineUsers();
				System.gc();
			}
		}
	}

	// 程式入口方法
	public static void main(String[] args) {
		new ChatServer();
	}
}
