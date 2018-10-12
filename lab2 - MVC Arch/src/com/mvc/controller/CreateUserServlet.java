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

/**
 * Servlet implementation class CreateUserServlet
 */
public class CreateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateUserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		String role = request.getParameter("role");
		PrintWriter out = response.getWriter();

		LoginBean loginBean = new LoginBean(); 
		loginBean.setUserName(userName);		loginBean.setPassword(password);
		loginBean.setRole(role);
		LoginDAOInterface loginDao = new LoginDao(); 
		String userCreated = loginDao.createUser(loginBean); 
		if(userCreated.equals("SUCCESS"))
		{
			Cookie firstName = new Cookie("lab2Cookie_name", userName);
			firstName.setMaxAge(60*60*24);
			response.addCookie( firstName );
			Cookie roleCookie = new Cookie("lab2Cookie_role", role);
			roleCookie.setMaxAge(60*60*24);
			response.addCookie( roleCookie );
			HttpSession session = request.getSession(true);
			session.setAttribute("name", userName);
			session.setAttribute("role", role);
			System.out.println("CreateUserServelet success created user: " + userName + " " + session.getAttribute("role"));
			//request.getRequestDispatcher("/Home.jsp").forward(request, response);
			response.sendRedirect(request.getContextPath()+"/Home.jsp");
		}
		else if(userCreated.equals("User already exists"))
		{
			request.setAttribute("errMessage", userCreated);
			response.sendRedirect(request.getContextPath() + "/CreateUser.jsp");

		} else {
			System.out.println("Error in CreateUserServlet");
			System.out.println("Return: " + userCreated);
		}
	}

}