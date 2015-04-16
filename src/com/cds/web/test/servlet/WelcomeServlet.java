package com.cds.web.test.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class WelcomeServlet extends javax.servlet.http.HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public WelcomeServlet()
	{
		super();
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	{
		request.getSession(true);
		try {
			request.getRequestDispatcher("/welcome.jsp").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
	{
		doGet(request, response);
	}
}
