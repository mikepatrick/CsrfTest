package com.cds.web.test.servlet;

import groovy.util.logging.Log;


import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjaxServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		writeParmsAndHeaders(request);

		response.getWriter().println(new StringBuilder()
							.append("Content from ajax servlet\n")
							.append(request.getHeader("cds_x_id")).toString());
	
	}
	
	private void writeParmsAndHeaders(HttpServletRequest request)
	{
		Enumeration<String> params = request.getParameterNames();
		while (params.hasMoreElements()) System.out.println("param: " + params.nextElement());
		
		Enumeration<String> headers = request.getHeaderNames();
		while (headers.hasMoreElements())
		{
			String header = headers.nextElement();
			System.out.println("header: " + header + " : " + request.getHeader(header));
		}
	}
}
