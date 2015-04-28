package com.cds.web.test.servlet;

import groovy.util.logging.Log
import groovy.xml.MarkupBuilder

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.PropertySource
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.servlet.ModelAndView

import com.cds.web.test.dao.TestDao
import com.cds.web.test.domain.DomainObject
//@Grab('log4j:log4j:1.2.16')

@Log
@Controller
@PropertySource("classpath:/com/cds/web/test/config/CsrfGuard.properties")
public class ProcessController {

	@Autowired TestDao testDao;	
	@Autowired DomainObject dob;
	
//	@Value("#{org.owasp.csrfguard.Enabled}") String csrfGuardEnabled
//	@Value("#{org.owasp.csrfguard.TokenPerPage}") String tokenPerPage
//	@Value("#{org.owasp.csrfguard.Ajax}") String protectAjax
	
	@RequestMapping("/welcome")
	public ModelAndView welcome()
	{
		new ModelAndView("welcome")
	}
	
	@RequestMapping(value="/process")
	public ModelAndView process(@RequestParam("username") String username, @RequestParam("password") String password)
	{	
		if (username.length() > 0 && password.length() > 0)
			new ModelAndView("success", ["domainObjects": dob])
		else
			new ModelAndView("failure")
	}
	
	@RequestMapping(value="/DeletePage")
	public @ResponseBody void deleteThePageFromTheThing(@RequestParam("page_id") Integer pageId, @RequestParam("mag_code") String magCode, 
		                                                  HttpServletRequest request, HttpServletResponse response)
	{
		
		println magCode
		log.info "magCode: ${magCode}"
		MarkupBuilder mb = new MarkupBuilder(response.getWriter());
		
		mb.mkp.yieldUnescaped(mb.html {
			div {
				p ("You deleted a page!")
			}
			br {}
		})
	}
	
	@RequestMapping(value="/JavaScriptServlet", method=RequestMethod.GET, produces = org.springframework.http.MediaType.TEXT_HTML_VALUE)
	public @ResponseBody String getJavaScript(HttpServletRequest request, HttpServletResponse response)
	{
		CsrfJavaScriptUtils.doGet(request, response)
	}
	
	@RequestMapping(value="/JavaScriptServlet", method=RequestMethod.POST)
	public @ResponseBody String getTokenz(HttpServletRequest request, HttpServletResponse response)
	{
		CsrfJavaScriptUtils.doPost(request, response)
	}
	
	@RequestMapping(value="ajaxCall", method=RequestMethod.GET)
	public @ResponseBody String ajaxCall(@RequestHeader(value="cds_x_id", required=false) String token)
	{
		"Ajax successful\n ${token}";
	}
	
}
