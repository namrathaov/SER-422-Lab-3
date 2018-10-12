package com.mvc.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mvc.bean.StoryBean;
import com.mvc.dao.NewStory;

/**
 * Servlet implementation class EditStorySubmit
 */
public class EditStorySubmit extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EditStorySubmit() {
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
		HttpSession session=request.getSession();
		
		if(session!=null) {
		String username=(String)request.getAttribute("name");
			System.out.println("Inside Edit Submission page");
			Integer storyID=Integer.parseInt(request.getParameter("storyID"));
			String title=request.getParameter("title");
			String content=request.getParameter("story");
			String visibility=request.getParameter("visibility");
			System.out.println("ID:"+storyID+" title: "+title+" content "+content+" visibility "+visibility);
			NewStory newStory=new NewStory();
			newStory.updateStory(storyID,title,content,visibility,username);
			response.sendRedirect("Home.jsp");
		}
	}

}
