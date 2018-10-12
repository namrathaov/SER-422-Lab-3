package client.tempuri;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Input
 */
public class Input extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Input() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		out.print("<HTML><HEAD><TITLE>Inputs</TITLE></HEAD><BODY><H1>Inputs</H1>");


		String method = request.getParameter("method");
		int methodID = 0;
		if (method == null) methodID = -1;

		if(methodID != -1) 
			methodID = Integer.parseInt(method);
		if(methodID==1 || methodID==2 || methodID==3) {
			request.setAttribute(method, 1);
			out.print("Select a method to test.");
			out.print("<FORM METHOD=POST ACTION=Result>"
					+"<INPUT TYPE=HIDDEN NAME=method VALUE="+method+">"
					+"<TABLE><TR><TD> city name:</TD>"
					+"<TD><INPUT TYPE=TEXT NAME=city_name></TD>"
					+"</TR><TR><TD>state name:</TD>"
					+"<TD><INPUT TYPE=TEXT NAME=state_name></TD></TR>");

		}
		if(methodID==2)
			{
			request.setAttribute(method, 2);
			}

		if(methodID==3) {
			request.setAttribute(method, 3);
		}
			
		out.print("</TABLE><BR><INPUT TYPE=SUBMIT NAME=Invoke VALUE=Invoke>" + 
				"<INPUT TYPE=RESET VALUE=Clear></BODY></HTML>");
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		response.getWriter().println("<html><body><p>Use " + "GET instead!</p></body></html>");
	
	}
}
