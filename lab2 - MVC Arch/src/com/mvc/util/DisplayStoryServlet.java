package com.mvc.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mvc.bean.StoryBean;
import com.mvc.dao.NewStory;
import com.mvc.dao.NewStoryDao;

public class DisplayStoryServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		System.out.println("Inside Display Story");
		System.out.println("Story ID:" + request.getParameter("storyID"));
		
		Integer storyID = Integer.parseInt(request.getParameter("storyID"));
		request.setAttribute("storyID", storyID);
		NewStory newStory = new NewStory();
		StoryBean storyBean = new StoryBean();
		newStory.getStory(storyID, storyBean);
		if (session != null) {
			// Display only if viewer has permission
			String user = (String) session.getAttribute("name");
			String role = (String) session.getAttribute("role");
			if (storyBean.getVisibility().equals("Public") || (role !=null && (role.equals("reporter")&&
					(session.getAttribute("name").equals(storyBean.getAuthor()))||role.equals("subscriber")))) {
				System.out.println("Has access");
				out.print(user+" "+role+"<br><br>Story :<br><br><table border=1><tr><td>Title:</td><td>" + storyBean.getStoryTitle()
				+ "</td></tr><tr><td>Content:</td><td>" + storyBean.getStory() + "</td></tr></table>");
				if (role!= null && role.equals("reporter")) {
					
					// Show edit and delete only if author
					if (session.getAttribute("name").equals(storyBean.getAuthor())) {

						out.print("<form action=EditStory?storyID=" + storyID + " method=post>"
								+ "<br><input type=submit name=EditStory value=Edit Story></form>"
								+ "<form action=DeleteStory?storyID=" + storyID
								+ " method=post><input type=submit name=DeleteStory value=Delete Story></form>");
					}
				} else if (role!= null && role.equals("subscriber")) {
					if (newStory.isFavourite(user, storyID)) {
						System.out.println("FAV: YES " + user + " " + storyID);
						out.print("<form action=RemoveFavourite?storyID=" + storyID + " method=post>"
								+ "<br><input type=submit name=FavStory value=\"Remove as Favourite\"></form>");
					} else {
						System.out.println("FAV: NO " + user + " " + storyID);
						out.print("<form action=AddFavourite?storyID=" + storyID + " method=post>"
								+ "<br><input type=submit name=FavStory value=\"Add as Favourite\"></form>");
					}
				}
				if(role== null ||( !role.equals("subscriber") && !role.equals("reporter")))
				{
					out.print("<a href=GuestServlet>Return Home</a>");
					out.print("<br><a href=Login.jsp>Login here!</a>");
				}
				else
					{
					out.print("<a href=Home.jsp>Return Home</a>");
					out.print("<br><a href=LogoutServlet1>Logout!</a>");
					}
			} else {
				System.out.println("Has no access");
				//TODO: No permission to view this page
				response.sendRedirect("/GuestServlet");
				/*out.print("You do not have permission to view this page.");
				out.print("<li><a href=GuestServlet>Return Home</a></li>");
				out.print("<br><a href=Login.jsp>Login here!</a>");*/
			}
		}
	}
}