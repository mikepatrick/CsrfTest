package com.cds.web.test.servlet;

import groovy.transform.CompileStatic

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@CompileStatic
public class AjaxController  {
	
	@RequestMapping(value="ajaxCall", method=RequestMethod.GET)
	public @ResponseBody String ajaxCall(@RequestHeader(value="cds_x_id", required=false) String token)
	{
		"Ajax successful\n ${token}";
	}
	
}
