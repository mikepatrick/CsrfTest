package com.cds.web.test.servlet;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.cds.web.test.dao.TestDao;
import com.cds.web.test.domain.DomainObject;

@Controller
public class ProcessController {

	@Autowired TestDao testDao;
	
	@Autowired DomainObject dob;
	
	@RequestMapping(value="/process")
	public ModelAndView process(@RequestParam("username") String username, @RequestParam("password") String password)
	{	
		Map<String, Object> model = new HashMap<String, Object>();
		
		model.put("domainObjects", dob);
		
		if (username.length() > 0 && password.length() > 0)
		{
			return new ModelAndView("success.jsp", model);
		}
		else
		{
			return new ModelAndView("failure.jsp", model);
		}
	}
}
