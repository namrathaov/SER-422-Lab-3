<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
 pageEncoding="ISO-8859-1"%>
<% 
 String name="",role="";
 Cookie[] cookies=request.getCookies();
 if(cookies!=null)
	{
		for(int i=0;i<cookies.length;i++)
		{
			if(cookies[i].getName().equals("lab2Cookie_name"))
			{
				name = cookies[i].getValue();
			}
			if(cookies[i].getName().equals("lab2Cookie_role"))
			{
				role = cookies[i].getValue();
			}
		}
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Login</title>
</head>
<body>
<div style="text-align:center"><h1>Login to New News </h1> </div>
<br>
<form name="form" action="LoginServlet" method="post">
<table align="center">
 <tr>
 <td>Username</td>
 <td><input type="text" name="username" value="<%=name%>"/></td>
 </tr>
 <tr>
 <td>Password</td>
 <td><input type="password" name="password" /></td>
 </tr>
 <tr><td><input type="hidden" name="role" value="<%=role%>" /></td></tr>
 <tr> 
 <td><span style="color:red"><%=(request.getAttribute("errMessage") == null) ? ""
 : request.getAttribute("errMessage")%></span></td>
 </tr>
 <tr>
 <td></td>
 <td><input type="submit" name="submit" value="Login"></input></td>
 </tr>
</table>
</form>
</body>
</html>