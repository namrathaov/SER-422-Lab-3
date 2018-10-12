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

import com.mvc.bean.LoginBean;
import com.mvc.bean.StoryBean;
import com.mvc.dao.NewStory;
import com.mvc.dao.NewStoryDao;
import com.mvc.util.DatabaseMap;
import com.mvc.util.Story;

/**
 * Servlet implementation class ReporterServlet
 */
public class ReporterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		System.out.println("Inside ReporterServlet");
		StoryBean storyBean = new StoryBean();
		NewStory newStory = new NewStory();
		LoginBean loginBean = new LoginBean();
		HttpSession session = request.getSession(false);
		System.out.print(session);
		if (session != null) {
			out.print("<a href=CreateNewStory.jsp>Create New Story</a>");
			String userName = (String) session.getAttribute("name");
			loginBean.setUserName(userName);

			// Get Author stories
			ArrayList<Integer> authorStories = newStory.getAuthorStories(userName);
			Iterator<Integer> storyIter = authorStories.iterator();
			out.print("<ol>");
			while (storyIter.hasNext()) {
				//Story story = storyIter.next();
				Integer storyID = storyIter.next();// newStory.getStoryID(story);
				newStory.getStory(storyID, storyBean);
				System.out.println("ID: " + storyID + "Title: " + storyBean.getStory());
				out.print("<li><a href=DisplayStory.jsp?storyID=" + storyID + ">" + storyBean.getStoryTitle()
						+ "</a></li>");
			}
			
			//Get public stories
			ArrayList<Integer> publicStories = newStory.getPublicStories();
			ArrayList<Integer> allStories = newStory.getAllStories();
			Iterator<Integer>allIter = allStories.iterator();
			
			while (allIter.hasNext()) {
				Integer storyID = allIter.next();
				System.out.println("Public story: " + storyID);
				if(authorStories.contains(storyID)) {
					continue;
				}
				String storyTitle = newStory.getStoryTitle(storyID);
				// out.print("<li><a href=DisplayStory.jsp name=storyID
				// value="+storyID+">"+storyTitle+"</a></li>");
				if(authorStories.contains(storyID)) {
					//out.print("<li><a href=DisplayStory.jsp?storyID=" + storyID + ">" + storyTitle + "</a></li>");
				} else if (publicStories.contains(storyID)){
					out.print("<li><a href=DisplayStory.jsp?storyID=" + storyID + ">" + storyTitle + "</a></li>");
				} else {
					out.print("<li>" + storyTitle + "</li>");
				}
			}

			out.print("</ol>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

}