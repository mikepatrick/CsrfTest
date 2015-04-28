<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<!-- Any JSPs that use the taglib will need this: -->
<%@ taglib uri="http://www.owasp.org/index.php/Category:OWASP_CSRFGuard_Project/Owasp.CsrfGuard.tld" prefix="csrf" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Successful Login</title>

<!-- You have provided a username and a password.  Welcome to the land of protected resources!

	 This page is set up to use a mixture of JavaScriptServlet and the tag library.

	 The synchronizer token will be appended to each url.  The Ajax call, which sends a request to
	 AjaxServlet, is also protected (the token ends up in the header).
-->

<script src="jquery-2.1.3.js" ></script>  <!--  let's make sure javascript files come through okay -->
<script src="JavaScriptServlet"></script> <!-- Fetch the javascript that will inject the token where needed -->
</head>
<body>
	<h2>Success page</h2>
	<div>
		<p>
			<a href="jsp2jsp.jsp?parm1=sampleparm1&parm2=sampleparm2" >Click here to be sent directly to another JSP.</a>
		</p>
		<p>
			<!-- 
				In this configuration, all three protections are turned on:
					Inject token into attributes (href, action, etc.).  Think GET.
					Inject token into hidden input field on all forms (POST).
					Protect AJAX requests, as well.
			
				Mix using the taglib and the javascript servlet with caution.
			 -->
			<a href="jsp2jsp.jsp?parm1=differentParm1&parm2=differentParm2" >Click here to go to the other JSP (no JSTL)</a>
		</p>
		<p> <!--  #scriptletsSuck #useJSTLerryday #JSTLsavedMyMarriage -->
			${domainObjects.name} -- ${domainObjects.address}
		</p>
		<div>
			${param.param1} - ${param.param2 }
		</div>
		<div>
			${ param.optMeIn }
		</div>
		<div>
			<input type="button" id="ajaxButton" value="sendAJAX" />
		</div>
		
		<div>
		
		<!--  
			When this select box is changed, a request to the server is fired off that we need to protect.
			The "delete page" option is especially worrisome, and a good example of why we frequently
			need to protect GET requests in Engage.
			
			In cases like these, we may have to manually go in and insert the token into each option value.
			Our other options might include sticking the token in a header, or trying to make our
			javascript DOM manipulation code account for select boxes that do this sort of thing.
			
			Fortunately, the CSRFGuard tag library makes this type of manual token insertion trivial.  Here,
			the token is included on the two DeletePage links.
		 -->
		<select name="pageoptions_1" id="pageselect_2060" onchange="P7_JumpMenu(this,1,2060)" class="select_style">
			<option value="PageList.jsp?id=1430055047101&amp;mag_code=BNA~window:Main">Please Select an Option</option>
			<option value="CopyPageStart.jsp?id=1430055047101&amp;mag_code=BNA&amp;page_id=2060~window:Main">Copy Page</option>
			<option value="DeletePage?page_id=2060&amp;mag_code=BNA&amp;<csrf:tokenname/>=<csrf:tokenvalue/>~window:Main">Delete Page</option>
			<option value="DeletePage?page_id=2060&amp;delete_prod_only=P&amp;mag_code=BNA&amp;<csrf:tokenname/>=<csrf:tokenvalue />~window:Main">Delete Page from Production Only</option>
			<option value="ListPageAttrib.jsp?id=1430055047101&amp;page_id=2060&amp;mag_code=BNA~window:Main">Edit Page Attributes</option>
			<option value="/servlet/OrdersGateway?cds_page_id=2060&amp;cds_mag_code=BNA~window:New">Preview Current Page</option>
			<option value="/pubshtml/N3/BNA/CopyOfcouponsub.html~window:New">Preview Stored HTML</option>
			<option value="UploadAssets.jsp?id=1430055047102&amp;mag_code=BNA&amp;curr_page_type=1&amp;upload_type=u&amp;page_id=2060&amp;old_file_name=CopyOfcouponsub.jsp~window:Main">UpdateExisting Page</option>
			<option value="ListPageHistory.jsp?id=1430055047102&amp;mag_code=BNA&amp;curr_page_type=1&amp;page_id=2060~window:Main">View			Page History</option>
		</select>
		</div>
	</div>
	<script>
	
	$("#ajaxButton").click(function() 
		{
		
		//Here we use JQuery to issue a simple AJAX request.  CSRFGuard hijacks the underlying XHR object's send()
		//method, and appends the token as a request header.  The filter will look for this header and validate the token.
		//In this sense, the call to JavaScriptServlet gives us AJAX protection "for free".
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
	
		// I ripped this javascript straight out of engage, to display some of the more
		// "creative" ways that state-changing actions are sent to the server.
		
		// In this case, we have a select box, each with the following function attached
		// to their onchange event.  In reality, the page contains many of these select boxes.
		
		// Note the lovely use of window.open() and eval().
		
		function P7_JumpMenu(selObj,restore,pageID){ //v1.3 by Project Seven
			var theFullString = selObj.options[selObj.selectedIndex].value;
			if (restore) {
				selObj.selectedIndex=0;
			}
			var theLength = theFullString.length;
			var endPos = theFullString.lastIndexOf("~");
			var theUrl, theTarget, theParent;
			var proceed;
		
			if (endPos > 0) {
				theUrl = theFullString.substring(0,endPos);
			} else {
				theUrl = theFullString;
			}
		
			endPos++
			if (endPos < theLength) {
				theTarget = theFullString.substring(endPos,theLength);
			} else {
				theTarget = "window:Main";
			}
			//
			if (theFullString.indexOf("DeletePage") > 0) {
				if (theFullString.indexOf("delete_prod_only") > 0){
					if (!delProdPage(pageID)) {
						return false;
					}			
				} else {
					if (!delPage(pageID)) {
						return false;
					}
				}
			}
			//
			if (theTarget == "window:New") {
				window.open(theUrl);
			} else if (theTarget == "window:Main") {
				eval("parent.location='" + theUrl + "'"); 
			} else {
				eval("parent.frames[\'" + theTarget + "\'].location='" + theUrl + "'");
			}
		}
	
	function delPage(pageId) {
	    var answer = confirm("Are you sure you want to delete this page?\nPage ID: " + pageId + "\nDeleting this page will cause the page to be deleted from PRODUCTION as well.");
	    //
	    if (answer == true) {
			return true;
	     }
	     else {
	        return false;
	     }
	}
	function delProdPage(pageId) {
	    var answer = confirm("Are you sure you want to delete this page?\nPage ID: " + pageId);
	    //
	    if (answer == true) {
			return true;
	     }
	     else {
	        return false;
	     }
	}	
	</script>
</body>
</html>