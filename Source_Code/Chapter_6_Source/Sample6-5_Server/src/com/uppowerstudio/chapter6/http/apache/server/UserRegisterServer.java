package com.uppowerstudio.chapter6.http.apache.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URLDecoder;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * HTTP�û�ע��ʾ����������
 * @author UPPower Studio
 * Servlet implementation class UserRegisterServer
 */
public class UserRegisterServer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserRegisterServer() {
        super();
    }

	/**
	 * ������ΪHTTP GETʱִ�д˷���
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ͨ��request.getParameter������ȡ�ӿͻ��˷�����������
		String userName = request.getParameter("un");
		String email = request.getParameter("email");
		
		// ��ʾ�������ݵ�����̨����
		System.out.println("Register User Name = " + userName);
		System.out.println("Register User Email = " + email);
		
		// ��ͻ��˷�������
		response.getWriter().print("HTTP GET Request.\nUser ID = " + generateUserId());
	}

	/**
	 * ������ΪHTTP POSTʱִ�д˷���
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// ��ȡ�ӿͻ��˷�����������
		BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
        String data = null;
        StringBuilder sb = new StringBuilder();
        while((data = br.readLine())!=null){
            sb.append(data);
        }
        
        // �����ݽ��н������
        String temp = URLDecoder.decode(sb.toString(), "utf-8");
        
        // ��ʾ�������ݵ�����̨����
        System.out.println("Register User Information = " + temp);
        
        // ��ͻ��˷�������
		response.getWriter().print("HTTP POST Request.\nUser ID = " + generateUserId());
	}
	
	/**
	 * �������������ǰע���û�ID
	 * @return
	 */
	private String generateUserId(){
		Random ran = new Random();
		return String.valueOf(Math.abs(ran.nextInt()));
	}
}
