/*
 * Lab5Servlet.java
 *
 * Copyright:  2008 Kevin A. Gary All Rights Reserved
 *
 */
package edu.asupoly.ser422.lab5;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import org.json.simple.JSONObject;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/**
 * @author Kevin Gary
 *
 */
@SuppressWarnings("serial")
public class Lab5Servlet extends HttpServlet {

	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		res.setContentType("text/html");
		String year,subject;
		PrintWriter out = res.getWriter();
		WebTarget msCalc,msMapGrade;
		String grade;
		Client calc;
		Invocation.Builder buildHitURLCalc,buildHitURLMap;
		Properties props = new Properties();
		String __calcURL    = null;
		String __mapURL   = null;
		
		try {
			InputStream propFile = this.getClass().getClassLoader().getResourceAsStream("lab5ms.properties");
			props.load(propFile);
			propFile.close();
		}
		catch (IOException ie) {
			ie.printStackTrace();
			out.println("Could not read properties file<br>");
		}
		__calcURL = props.getProperty("lab5calc.url");
		__mapURL = props.getProperty("lab5map.url");
		try {
			calc = ClientBuilder.newClient();
			year = req.getParameter("year");
			subject = req.getParameter("subject");
			if (year != null && !year.trim().isEmpty()) {
				out.println("Year: "+req.getParameter("year")+"<br>");
			}
			if (subject != null && !subject.trim().isEmpty()) {
				out.println("Subject: "+req.getParameter("subject")+"<br>");
			}
			msCalc = calc.target(__calcURL)
					.queryParam("year", year).queryParam("subject", subject);
			buildHitURLCalc = msCalc.request(MediaType.APPLICATION_JSON)
					.accept(MediaType.APPLICATION_JSON);
			Response getCalc = buildHitURLCalc.get();

			if (getCalc.getStatus() != 200) {
				out.println("Bad request! <br>");
			}
			int gradeflag=1;
			if(getCalc.getStatus() == 200) {
			JSONObject array = getCalc.readEntity(JSONObject.class);
			grade=array.get("grade").toString();
			if(grade.contains("0.0")) {
				out.println("Grade unavailable<br>");
				gradeflag=0;
			}
			else
			out.println("Grade : "+grade+"<br>");
				if(gradeflag==1) {
				msMapGrade = calc.target(__mapURL)
						.queryParam("grade",grade);
				buildHitURLMap = msMapGrade.request(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON);
				Response letterResponse = buildHitURLMap.get();
				if (letterResponse.getStatus() != 200) {
					out.println("Bad Request!<br>");
				}
				else {
					array = letterResponse.readEntity(JSONObject.class);
					out.println("Letter Grade: "+array.get("lettergrade").toString()+"<br>");
				}
			}
			}
		}
		catch (Exception e) {
			out.println("Could not connect to Microservices!");
		}
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doGet(req, res);
	}
}
