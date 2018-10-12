package edu.asupoly.ser422.lab4;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import edu.asupoly.ser422.lab4.Lab4Service;
public class Lab4Subjects extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
			res.setContentType("text/html");
			StringBuffer pageBuf = new StringBuffer();
			PrintWriter out = res.getWriter();
			
			Lab4Service service = null;
			try {
				service = Lab4Service.getService();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (service == null) {
			    pageBuf.append("\tSERVICE NOT AVAILABLE");
			} else {
			    String[] subjects = service.getSubjects();
			    out.print("<html><head><title>Subjects</title></head><body><ul>");
			   for(String sub: subjects){
				   System.out.println(sub);
			      out.print("<li>"+sub+"</li>");
			    
			    }
			    out.print("<ul>");
			}
			
			out.println(pageBuf.toString());
			out.print("</body></html>");
		    }
}
