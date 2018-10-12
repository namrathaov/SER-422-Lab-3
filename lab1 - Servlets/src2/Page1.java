import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class page1
 */
@WebServlet("/Page1")
public class Page1 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = request.getCookies();
		String first_name="";
		String last_name="";
		if (cookies!=null) {
			for (int i=0; i<cookies.length;i++)
			{
				if (cookies[i].getName().equals("lab1Cookie_first_name"))
				{
					first_name=cookies[i].getValue();
				}
				else if(cookies[i].getName().equals("lab1Cookie_last_name"))
				{
					last_name=cookies[i].getValue();
				}
			}
		}
		PrintWriter out=response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html xmlns=\"http://www.w3.org/1999/html\">\n" + 
				"<body>\n" + 
				"<h3>Please fill out the following form</h3>\n" + 
				"<form method=\"post\" action=\"Page2\" >");
		out.println("<label>First Name: </label>\n" + 
				"    <input type=\"text\" name=\"fname\" id=\"fname\" placeholder=\"Your first name here\"" + 
				"    value = \""+first_name+"\"/>" +
				"    <br/><br/>\n" + 
				"\n" + 
				"    <label>Last Name: </label>\n" + 
				"    <input type=\"text\" name=\"lname\" id=\"lname\" placeholder=\"Your last name here\"" +
				"     value = \""+last_name+"\"/>" +
				"    <br/><br/>");
		out.println("<input type=\"Submit\" value=\"Submit\"/><input type=\\\"hidden\\\" name=\\\"back\\\" value=\\\"WebApp1\\\" />\\"
				+ "<input type=\\\"hidden\\\" name=\\\"reset\\\" value=\\\"WebApp1\\\" />\\" + 
				"</form>\n" + 
				"</body>\n" + 
				"</html>");	
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}
	
}
