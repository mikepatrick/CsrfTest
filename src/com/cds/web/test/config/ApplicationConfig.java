package com.cds.web.test.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.cds.web.test.dao.TestDao;
import com.cds.web.test.domain.DomainObject;

@Configuration
@EnableWebMvc
public class ApplicationConfig {

	@Bean
	public DataSource dataSource()
	{
		return new SingleConnectionDataSource();
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate()
	{
		return new JdbcTemplate(dataSource());
	}
	
	@Bean
	public DomainObject domainObject()
	{
		DomainObject dobj = new DomainObject();
		dobj.setName("Frank");
		dobj.setAddress("123 Main St");
		return dobj;
	}
	
	@Bean
	public TestDao testDao()
	{
		return new TestDao();
	}
//	@Bean
//	 public UrlBasedViewResolver setupViewResolver() {
//	   UrlBasedViewResolver resolver = new UrlBasedViewResolver();
//	   resolver.setPrefix("/WEB-INF/pages/");
//	   resolver.setSuffix(".jsp");
//	   resolver.setViewClass(JstlView.class);
//	   return resolver;
//	 }
	
}
