<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		HttpSession sessionHome = request.getSession(false);
		if (sessionHome != null) {
			String name = (String) sessionHome.getAttribute("name");
			String role = (String) sessionHome.getAttribute("role");
			System.out.println(name);
			System.out.println("Inside home");

			if (role != null && role.equals("reporter")) {
				System.out.println("Inside reporter role");
				out.print("Welcome " + name + "\n" + sessionHome.getAttribute("role"));
			} else if (role != null && role.equals("subscriber")) {
				System.out.println("subscriber " + sessionHome.getAttribute("role"));
				out.print("Welcome " + name + "\n" + sessionHome.getAttribute("role"));
			}
		} else {
			System.out.println("Invalid Session");
		}
	%>
	<form action="CreateNewStory" method="post">
		<br> <input type="text" name="title"
			placeholder="Enter the title here"><br> <br> <br>
		<textarea name="story" rows="10" cols="60"
			placeholder="Start your story.."></textarea>
		<br> <br> <br> <select name="visibility">
			<option value="Public">Public</option>
			<option value="Reporter">Subscriber only</option>
		</select><br> <br>
		<%=(request.getAttribute("errMessage") == null) ? "" : request.getAttribute("errMessage")%>
		<input type="submit" name="SubmitStory" value="Submit Story">
		<br><br> <a href="LogoutServlet1">Logout</a>
	</form>
</body>
</html>