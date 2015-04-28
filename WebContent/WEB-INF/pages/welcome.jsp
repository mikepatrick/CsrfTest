<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>My Test Login Page</title>

</head>
<body>
<!-- 
Currently, this page is using the supplied tag library to inject the token.

Both the login page (Login.jsp) and the login "action" that redirects to the login page
(Login) are declared unprotected in the properties file.  The login page and the generic
error page are the only unprotected resources.

Submission of this form sends a request to the ProcessServlet, which will render success.jsp.

Alternatively, we could use the call to JavaScriptServlet below, which would append the 
hidden input via javascript after the page has loaded.

 -->
<script src="JavaScriptServlet"></script>
	<div>
		<h2>Login:</h2>
	</div>
	<form name="loginForm" id="loginForm" action="process" method="post" >
		<input type="text" id="username" name="username" value="Username"><br />
		<input type="text" id="password" name="password" value="password"><br />
		<input type="submit">
	</form>
	<br /><br />
	<form name="loginForm2" id="loginForm2" action="process" method="post" >
		<input type="text" id="username" name="username" value="Username"><br />
		<input type="text" id="password" name="password" value="password"><br />
		<input type="submit">
	</form>
</body>
</html>