package client.tempuri;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.tempuri.IServiceProxy;
import java.lang.String;

public class Result extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		response.getWriter().println("<html><body><p>Use " + "POST instead!</p></body></html>");

	}
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");
		IServiceProxy clientIServiceProxy = new IServiceProxy();
		if(request.getParameter("Invoke").contains("Invoke")) {
			String method = (String) request.getParameter("method");
			int methodID = 0;
			if (method.equals(null))
				methodID = -1;
			if(methodID != -1) methodID = Integer.parseInt(method);

			boolean gotMethod = false;
			String city_name=  request.getParameter("city_name");
			String state_name=  request.getParameter("state_name");
			String getWeather;
			try {
				if(methodID==1) {
					gotMethod = true;
					if(!city_name.equals("") && (!state_name.equals(""))){
						getWeather = clientIServiceProxy.getWeather(city_name,state_name);
						if(getWeather != null){
						//out.print(getWeather);
							String[] jsonStr = getWeather.split(":");
							out.print("<table border=1><tr><td>Temperature in F and C</td>"
									+ "<td>Feels Like in T and C</td>"
									+ "<td>Relative Humidity</td>"
									+ "<td>Wind</td>"
									+ "<td>Wind Direction</td></tr>");
							for(int i=0;i<jsonStr.length;i++) {
								if(jsonStr[i].contains("temperature_string")) {
									out.print("<tr><td>"+jsonStr[i+1].split(",")[0]+"</td>");
								}
								if(jsonStr[i].contains("feelslike_string")) {
									out.print("<td>"+jsonStr[i+1].split(",")[0]+"</td>");
								}
								if(jsonStr[i].contains("relative_humidity")) {
									out.print("<td>"+jsonStr[i+1].split(",")[0]+"</td>");
								}
								if(jsonStr[i].contains("wind_string")) {
									out.print("<td>"+jsonStr[i+1].split(",")[0]+"</td>");
								}
								if(jsonStr[i].contains("wind_dir")) {
									out.print("<td>"+jsonStr[i+1].split(",")[0]+"</td></tr>");
								}

							}
							out.print("</table><br><br><a href=Index>Go Back</a><br><br>");
							out.print(getWeather);
						}else{
							out.print("Weather for "+city_name +" and "+state_name+ " not found");
						}
					}
				}
				if(methodID==2) {
					gotMethod = true;
					String getWeather_hourly = clientIServiceProxy.getWeather_hourly(city_name,state_name,true);
						if(getWeather_hourly != null){
							
							String[] jsonStr = getWeather_hourly.split(":");
							out.print("<table border=1><tr><td>Hour</td><td>Temperature in F and C</td>"
									+ "<td>Dew Point</td>"
									+ "<td>Prediction</td>"
									+ "</tr>");
							for(int i=0;i<jsonStr.length;i++) {
								if(jsonStr[i].contains("pretty")) {
									out.print("<tr><td>"+jsonStr[i+1].split(",")[0]+"</td>");
								}
								if(jsonStr[i].contains("temp")) {
									out.print("<td>"+jsonStr[i+2].split(",")[0]+", "+jsonStr[i+3].split(",")[0]+"</td>");
								}
								
								if(jsonStr[i].contains("dewpoint")) {
									out.print("<td>"+jsonStr[i+2].split(",")[0]+", "+jsonStr[i+3].split(",")[0]+"</td>");
								}
								if(jsonStr[i].contains("wx")) {
									out.print("<td>"+jsonStr[i+1].split(",")[0]+"</td></tr>");
								}				
							}
							out.print("</table><br><br><a href=Index>Go Back</a><br><br>");
							out.print(getWeather_hourly);

						}else{
							out.print("Hourly Weather for "+city_name +" and "+state_name+ " not found");
						}
					}
				
				if(methodID==3) {
					gotMethod = true;
					
						String getWeather_tenDays = clientIServiceProxy.getWeather_tenDays(city_name,state_name,true);
						if(getWeather_tenDays != null){
							String[] jsonStr = getWeather_tenDays.split(":");
							out.print("<table border=1><tr>"
									+ "<td>High</td>"
									+ "<td>Low</td>"
									+ "<td>Condition</td></tr>");
							for(int i=0;i<jsonStr.length;i++) {
								
								if(jsonStr[i].contains("high")) {
									//out.print("<td>"+jsonStr[i+1]+jsonStr[i+2].split(",")[0]+"</td>");
									out.print("<td>"+jsonStr[i+1].split(",")[0]+jsonStr[i+2].split(",")[0]+"</td>");
								}
								if(jsonStr[i].contains("low")) {
									out.print("<td>"+jsonStr[i+1]+jsonStr[i+2].split(",")[0]+"</td>");
								}
								if(jsonStr[i].contains("conditions")) {
									out.print("<td>"+jsonStr[i+1].split(",")[0]+"</td></tr>");
								}
								

							}
							out.print("</table><br><br><a href=Index>Go Back</a><br><br>");
							out.print(getWeather_tenDays);

						}else{
							out.print("Ten Day Weather for "+city_name +" and "+state_name+ " not found");
						}

					}
				}
			
			catch (Exception e) { 
				e.printStackTrace();
				out.print("Error in parsing JSON");
			}
			
			if(!gotMethod){
				out.print("NA");
			}   
		}
	}
}