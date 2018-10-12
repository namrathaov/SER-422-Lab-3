package com.mvc.controller;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mvc.bean.LoginBean;
import com.mvc.dao.LoginDAOInterface;
import com.mvc.dao.LoginDBDao;
import com.mvc.dao.LoginDao;

public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String role="";	
		LoginBean loginBean = new LoginBean();
		loginBean.setUserName(userName); 
		loginBean.setPassword(password);
		loginBean.setRole(role);
		LoginDAOInterface loginDao = new LoginDao(); 
		String userValidate = loginDao.authenticateUser(loginBean); 
		if(userValidate.equals("SUCCESS")) 
		{
			HttpSession session = request.getSession();
			System.out.println(userValidate);
			Cookie firstName = new Cookie("lab2Cookie_name", userName);
			firstName.setMaxAge(60*60*24);
			response.addCookie( firstName );
			role = loginDao.getUserType(loginBean);
			Cookie roleCookie = new Cookie("lab2Cookie_role", role);
			roleCookie.setMaxAge(60*60*24);
			response.addCookie( roleCookie );
			request.setAttribute("username", userName); 
			request.setAttribute("password", password); 
			System.out.println("Inside Login servlet"+role);
			request.setAttribute("role", role);
			session.setAttribute("name", userName);
			session.setAttribute("role", role);
			response.sendRedirect("Home.jsp");
		}
		else if(userValidate.equals("Incorrect password"))
		{
			System.out.print(userValidate);
			request.setAttribute("errMessage", userValidate); 
			request.getRequestDispatcher("/Login.jsp").forward(request, response);
		}
		else if(userValidate.equals("User does not exist"))
		{
			System.out.println(userValidate);
			request.setAttribute("errMessage", userValidate);
			request.getRequestDispatcher("/CreateUser.jsp").forward(request, response);
		}
		else
		{
			System.out.println("error in login");
		}
	}
}