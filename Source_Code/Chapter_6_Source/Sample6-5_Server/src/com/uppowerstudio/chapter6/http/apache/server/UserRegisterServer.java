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
 * HTTP用户注册示例服务器端
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
	 * 当请求为HTTP GET时执行此方法
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 通过request.getParameter方法获取从客户端发送来的数据
		String userName = request.getParameter("un");
		String email = request.getParameter("email");
		
		// 显示传入数据到控制台窗口
		System.out.println("Register User Name = " + userName);
		System.out.println("Register User Email = " + email);
		
		// 向客户端发送数据
		response.getWriter().print("HTTP GET Request.\nUser ID = " + generateUserId());
	}

	/**
	 * 当请求为HTTP POST时执行此方法
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 读取从客户端发送来的数据
		BufferedReader br = new BufferedReader(new InputStreamReader((ServletInputStream)request.getInputStream()));
        String data = null;
        StringBuilder sb = new StringBuilder();
        while((data = br.readLine())!=null){
            sb.append(data);
        }
        
        // 对数据进行解码操作
        String temp = URLDecoder.decode(sb.toString(), "utf-8");
        
        // 显示传入数据到控制台窗口
        System.out.println("Register User Information = " + temp);
        
        // 向客户端发送数据
		response.getWriter().print("HTTP POST Request.\nUser ID = " + generateUserId());
	}
	
	/**
	 * 生成随机数代表当前注册用户ID
	 * @return
	 */
	private String generateUserId(){
		Random ran = new Random();
		return String.valueOf(Math.abs(ran.nextInt()));
	}
}
