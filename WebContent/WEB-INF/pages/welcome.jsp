<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Test Login Page</title>

</head>
<body>
<!-- <script src="JavaScriptServlet"></script> -->
	<div>
		<h2>Login:</h2>
	</div>
	<form name="loginForm" id="loginForm" action="process" method="post" >
		<input type="text" id="username" name="username" value="Username"><br />
		<input type="text" id="password" name="password" value="password"><br />
		<input type="submit">
	</form>
	
</body>
</html>