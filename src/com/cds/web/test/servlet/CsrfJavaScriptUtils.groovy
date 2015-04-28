package com.cds.web.test.servlet

import groovy.transform.CompileStatic

import java.util.regex.Pattern

import javax.servlet.ServletConfig
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession

import org.owasp.csrfguard.CsrfGuard
import org.owasp.csrfguard.CsrfGuardServletContextListener
import org.owasp.csrfguard.log.LogLevel
import org.owasp.csrfguard.util.CsrfGuardUtils
import org.owasp.csrfguard.util.Strings
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource

@CompileStatic
class CsrfJavaScriptUtils {
		
		private static final long serialVersionUID = -1459584282530150483L;
		
		private static final String TOKEN_NAME_IDENTIFIER = "%TOKEN_NAME%";
		private static final String TOKEN_VALUE_IDENTIFIER = "%TOKEN_VALUE%";
		private static final String DOMAIN_ORIGIN_IDENTIFIER = "%DOMAIN_ORIGIN%";
		private static final String DOMAIN_STRICT_IDENTIFIER = "%DOMAIN_STRICT%";
		private static final String INJECT_INTO_XHR_IDENTIFIER = "%INJECT_XHR%";
		private static final String INJECT_INTO_FORMS_IDENTIFIER = "%INJECT_FORMS%";
		private static final String INJECT_GET_FORMS_IDENTIFIER = "%INJECT_GET_FORMS%";
		private static final String INJECT_FORM_ATTRIBUTES_IDENTIFIER = "%INJECT_FORM_ATTRIBUTES%";
		private static final String INJECT_INTO_ATTRIBUTES_IDENTIFIER = "%INJECT_ATTRIBUTES%";
		private static final String CONTEXT_PATH_IDENTIFIER = "%CONTEXT_PATH%";
		private static final String SERVLET_PATH_IDENTIFIER = "%SERVLET_PATH%";
		private static final String X_REQUESTED_WITH_IDENTIFIER = "%X_REQUESTED_WITH%";
		private static final String TOKENS_PER_PAGE_IDENTIFIER = "%TOKENS_PER_PAGE%";
		private static ServletConfig servletConfig = null;
	
		public static ServletConfig getStaticServletConfig() {
			return servletConfig;
		}
		
		public void init(ServletConfig theServletConfig) {
		  servletConfig = theServletConfig;
		  //print again since it might change based on servlet config of javascript servlet
		  CsrfGuardServletContextListener.printConfigIfConfigured(servletConfig.getServletContext(),
				  "Printing properties after Javascript servlet, note, the javascript properties have now been initialized: ");
		}
	
		public static String doGet(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
			String refererHeader = request.getHeader("referer");
			boolean hasError = false;
			CsrfGuard csrfGuard = CsrfGuard.getInstance()
			Pattern javascriptRefererPattern = CsrfGuard.getInstance().getJavascriptRefererPattern();
			if(refererHeader != null && !javascriptRefererPattern?.matcher(refererHeader)?.matches()) {
				CsrfGuard.getInstance().getLogger().log(LogLevel.Error, "Referer domain " + refererHeader + " does not match regex: " + javascriptRefererPattern.pattern());
				response.sendError(404);
				return "404"
				hasError = true;
			}
				
			if (refererHeader != null && CsrfGuard.getInstance().isJavascriptRefererMatchDomain()) {
				//this is something like http://something.com/path or https://something.com/path
				String url = request.getRequestURL().toString();
				String requestProtocolAndDomain = CsrfGuardUtils.httpProtocolAndDomain(url);
				String refererProtocolAndDomain = CsrfGuardUtils.httpProtocolAndDomain(refererHeader);
				if (!refererProtocolAndDomain.equals(requestProtocolAndDomain)) {
					CsrfGuard.getInstance().getLogger().log(LogLevel.Error, "Referer domain " + refererHeader + " does not match request domain: " + url);
					hasError = true;
					return "404"
					response.sendError(404);
				}
				
			}
			if (!hasError) {
				
				//save this path so javascript is whitelisted
				String javascriptPath = request.getContextPath() + request.getServletPath();
				
				//dont know why there would be more than one... hmmm
				if (javascriptUris.size() < 100) {
					javascriptUris.add(javascriptPath);
				}
				
				return writeJavaScript(request, response);
			}
		}
	
		/**
		 * whitelist the javascript servlet from csrf errors
		 * @return the javascriptUris
		 */
		public static Set<String> getJavascriptUris() {
			return javascriptUris;
		}
	
		/** whitelist the javascript servlet from csrf errors */
		private static Set<String> javascriptUris = new HashSet<String>();
		
	
		public static String doPost(final HttpServletRequest request, final HttpServletResponse response) throws IOException {
			CsrfGuard csrfGuard = CsrfGuard.getInstance();
			String isFetchCsrfToken = request.getHeader("FETCH-CSRF-TOKEN");
			
			if (csrfGuard != null && isFetchCsrfToken != null){
				return fetchCsrfToken(request, response);
			} else {
				if (csrfGuard != null && csrfGuard.isTokenPerPageEnabled()) {
					return writePageTokens(request, response);
				} else {
					//response.sendError(404);
					return "404"
				}
			}
		}
	
		private static String fetchCsrfToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
			HttpSession session = request.getSession(true);
			@SuppressWarnings("unchecked")
			CsrfGuard csrfGuard = CsrfGuard.getInstance();
			String token_name = csrfGuard.getTokenName();
			String token_value = (String) session.getAttribute(csrfGuard.getSessionKey());
			String token_pair = token_name + ":" + token_value;
	
//			/** setup headers **/
//			response.setContentType("text/plain");
//	
//			/** write dynamic javascript **/
//			OutputStream output = null;
//			PrintWriter writer = null;
	
			return token_pair
			
//			try {
//				output = response.getOutputStream();
//				writer = new PrintWriter(output);
//	
//				writer.write(token_pair);
//				writer.flush();
//			} finally {
//				Writers.close(writer);
//				Streams.close(output);
//			}
		}
	
	
		private static String writePageTokens(HttpServletRequest request, HttpServletResponse response) throws IOException {
			HttpSession session = request.getSession(true);
			@SuppressWarnings("unchecked")
			Map<String, String> pageTokens = (Map<String, String>) session.getAttribute(CsrfGuard.PAGE_TOKENS_KEY);
			String pageTokensString = (pageTokens != null ? parsePageTokens(pageTokens) : Strings.EMPTY);
	
			/** setup headers **/
			response.setContentType("text/plain");
			response.setContentLength(pageTokensString.length());
	
			/** write dynamic javascript **/
			OutputStream output = null;
			PrintWriter writer = null;
	
			return pageTokensString;
//			try {
//				output = response.getOutputStream();
//				writer = new PrintWriter(output);
//	
//				writer.write(pageTokensString);
//				writer.flush();
//			} finally {
//				Writers.close(writer);
//				Streams.close(output);
//			}
		}
	
		private static String writeJavaScript(HttpServletRequest request, HttpServletResponse response) throws IOException {
			HttpSession session = request.getSession(true);
			CsrfGuard csrfGuard = CsrfGuard.getInstance();
	
			/** cannot cache if rotate or token-per-page is enabled **/
//			if (csrfGuard.isRotateEnabled() || csrfGuard.isTokenPerPageEnabled()) {
//				response.setHeader("Cache-Control", "no-cache, no-store");
//				response.setHeader("Pragma", "no-cache");
//				response.setHeader("Expires", "0");
//			} else {
//				response.setHeader("Cache-Control", CsrfGuard.getInstance().getJavascriptCacheControl());
//			}
	
			//response.setContentType("text/javascript");
	
			/** build dynamic javascript **/
			String code = CsrfGuard.getInstance().getJavascriptTemplateCode();
	
			code = code.replace(TOKEN_NAME_IDENTIFIER, CsrfGuardUtils.defaultString(csrfGuard.getTokenName()));
			code = code.replace(TOKEN_VALUE_IDENTIFIER, CsrfGuardUtils.defaultString((String) session.getAttribute(csrfGuard.getSessionKey())));
			code = code.replace(INJECT_INTO_FORMS_IDENTIFIER, Boolean.toString(csrfGuard.isJavascriptInjectIntoForms()));
			code = code.replace(INJECT_GET_FORMS_IDENTIFIER, Boolean.toString(csrfGuard.isJavascriptInjectGetForms()));
			code = code.replace(INJECT_FORM_ATTRIBUTES_IDENTIFIER, Boolean.toString(csrfGuard.isJavascriptInjectFormAttributes()));
			code = code.replace(INJECT_INTO_ATTRIBUTES_IDENTIFIER, Boolean.toString(csrfGuard.isJavascriptInjectIntoAttributes()));
			code = code.replace(INJECT_INTO_XHR_IDENTIFIER, String.valueOf(csrfGuard.isAjaxEnabled()));
			code = code.replace(TOKENS_PER_PAGE_IDENTIFIER, String.valueOf(csrfGuard.isTokenPerPageEnabled()));
			code = code.replace(DOMAIN_ORIGIN_IDENTIFIER, CsrfGuardUtils.defaultString(parseDomain(request.getRequestURL())));
			code = code.replace(DOMAIN_STRICT_IDENTIFIER, Boolean.toString(csrfGuard.isJavascriptDomainStrict()));
			code = code.replace(CONTEXT_PATH_IDENTIFIER, CsrfGuardUtils.defaultString(request.getContextPath()));
			code = code.replace(SERVLET_PATH_IDENTIFIER, CsrfGuardUtils.defaultString(request.getContextPath() + request.getServletPath()));
			code = code.replace(X_REQUESTED_WITH_IDENTIFIER, CsrfGuardUtils.defaultString(csrfGuard.getJavascriptXrequestedWith()));
	
			/** write dynamic javascript **/
			OutputStream output = null;
			PrintWriter writer = null;
	
			return code;
//			try {
//				output = response.getOutputStream();
//				writer = new PrintWriter(output);
//	
//				writer.write(code);
//				writer.flush();
//			} finally {
//				Writers.close(writer);
//				Streams.close(output);
//			}
		}
	
		static String parsePageTokens(Map<String, String> pageTokens) {
//			StringBuilder sb = new StringBuilder();
//			Iterator<String> keys = pageTokens.keySet().iterator();
//	
//			while (keys.hasNext()) {
//				String key = keys.next();
//				String value = pageTokens.get(key);
//	
//				sb.append(key);
//				sb.append(':');
//				sb.append(value);
//	
//				if (keys.hasNext()) {
//					sb.append(',');
//				}
//			}
			StringBuilder sb = new StringBuilder();
			pageTokens.eachWithIndex {String key, String value, Integer indx ->
				sb.append("${key}:${value}" + indx == pageTokens.size() - 1 ? "" : ",")
			}
			
			sb.toString()
		}
		
	
		static String parseDomain(StringBuffer url) {
			String token = "://";
			int index = url.indexOf(token);
			String part = url.substring(index + token.length());
			StringBuilder domain = new StringBuilder();
	
			for (int i = 0; i < part.length(); i++) {
				char character = part.charAt(i);
	
				if (character == '/' || character == ':') {
					break;
				}
	
				domain.append(character);
			}
	
			return domain.toString();
		}	
}
