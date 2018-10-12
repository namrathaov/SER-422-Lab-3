package edu.asupoly.ser422.lab5;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import javax.ws.rs.core.Response;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;
import org.json.simple.parser.JSONParser;

@Path("")
@Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})

public class Lab5Map {
	@GET
	public Response getGrade(@QueryParam("grade") double grade) {
		try {
			Lab5Service service =Lab5Service.getService();
			JSONObject array = new JSONObject();
			array.put("lettergrade",String.valueOf(service.mapToLetterGrade(grade)));
			
			return Response.status(200).type(MediaType.APPLICATION_JSON).entity(array).build();	
		} 
		catch (Exception e) {
			Response.status(404).type(MediaType.APPLICATION_JSON).build();	
			e.printStackTrace();
		}
		return Response.status(404).type(MediaType.APPLICATION_JSON).build();
	}
}
