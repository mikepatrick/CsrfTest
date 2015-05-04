package com.cds.web.test.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cds.web.test.domain.CodeDisplayUtils;

public class WelcomeServlet extends javax.servlet.http.HttpServlet {

	private static final long serialVersionUID = 1L;
	
	public WelcomeServlet()
	{
		super();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.getSession(true);  // We need a session to bind the synchronizer token to.
		request.setAttribute("jsInclude", CodeDisplayUtils.getJsInclude());
		request.getRequestDispatcher("/welcome.jsp").forward(request, response);

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doGet(request, response);
	}
}
