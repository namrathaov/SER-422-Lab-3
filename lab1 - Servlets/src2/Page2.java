

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
 * Servlet implementation class Page2
 */
@WebServlet("/Page2")
public class Page2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Page2() {
        super();
        // TODO Auto-generated constructor stub
    }

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
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		String formFName=request.getParameter("fname");
		String formLName=request.getParameter("lname");
		Cookie firstName = new Cookie("lab1Cookie_first_name", formFName);
		Cookie lastName= new Cookie("lab1Cookie_last_name", formLName);
		PrintWriter out = response.getWriter();
		firstName.setMaxAge(60*60*24);
		lastName.setMaxAge(60*60*24);
		response.addCookie(firstName);
		response.addCookie(lastName);
		if(formFName!=null)
			session.setAttribute("first_name",formFName);
		if(formLName!= null)
			session.setAttribute("last_name",formLName);
		String language="";
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (cookies[i].getName().equals("lab1Cookie_languages")) {
					language = cookies[i].getValue();					
				}
			}
		}
		String[] progLang= {"Java","Perl","Python","C","PHP","R"};
		out.println("<!DOCTYPE html>");
		out.println("<html xmlns=\"http://www.w3.org/1999/html\">\n<body>\n" + 
				"<h3>Please fill out the following form</h3>\n" + 
				"<form method=\"post\" action=\"Page3\" >");
		out.println(" <label>Programming languages you're proficient in</label><br>");
		for(String lang : progLang) {
			if(language.contains(lang))
				out.println("<input type=\"checkbox\" name=\"languages\" value=\""+lang+"\" checked/>"+lang+"<br>");
			else
				out.println("<input type=\"checkbox\" name=\"languages\" value=\""+lang+"\"/>"+lang+"<br>");
		}
		out.println("<input type=\"Submit\" value=\"Submit\"/><input type=\\\"hidden\\\" name=\\\"reset\\\" value=\\\"WebApp1\\\" />\\\n</form>\n</body>\n</html>");
		}
}