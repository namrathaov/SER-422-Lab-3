package client.tempuri;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Index extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		out.print("<HTML><HEAD><TITLE>Weather API Methods</TITLE></HEAD>"
				+ "<BODY><H1>Methods</H1><UL>"
				+ "<LI><A HREF=Input?method=1> Get Weather </A></LI>"
				+ "<LI><A HREF=Input?method=2> Get Weather by Hours</A></LI>"
				+ "<LI><A HREF=Input?method=3> Get Weather by 10 Days"
						+ "</A></LI></UL></BODY></HTML>");
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		response.getWriter().println("<html><body><p>Use " + "GET instead!</p></body></html>");

	}
}
