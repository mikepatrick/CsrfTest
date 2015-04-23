package com.cds.web.test.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjaxServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		StringBuilder sb = new StringBuilder();
		String token = request.getParameter("OWASP-CSRFTOKEN");
		Enumeration<String> params = request.getParameterNames();
		System.out.println("params:");
		
		while (params.hasMoreElements())
		{
			System.out.println(params.nextElement());
		}
		System.out.println("headers:");
		Enumeration<String> headers = request.getHeaderNames();
		while (headers.hasMoreElements())
		{
			System.out.println(headers.nextElement());
		}
		sb.append("Content from ajax servlet\n");
		sb.append(token);
		PrintWriter pw = response.getWriter();
		pw.println(sb.toString());
	
	}
}
