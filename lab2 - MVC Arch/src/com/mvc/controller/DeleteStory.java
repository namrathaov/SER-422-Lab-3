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
 * Servlet implementation class DeleteStory
 */
public class DeleteStory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteStory() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.getWriter().append("Served at: ").append(request.getContextPath());
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

				out.print("<html><body>"+user+" "+role+"<br>Delete Story<form action=DeleteStorySubmit?storyID=" + storyID + " method=\"post\">\r\n"
						+ "Are you sure you want to delete this story?<br><input type=submit name=submit value=yes><br>"
						+ "<input type=submit name=submit value=cancel>" + "<br><br><a href=LogoutServlet1>Logout</a></form></body></html>");
			} else {
				out.print("<html><body>Something went wrong</body></html>");
				out.print("<li><a href=Home.jsp>Return Home</a></li>");
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);

	}
}
