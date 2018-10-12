package com.mvc.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mvc.bean.StoryBean;
import com.mvc.dao.NewStory;
import com.mvc.dao.NewStoryDao;
import com.mvc.util.Story;

/**
 * Servlet implementation class Subscriber
 */
public class Subscriber extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Subscriber() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		System.out.println("Inside Subscriber GET");
		HttpSession session = request.getSession();
		String user = (String) session.getAttribute("name");
		PrintWriter out = response.getWriter();
		StoryBean storyBean = new StoryBean();
		NewStory newStory = new NewStory();
		out.print("<ol>");
		
		//Favs
		ArrayList<Integer> favStories = newStory.getFavouriteStories(user);
		Iterator<Integer> storyIter = favStories.iterator();
		while (storyIter.hasNext()) {
			Integer storyID = storyIter.next();
			String storyTitle = newStory.getStoryTitle(storyID);
			// out.print("<li><a href=DisplayStory.jsp name=storyID
			// value="+storyID+">"+storyTitle+"</a></li>");
			if(newStory.isFavourite(user, storyID)) {
				out.print("<li><a href=DisplayStory.jsp?storyID=" + storyID + ">" + storyTitle + "\t (Fav) </a></li>");
			}
		}
		
		//Public
		ArrayList<Integer> publicStories = newStory.getPublicStories();
		ArrayList<Integer> allStories = newStory.getAllStories();
		storyIter = allStories.iterator();
		
		while (storyIter.hasNext()) {
			Integer storyID = storyIter.next();
			if(favStories.contains(storyID)) {
				continue;
			}
			String storyTitle = newStory.getStoryTitle(storyID);
			
			if(newStory.isFavourite(user, storyID)) {
				//out.print("<li><a href=DisplayStory.jsp?storyID=" + storyID + ">" + storyTitle + "\t (Fav) </a></li>");
			} else if(allStories.contains(storyID)){
				out.print("<li><a href=DisplayStory.jsp?storyID=" + storyID + ">" + storyTitle + "</a></li>");
			}
			/*else {
				out.print("<li>" + storyTitle + "</li>");
			}*/
		}
		out.print("</ol>");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
