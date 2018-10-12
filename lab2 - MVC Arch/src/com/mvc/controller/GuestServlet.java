package com.mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

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
 * Servlet implementation class GuestServlet
 */
public class GuestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GuestServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String user = (String) session.getAttribute("name");
		PrintWriter out = response.getWriter();
		StoryBean storyBean = new StoryBean();
		NewStory newStory = new NewStory();
		ArrayList<Integer> publicStories = newStory.getPublicStories();
		ArrayList<Integer> allStories = newStory.getAllStories();
		
		Iterator<Integer> storyIter = allStories.iterator();
		out.print("<html><body><ol>");
		while (storyIter.hasNext()) {
			Integer storyID = storyIter.next();
			String storyTitle = newStory.getStoryTitle(storyID);
			//Link if has permission to view
			if(publicStories.contains(storyID)) {
				out.print("<li><a href=DisplayStory.jsp?storyID=" + storyID + ">" + storyTitle + "</a></li>");
			} else {
				out.print("<li>" + storyTitle + "</li>");
			}
		}
		out.print("</ol><a href=Login.jsp> Login here!</a></body></html>");
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

}
