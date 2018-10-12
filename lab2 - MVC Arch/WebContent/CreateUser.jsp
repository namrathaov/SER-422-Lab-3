<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%
String name=request.getParameter("username");
String password=request.getParameter("password");
System.out.println("Password "+password);%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Create New User</title>
</head>
<body>
	<form action="CreateUserServlet" method="post">
		<table align="center">
			<tr>
				<td>Username</td>
				<td><input type="text" name="username" value="<%=name%>" /></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="password" value="<%=password%>"/></td>
			</tr>
			<tr>
				<td><span style="color: red"><%=(request.getAttribute("errMessage") == null) ? "" : request.getAttribute("errMessage")%></span></td>
			</tr>
			<tr>
				<td><select name="role">
						<option value="subscriber">Subscriber</option>
						<option value="reporter">Reporter</option>
				</select></td>
			</tr>
			<tr>
				<td></td>
				<td><input type="submit" name="submit" value="Create User"></input></td>
			</tr>
		</table>
	</form>
</body>
</html>