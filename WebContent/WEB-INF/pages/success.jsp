<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib uri="http://www.owasp.org/index.php/Category:OWASP_CSRFGuard_Project/Owasp.CsrfGuard.tld" prefix="csrf" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Successful Login</title>
<!-- <script src="jquery-2.1.3.js" /> --> <!-- SUNUVABITCH fetching this breaks everything--> 
<!-- <script src="scripts/csrfguard.js"></script> -->
<script src="jquery-2.1.3.js" ></script>
<script src="JavaScriptServlet"></script>
</head>
<body>
	
	<h2>Success page</h2>
	<div>
		<p>
			<a href="jsp2jsp.jsp?parm1=sampleparm1&parm2=sampleparm2" >Click here to be sent directly to another JSP.</a>
		</p>
		<p>
			<a href="jsp2jsp.jsp?parm1=differentParm1&parm2=differentParm2" >Click here to go to the other JSP (no JSTL)</a>
		</p>
		<p>
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
		<div>
		
		</div>
	</div>
	<script>
	$("#ajaxButton").click(function() 
		{
			$.ajax({url: "ajaxCall", 
					htmlOption: "optionA", 
					success: function(ajaxData) 
					{
						alert(ajaxData);
					}, 
					failure: function()
					{
						alert("Oh, no.");						
					}
					
				   })
		})
	
	</script>

</body>
</html>