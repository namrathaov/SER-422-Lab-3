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
 * Servlet implementation class RemoveFavourite
 */
public class RemoveFavourite extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoveFavourite() {
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
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		System.out.println("Inside Remove Fav POST");
		System.out.println("Story ID:" + request.getParameter("storyID"));
		String user = (String) session.getAttribute("name");
		Integer storyID = Integer.parseInt(request.getParameter("storyID"));
		NewStory newStory = new NewStory();
		StoryBean storyBean = new StoryBean();
		newStory.removeFavourite(user, storyID);
		response.sendRedirect("Home.jsp");
		//doGet(request, response);
	}

}
