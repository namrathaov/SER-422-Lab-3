/*
 * Lab4Servlet.java
 *
 * Copyright:  2008 Kevin A. Gary All Rights Reserved
 *
 */
package edu.asupoly.ser422.lab4;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.*;
import javax.servlet.http.*;
import java.lang.Exception;
/**
 * @author Kevin Gary
 *
 */
@SuppressWarnings("serial")
public class Lab4Servlet extends HttpServlet {

    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public void doGet(HttpServletRequest req, HttpServletResponse res)
	throws ServletException, IOException {
	StringBuffer pageBuf = new StringBuffer();
	double grade;
	String year = req.getParameter("year");
	String subject = req.getParameter("subject");
	
	if (year != null) {
	    pageBuf.append("<br/>Year: " + year);
	}
	if (subject != null) {
	    pageBuf.append("<br/>Subject: " + subject);
	}
	
	edu.asupoly.ser422.lab4.Lab4ServicePortTypeProxy service = new edu.asupoly.ser422.lab4.Lab4ServicePortTypeProxy();
	
	if (service == null) {
	    pageBuf.append("\tSERVICE NOT AVAILABLE");
	} else {
	    grade = service.calculateGrade(year, subject);
	    pageBuf.append("\n\t<br/>Grade: " + grade);
	    pageBuf.append("\n\t<br/> Letter: " + service.mapToLetterGrade(grade));
	}
	
	// some generic setup - our content type and output stream
	res.setContentType("text/plain");
	PrintWriter out = res.getWriter();

	String[] subjects = service.getSubjects();
	for(int i = 0; i < subjects.length; i++ ){
		pageBuf.append("\n"+subjects[i]);
	}
	out.println(pageBuf.toString());

    }
}
