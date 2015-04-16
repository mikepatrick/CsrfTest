<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.owasp.org/index.php/Category:OWASP_CSRFGuard_Project/Owasp.CsrfGuard.tld" prefix="csrf" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>JSP to JSP</title>
<!-- <script src="JavaScriptServlet"></script> -->
</head>
<body>
<%
	String parm1 = request.getParameter("parm1");
	String parm2 = request.getParameter("parm2");

%>
	<div>
		<h2><% out.write(parm1); %></h2>
		<br/>
		<h3><% out.write(parm2); %></h3>
		<div>
			${param.parm1}
		</div>

	</div>
	
	<div>
		<div>
			<a href="welcome">Back to login page</a>
		</div>
		<form name="internalForm" action="process?username=abc&password=123" method="POST" >
			<input type="checkbox" name="optMeIn" />Opt me IN!<br />
			<input type="hidden" name="<csrf:tokenname/>" value="<csrf:tokenvalue/>" />
			<input type="submit" />
		
		</form>
	
	</div>
</body>
</html>