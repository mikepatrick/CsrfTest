package com.cds.web.test.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cds.web.test.domain.CodeDisplayUtils;

public class DeletePageServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException
	{
		response.setContentType("text/html");
		String html = CodeDisplayUtils.getDeletePageHtml(request.getParameter("page_id"));
		response.getWriter().print(html);
		response.getWriter().flush();
	}
}
