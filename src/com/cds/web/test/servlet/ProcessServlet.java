package com.cds.web.test.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cds.web.test.domain.DomainObject;

public class ProcessServlet extends HttpServlet{

	private static final long serialVersionUID = -4027458989452089813L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		throw new RuntimeException("No GETting!");
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		// This is for the benefit of the JSTL evangelism.
		request.setAttribute("domainObjects", new DomainObject("frank", "123 Main St."));

		if (request.getParameter("username").length() > 0 && request.getParameter("password").length() > 0)
			request.getRequestDispatcher("/success.jsp").forward(request, response);
		else
			response.sendRedirect("/TestWebProject/failure.jsp");
		
	}
}
