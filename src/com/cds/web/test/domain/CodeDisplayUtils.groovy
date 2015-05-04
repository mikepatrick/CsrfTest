package com.cds.web.test.domain

import groovy.util.logging.Log

import org.apache.commons.lang3.StringEscapeUtils

@Log
class CodeDisplayUtils {

	static String getOption()
	{
		String html = """<option value="DeletePage?page_id=2060&amp;mag_code=BNA&amp;<csrf:tokenname/>=<csrf:tokenvalue/>~window:Main">Delete Page</option>"""
		StringEscapeUtils.escapeHtml4 html
	}
	
	static String getJsInclude()
	{
		 StringEscapeUtils.escapeHtml4 """<script src="JavaScriptServlet"></script>"""
	}
	
	static String getJspCode()
	{
		String jsp = """
<% if ((!currPage.isPending()) && (!currPage.isErrored())) { %>
	<% if (currPage.getOffsiteInd().equalsIgnoreCase("N")) { %>	
		<% if (currPage.isHtmlStored()) {
			String previewHtmlUrl	= "/" + jsp.getProperty(AdminToolConstants.PROPS_HTML_DIR)
				+ "/" + admin.getCurrentPubCode()
				+ "/" + admin.getCurrentMagCode()
				+ "/" + currPage.getPageName().substring(0, currPage.getPageName().indexOf(".jsp"))
				+ currPage.getStoredHTMLExtension()
				;
		%>
			<option value="<%= SecurityUtil.encodeHTML(previewHtmlUrl) %>&amp;<csrf:tokenname />=<csrf:tokenvalue/>~window:New">
				Preview Stored HTML
			</option>
		<% } %>
	<% } %>
<% } %>
		"""
		StringEscapeUtils.escapeHtml4 jsp
	}
	
	static String getWebXml()
	{
		String xml = """
   <!-- CSRFGuard context parameters, session listener, and filter -->
       <listener>
            <listener-class>org.owasp.csrfguard.CsrfGuardServletContextListener</listener-class>
       </listener>
       <listener>
            <listener-class>org.owasp.csrfguard.CsrfGuardHttpSessionListener</listener-class>
       </listener>
       <context-param>
             <param-name>Owasp.CsrfGuard.Config</param-name>
             <param-value>/com/cds/web/test/config/CsrfGuard.properties</param-value>
       </context-param>
       <context-param>
             <param-name>Owasp.CsrfGuard.Config.Print</param-name>
             <param-value>false</param-value>
       </context-param>    
       <context-param>
       		<param-name>Owasp.CsrfGuard.overlay.properties</param-name>
       		<param-value>/com/cds/web/test/config/CsrfGuard.properties</param-value>
       </context-param>
       
    <!-- Declare and map CSRF enforcement mechanism -->
	<filter>
		<filter-name>CSRFGuard</filter-name>
		<filter-class>org.owasp.csrfguard.CsrfGuardFilter</filter-class>
	</filter>
	<filter-mapping>
		<filter-name>CSRFGuard</filter-name> 
		<url-pattern>/*</url-pattern>
	</filter-mapping>       
	
	
	<!--  Servlet for javascript injection of token into html/jsp -->
	<servlet>
		 <servlet-name>JavaScriptServlet</servlet-name>
		 <servlet-class>org.owasp.csrfguard.servlet.JavaScriptServlet</servlet-class>
		 <init-param>
			 <param-name>source-file</param-name>
			 <param-value>/WEB-INF/csrfguard.js</param-value>
		 </init-param>
		 <init-param>
			 <param-name>inject-into-forms</param-name>
			 <param-value>true</param-value>
		 </init-param>
		 <init-param>
			 <param-name>inject-into-attributes</param-name>
			 <param-value>true</param-value>
		 </init-param>
		<init-param>
			 <param-name>domain-strict</param-name>
			 <param-value>false</param-value>
		</init-param>
		<init-param>
			 <param-name>referer-pattern</param-name>
			 <param-value>.*localhost.*</param-value>
		</init-param>
	</servlet>	
	<servlet-mapping>
		 <servlet-name>JavaScriptServlet</servlet-name>
		 <url-pattern>/JavaScriptServlet</url-pattern>
	</servlet-mapping>
"""
		StringEscapeUtils.escapeXml xml
	}
	
	static String getDeletePageHtml(String pageId)
	{
		String html = """
<html>
	<body>
		<h2>You have deleted page $pageId!</h2><br/>
		<a href='welcome'>Back to login</a>
	</body>
</html>

"""
		StringEscapeUtils.escapeHtml4 html
	}
	
	static String getJstlDemo()
	{
		String code = """
<body>
<%
	// You don't need to do any of this with #EL:
	String parm1 = request.getParameter("parm1");
	String parm2 = request.getParameter("parm2");

%>
	<div>
		<!-- Again, this sucks: -->
		<h3><% out.write(parm1); %></h3>
		<br/>
		<h3><% out.write(parm2); %></h3>
		<div>
			<!--  And this sucks way less: -->
			\${param.parm1}
		</div>
	</div>

"""
		String escaped = StringEscapeUtils.escapeHtml4 code
		return escaped
	}

	static String getEngageCode()
	{
		String code = """
	public void sendToEo() throws Exception {

		String redirectEOPath = "/servlet/Login?toggle=Y&";

		String key = null;
		String value = null;
		StringBuffer urlKeyValuePairs = new StringBuffer("?");
		if (requestMap.size() > 0 && requestMap.containsKey("redirectTo")) {
			for (Iterator<?> i = requestMap.keySet().iterator(); i.hasNext(); ) {
				key = (String) i.next();
				value = FormUtil.readString(requestMap, key);
				if ("redirectTo".equals(key)) {
					// prepend the redirect to
					urlKeyValuePairs.insert(0, key + "=" + value);
				} else {
					if ("magCode".equals(key)) {
						// mag code gets sent to both the login servlet and the redirect string
						redirectEOPath += "mag_code=" + FormUtil.readString(requestMap, "magCode") + "&";
						urlKeyValuePairs.append("mag_code=" + value);
					} else if ("pubCode".equals(key)) {
						// pub code also gets sent to both the login servlet and the redirect string
						redirectEOPath += "pub_code=" + FormUtil.readString(requestMap, "pubCode") + "&";
						urlKeyValuePairs.append("pub_code=" + value);
					} else if ("pageId".equals(key)) {
						// page id 
						redirectEOPath += "page_id=" + FormUtil.readString(requestMap, "pageId") + "&";
						urlKeyValuePairs.append("page_id=" + value);
					} else {
						if ("cds_x_id".equals(key)) //This shouldn't be hardcoded... bad programmer.
						{
							redirectEOPath += "cds_x_id=" + FormUtil.readString(requestMap, "cds_x_id") + "&"; 
						}
							urlKeyValuePairs.append(key + "=" + value);
						
					}
					if (i.hasNext()) {
						urlKeyValuePairs.append("%26");
					}
				}
			}
		} else {
			throw new Exception("Invalid URL.");
		}
		dispatch.redirect(redirectEOPath + urlKeyValuePairs.toString());
	}

"""
		String escaped = StringEscapeUtils.escapeHtml4(code)
		return escaped
	}
}
