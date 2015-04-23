<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.owasp.org/index.php/Category:OWASP_CSRFGuard_Project/Owasp.CsrfGuard.tld" prefix="csrf" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Successful Login</title>
<!-- 

You have provided a username and a password.  Welcome to the land of protected resources!

This page is set up to use the JavaScriptServlet, rather than the tag library.

The synchronizer token will be appended to each url.  The Ajax call, which sends a request to
AjaxServlet, is also protected (the token ends up in the header).

 -->


<script src="jquery-2.1.3.js" ></script> <!--  make sure javascript files come through okay -->
<script src="JavaScriptServlet"></script>
</head>
<body>
	
	<h2>Success page</h2>
	<div>
		<p>
			<a href="jsp2jsp.jsp?parm1=sampleparm1&parm2=sampleparm2" >Click here to be sent directly to another JSP.</a>
		</p>
		<p>
			<!-- 
				Here we include the token using JSTL.
				
				The javascript that appends the token to each URL isn't smart enough to check and see if the URL already
				has a token.  So we'll get two CSRF tokens on this link.  Harmless, but sloppy and butt-ugly.  
				
				In this configuration, all three protections are turned on:
					Inject token into attributes (href, action, etc.).  Think GET.
					Inject token into hidden input field on all forms (POST).
					Protect AJAX requests, as well.
					
				
			 -->
			<a href="jsp2jsp.jsp?parm1=differentParm1&parm2=differentParm2&<csrf:tokenname />=<csrf:tokenvalue />" >Click here to go to the other JSP (no JSTL)</a>
		</p>
		<p> <!--  #scriptletsSuck #useJSTL #JSTLsavedMyMarriage -->
			${domainObjects.name} -- ${domainObjects.address}
		</p>
		<div>
			${param.param1} - ${param.param2 }
		</div>
		<div>
			${ param.optMeIn }
		</div>
		<p>
			<a href="postTest" >Click here to call a servlet action that will put some objects in the model.</a>
		</p>
		<div>
			<input type="button" id="ajaxButton" value="sendAJAX" />
		</div>

	</div>
	<script>
	
	$("#ajaxButton").click(function() 
		{
		
		//Here we use JQuery to issue an AJAX request.  CSRFGuard hijacks the underlying XHR object's send()
		//method, and appends the token as a request header.  The filter will look for this header.
		
			$.ajax({url: "ajaxCall", 
					htmlOption: "optionA", 
					success: function(ajaxData) 
					{
						alert(ajaxData);
					}, 
					failure: function()
					{
						alert("Oh, no.  All my base are belong to them.");						
					}
					
				   })
		})
	
	</script>

</body>
</html>