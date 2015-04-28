package com.cds.web.test.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AjaxController  {
	
	@RequestMapping(value="ajaxCall", method=RequestMethod.POST)
	public String ajaxCall(@RequestHeader("cds_x_id") String token)
	{
		"Ajax successful";
	}
	
}
