import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
public class Lab1task1 extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, 
			HttpServletResponse response)
					throws ServletException, IOException 
	{
		Cookie[] cookies=request.getCookies();
		String first_name="";
		String last_name="";
		
		if(cookies!=null)
		{
			for(int i=0;i<cookies.length;i++)
			{
				if(cookies[i].getName().equals("lab1Cookie_first_name"))
				{
					first_name = cookies[i].getValue();
				}
				else if(cookies[i].getName().equals("lab1Cookie_last_name"))
				{
					last_name = cookies[i].getValue();
				}
			}
		}
		PrintWriter out = response.getWriter();
		out.println("<!DOCTYPE html>");
		out.println("<html xmlns=\"http://www.w3.org/1999/html\">\n<body>\n"+
				"<h3>Please fill out the following form</h3>\n" + 
				"<form method=\"post\" action=\"newperson\" >");
		out.println("<label>First Name: </label>\n" + 
				"<input type=\"text\" name=\"fname\" id=\"fname\" placeholder=\"Your first name here\"" + 
				"value = \""+first_name+"\"/><br/><br/>\n\n" + 
				"<label>Last Name: </label>\n"+
				"<input type=\"text\" name=\"lname\" id=\"lname\" placeholder=\"Your last name here\"" +
				"value = \""+last_name+"\"/><br/><br/>");
		out.println("<label>Programming languages you're proficient in</label>\n" + 
				"<input type=\"checkbox\" name=\"languages\" value=\"Java\">Java\n" + 
				"<input type=\"checkbox\" name=\"languages\" value=\"Javascript\">Javascript\n" + 
				"<input type=\"checkbox\" name=\"languages\" value=\"Python\">Python\n" + 
				"<input type=\"checkbox\" name=\"languages\" value=\"C\">C\n" + 
				"<input type=\"checkbox\" name=\"languages\" value=\"C++\">C++\n" + 
				"<input type=\"checkbox\" name=\"languages\" value=\"C#\">C#\n<br/><br/>");
		out.println("<label>When are you available?</label>\n<input type=\"checkbox\" name=\"days\" value=\"M\">Monday\n" + 
				"<input type=\"checkbox\" name=\"days\" value=\"T\">Tuesday\n" + 
				"<input type=\"checkbox\" name=\"days\" value=\"W\">Wednesday\n" + 
				"<input type=\"checkbox\" name=\"days\" value=\"Th\">Thursday\n" + 
				"<input type=\"checkbox\" name=\"days\" value=\"F\">Friday\n" + 
				"<input type=\"checkbox\" name=\"days\" value=\"S\">Saturday\n" + 
				"<input type=\"checkbox\" name=\"days\" value=\"Su\">Sunday\n<br/><br/>");
		out.println("<label>Favorite movies:</label>\n" + 
				"<input type=\"checkbox\" name=\"movies\" value=\"Inception\">Inception\n" + 
				"<input type=\"checkbox\" name=\"movies\" value=\"Titanic\">Titanic\n" + 
				"<input type=\"checkbox\" name=\"movies\" value=\"The pursuit of happyness\">The pursuit of happyness\n" + 
				"<input type=\"checkbox\" name=\"movies\" value=\"2012\">2012\n" + 
				"<input type=\"checkbox\" name=\"movies\" value=\"The day after tomorrow\">The day after tomorrow\n<br/>");
		out.println("<input type=\"Submit\" value=\"Submit\"/><input type=\"hidden\" name=\"back\" value=\"WebApp1\" />\n" + 
				"</form>\n</body>\n</html>");
	}
}