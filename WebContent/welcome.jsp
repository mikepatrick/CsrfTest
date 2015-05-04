<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="com.cds.web.test.domain.CodeDisplayUtils" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ taglib uri="http://www.owasp.org/index.php/Category:OWASP_CSRFGuard_Project/Owasp.CsrfGuard.tld" prefix="csrf" %>
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

<script src="https://google-code-prettify.googlecode.com/svn/loader/run_prettify.js"></script>
<script src="JavaScriptServlet"></script>
	<div>
		<h2>Login:</h2>
	</div>
	<div>
		<p>
			This page is declared unprotected in the CsrfGuard.properties file.  It does not require a token to visit.
		</p>
		<form name="loginForm" id="loginForm" action="process" method="post" >
			<input type="text" id="username" name="username" value="Username"><br />
			<input type="text" id="password" name="password" value="password"><br />
			<input type="submit">
		</form><br/>
		<div>
			When this page is loaded, a few things happen:
			
			<ul>
				<li>A GET request is sent to JavaScriptServlet, which returns the javascript we need to inject the token.  Currently, a servlet generates the javascript, but it could be served as a static resource, as well.</li>
				<li>At token injection time, a POST request is sent to JavaScriptServlet, which returns the token only.</li>
				<li>The javascript executes, injecting hidden input elements into forms, and appending the token to URLs where appropriate.</li>
			</ul>
		</div>
		<br /><br />
		<div>
			A second form to verify that the hidden input is added to all forms.<br/>
		</div>
		<form name="loginForm2" id="loginForm2" action="process" method="post" >
			<input type="text" id="username" name="username" value="Username"><br />
			<input type="text" id="password" name="password" value="password"><br />
			<input type="submit">
		</form><br/>
	</div>
	<div>
		The only code added to the JSP:<br/>
		<pre class="prettyprint" style="border:none!important;">${jsInclude}</pre>
	</div>
	<div>
		<p>Some internal and external links:</p>
		<a href="jsp2jsp.jsp?parm1=sampleparm1&parm2=sampleparm2">Whut</a><br/>
		<a href="https://www.google.com">External link to google</a><br/>
		<a href="jsp2jsp.jsp">Fictional Internal link</a><br/>
		<a href="somejsp.jsp?parm1=sampleparm1&parm2=sampleparm2">Is this a bug?</a>
	</div>
</body>
</html>