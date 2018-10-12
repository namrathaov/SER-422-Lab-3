<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Home Page</title>
</head>
<body>
<h2>Home Page!</h2>
	<%
		HttpSession sessionHome = request.getSession(false);
		if (sessionHome != null) {
			String name = (String) sessionHome.getAttribute("name");
			String role = (String) sessionHome.getAttribute("role");
			System.out.println(name);
			System.out.println("Inside home"+role);

			if (role != null && role.equals("reporter")) {
				request.getRequestDispatcher("ReporterServlet").include(request, response);
				System.out.println("Inside reporter role");
				out.print("Welcome " + name + "\n" + sessionHome.getAttribute("role"));
			} else if (role != null && role.equals("subscriber")) {
				request.getRequestDispatcher("Subscriber").include(request, response);
				System.out.println("subscriber " + sessionHome.getAttribute("role"));
				out.print("Welcome " + name + "\n" + sessionHome.getAttribute("role"));
			}
		} else {
			System.out.println("Invalid Session");
		}
	%>
	<br><a href="LogoutServlet1">Logout</a>
</body>
</html>