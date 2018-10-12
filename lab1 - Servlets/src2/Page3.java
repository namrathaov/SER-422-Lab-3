

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Page3
 */
@WebServlet("/Page3")
public class Page3 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String[] progLang= {"Java","Perl","Python","C","PHP","R"};
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		for(int i=0;i<progLang.length;i++)
		{
			String lang=request.getParameter("languages");
		}
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		out.println("<label>When are you available?</label>\n" + 
				"    <input type=\"checkbox\" name=\"days\" value=\"M\">Monday\n" + 
				"    <input type=\"checkbox\" name=\"days\" value=\"T\">Tuesday\n" + 
				"    <input type=\"checkbox\" name=\"days\" value=\"W\">Wednesday\n" + 
				"    <input type=\"checkbox\" name=\"days\" value=\"Th\">Thursday\n" + 
				"    <input type=\"checkbox\" name=\"days\" value=\"F\">Friday\n" + 
				"    <input type=\"checkbox\" name=\"days\" value=\"S\">Saturday\n" + 
				"    <input type=\"checkbox\" name=\"days\" value=\"Su\">Sunday\n<input type=\\\"Submit\\\" value=\\\"Submit\\\"/>" + 
				"    <br/><br/>");
	}

}
