package com.mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mvc.bean.StoryBean;
import com.mvc.dao.NewStory;
import com.mvc.dao.NewStoryDao;

/**
 * Servlet implementation class EditStory
 */
public class EditStory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session != null) {
			System.out.println("Inside Delete Story");
			// Delete should check if author
			String user = (String) session.getAttribute("name");
			String role = (String) session.getAttribute("role");
			PrintWriter out = response.getWriter();
			Integer storyID = -1;
			try {
				storyID = Integer.parseInt((String) request.getParameter("storyID"));
			} catch (Exception e) {
				out.print("<html><body>Invalid Story ID</body></html>");
				out.print("<li><a href=Home.jsp>Return Home</a></li>");
			}
			NewStory newStory = new NewStory();
			StoryBean storyBean = new StoryBean();
			newStory.getStory(storyID, storyBean);
			if ((role != null && role.equals("reporter")
					&& session.getAttribute("name").equals(storyBean.getAuthor()))) {

				System.out.println("Story ID: " + storyID);

				out.print("<html><body>"+user+" "+role+"<br>Edit Story<form action=EditStorySubmit?storyID="+storyID+" method=\"post\">\r\n" + 
						"<input type=\"text\" name=\"title\" value="+storyBean.getStoryTitle()+
								"><br><br><br>\r\n" + 
						"<textarea name=\"story\" rows=\"10\" cols=\"60\">"+storyBean.getStory()+"</textarea><br><br><br>\r\n" + 
						"<select name=\"visibility\">\r\n" + 
						"<option value=\"Public\">Public</option>\r\n" + 
						"<option value=\"Reporter\">Reporter only</option></select><br><br>\r\n" + 
						"<input type=\"submit\" name=\"SubmitStory\" value=\"Submit Story\">\r\n" + 
						"<a href=LogoutServlet1>Logout</a></form></body></html>");
			} else {
				out.print("<html><body>Something went wrong</body></html>");
				out.print("<li><a href=Home.jsp>Return Home</a></li>");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session=request.getSession();
		PrintWriter out = response.getWriter();
		System.out.println("Inside Edit Story");
		Integer storyID=Integer.parseInt((String) request.getParameter("storyID"));
		System.out.println("Story ID: "+storyID);
		NewStory newStory=new NewStory();
		StoryBean storyBean=new StoryBean();
		newStory.getStory(storyID,storyBean);
		if (session != null) {
			String user = (String) session.getAttribute("name");
			String role = (String) session.getAttribute("role");
			out.print("<html><body>"+user+" "+role+"<br>Edit Story<form action=EditStorySubmit?storyID="+storyID+" method=\"post\">\r\n" + 
				"<input type=\"text\" name=\"title\" value="+storyBean.getStoryTitle()+
						"><br><br><br>\r\n" + 
				"<textarea name=\"story\" rows=\"10\" cols=\"60\">"+storyBean.getStory()+"</textarea><br><br><br>\r\n" + 
				"<select name=\"visibility\">\r\n" + 
				"<option value=\"Public\">Public</option>\r\n" + 
				"<option value=\"Reporter\">Reporter only</option></select><br><br>\r\n" + 
				"<input type=\"submit\" name=\"SubmitStory\" value=\"Submit Story\">\r\n" + 
				"<br><br><a href=LogoutServlet1>Logout</a></form></body></html>");
		}
	}

}
