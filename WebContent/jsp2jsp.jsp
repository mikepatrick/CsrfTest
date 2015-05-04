<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="com.cds.web.test.domain.CodeDisplayUtils" %>
<%@ taglib uri="http://www.owasp.org/index.php/Category:OWASP_CSRFGuard_Project/Owasp.CsrfGuard.tld" prefix="csrf" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>JSP to JSP</title>
<!-- <script src="JavaScriptServlet"></script> -->

<!-- 
	Here again, we have a choice.  Use the tag library (you have to check the damn JSP in anyway),
	or use the javascript DOM manipulation.
	
 -->
</head>
<body>
<script src="https://google-code-prettify.googlecode.com/svn/loader/run_prettify.js"></script>
<%
	// You don't need to do any of this with #JSTL:
	String parm1 = request.getParameter("parm1");
	String parm2 = request.getParameter("parm2");

%>
	<div>
		<!-- Again, this sucks: -->
		<h3><% out.write(parm1); %> - <% out.write(parm2); %></h3>
		<div>
			<!--  And this sucks way less: -->
			${param.parm1} - ${param.parm2}
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
	<hr/>
	<div>
		<pre class="prettyprint" style="border:none!important;"><% out.write(CodeDisplayUtils.getJstlDemo()); %></pre>
	</div>
	<hr/>
	<div>
		There are a few places where java code outside of JSPs needs to be touched (from UserController):
	</div>
	<div>
		<pre class="prettyprint" style="border:none!important;"><% out.write(CodeDisplayUtils.getEngageCode()); %></pre>
	</div>
</body>
</html>