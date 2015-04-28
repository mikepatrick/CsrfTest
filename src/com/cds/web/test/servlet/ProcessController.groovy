package com.cds.web.test.servlet;

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.servlet.ModelAndView

import com.cds.web.test.dao.TestDao
import com.cds.web.test.domain.DomainObject

@Controller
public class ProcessController {

	@Autowired TestDao testDao;	
	@Autowired DomainObject dob;
	
	@RequestMapping(value="/process")
	public ModelAndView process(@RequestParam("username") String username, @RequestParam("password") String password)
	{	
		if (username.length() > 0 && password.length() > 0)
			new ModelAndView("success", ["domainObjects": dob]);
		else
			new ModelAndView("failure");
	}
	
	@RequestMapping(value="/DeletePage")
	public ModelAndView deleteThePageFromTheThing()
	{
		"page deleted"
	}
}
