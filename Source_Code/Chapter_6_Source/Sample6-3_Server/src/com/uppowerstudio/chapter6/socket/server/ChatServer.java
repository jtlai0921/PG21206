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
 * ²����Ѧ��A����
 * 
 * @author UPPower Studio
 * 
 */
public class ChatServer {
	// ���A����
	private final int SERVER_PORT=9000;

	// ���A���a�}
	private final String SERVER_ADDRESS="10.0.0.5";

	// �ŧi�s�x�Τ���ܼ�
	private Vector<SystemChatAssistor> clients;

	// �ŧi�A�Ⱥݦ�}�ܼ�
	private InetAddress inetAddress;

	// �ŧi�A�Ⱥ�Socket�ܼ�
	private ServerSocket serverSocket;

	/**
	 * ��l�Ʀ��A���t�m
	 */
	public ChatServer() {
		clients=new Vector<SystemChatAssistor>();
		try {
			// ������A��IP�a�}
			inetAddress=InetAddress.getByName(SERVER_ADDRESS);

			// �s�W�A�Ⱥ�Socket�s��
			serverSocket=new ServerSocket(SERVER_PORT, Integer.MAX_VALUE,
					inetAddress);

			// �Ұʫ�O��������ݥΤ�ݶi��s��
			new Thread(runnable).start();

			// �V����x��X���A���Ұʦ��\���A
			System.out.println("���A���w�g�Ұ�...");
		} catch (UnknownHostException ex) {
			System.out.println("���A���a�}�L��.");
		} catch (BindException ex) {
			System.out.println("���A�������t�m�Ĭ�.");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * �w�q��O������A���C�Ӷi��s�����Τ�ݶ}�Ҥ@�Ӱ����
	 */
	private Runnable runnable=new Runnable() {
		public void run() {
			while (true) {
				try {
					// �P�B�B�z�A����Τ�ݳQ��|
					synchronized (this) {
						// ��ť�õ��ݥΤ�ݳs��
						Socket socket=serverSocket.accept();

						// �K�[�@�ӫȪA�Ω�o�e�t�ή���
						clients.addElement(new SystemChatAssistor(socket));

						System.out.println(new StringBuffer(socket
								.getInetAddress().getHostAddress()).append("/")
								.append(socket.getPort()).append(" �s��.")
								.toString());

						// �έp�u�W�H��
						countOnlineUsers();
					}
				} catch (Exception ex) {
					ex.printStackTrace();
					// �פ�A�Ⱥ�
					System.exit(1);
				}
			}
		}
	};

	/**
	 * �έp�u�W�Τ��`��
	 */
	private void countOnlineUsers() {
		System.out.println("�u�W�H��: "+clients.size());
	}

	/**
	 * ���A���t�ΧU��ҲաA�Ω�B�z�t�ή����ާ@
	 */
	class SystemChatAssistor {
		private String clientNickName="System";
		private Socket socket;
		private BufferedReader reader;
		private PrintStream writer;

		/**
		 * ��l�Ʀ��A���t�ΧU��
		 * 
		 */
		SystemChatAssistor(Socket socket) {
			this.socket=socket;
			try {
				// �����J�y
				reader=new BufferedReader(new InputStreamReader(
						socket.getInputStream(), "utf-8"));

				// �����X�y
				writer=new PrintStream(socket.getOutputStream(),true,"utf-8");
				
				// �Ұʨt�ΧU�������i���ť�ާ@
				new Thread(sysAssistor).start();
			} catch (Exception ex) {
				ex.printStackTrace();

				// �p�G�X�{���`�A����Ҧ��w�g�}�Ҫ��귽
				releaseResouces();
			}
		}

		private Runnable sysAssistor=new Runnable() {
			public void run() {
				while (true) {
					try {
						String message=reader.readLine();
						
						// ���쪺�����O���U�ʺ�
						if (message.startsWith("NN-")) {
							message=message.substring(3, message.length());
							registerClient(message);
						} else if(message.startsWith("MSG-")) { // ���쪺�����O���q����
							message=message.substring(3, message.length());
							sendMessageToOnlineClients(clientNickName + ": "
									+message);
						}
					} catch (Exception ex) {
						ex.printStackTrace();

						// �p�G�X�{���`�A����Ҧ��w�g�}�Ҫ��귽
						releaseResouces();

						// ���������
						break;
					}
				}
			}
		};

		// �N�����o�e���Ҧ��u�W���Τ�
		private void sendMessageToOnlineClients(String msg) {
			for (int i=0; i<clients.size(); i++) {
				SystemChatAssistor client=(SystemChatAssistor) clients.get(i);
				try {
					// �p�G�Τ�ݨS�����U�ʺ١A�h�����o�e����
					if (!"".equals(client.clientNickName)) {
						client.writer.println(msg);
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}

		// �Τ�ݼʺٵ��U
		private void registerClient(String name) {
			try {
				if (!"".equals(name)) {
					sendMessageToOnlineClients("�t�ή���: " + name + " �i�J��ѫ�.");
					clientNickName=name;
				} else {
					writer.println("REG");
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		/**
		 * ����Ȥ�Ҧ��Ϊ��귽
		 */
		public void releaseResouces() {
			try {
				System.out.println(new StringBuffer(socket.getInetAddress()
						.getHostAddress()).append("/").append(socket.getPort())
						.append(" ���_.").toString());
				sendMessageToOnlineClients("�t�ή���: "+clientNickName+"�w�g���}.");
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

	// �{���J�f��k
	public static void main(String[] args) {
		new ChatServer();
	}
}
