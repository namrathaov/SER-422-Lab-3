package com.mvc.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mvc.bean.LoginBean;
import com.mvc.bean.StoryBean;
import com.mvc.dao.NewStory;
import com.mvc.dao.NewStoryDao;

/**
 * Servlet implementation class CreateNewStory
 */
public class CreateNewStory extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CreateNewStory() {
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
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if (session != null) {
			StoryBean storyBean=new StoryBean();
			NewStory newStory = new NewStory();
			LoginBean loginBean=new LoginBean();
			String title=request.getParameter("title");
			storyBean.setStoryTitle(title);
			String story=request.getParameter("story");
			storyBean.setStory(story);
			String visibility=request.getParameter("visibility");
			storyBean.setVisibility( visibility);
			System.out.println("Inside Create New Story"+storyBean.getVisibility()+storyBean.getStory()+storyBean.getStoryTitle());
			String userName = (String)session.getAttribute("name");
			loginBean.setUserName(userName);
			storyBean.setStoryTitle(title);
			storyBean.setStory(story);
			storyBean.setVisibility(visibility);
			newStory.createStory(userName,storyBean);
			request.setAttribute("errMessage", "Story Published"); 
			response.sendRedirect(request.getContextPath()+"/Home.jsp");
		}
	}
}