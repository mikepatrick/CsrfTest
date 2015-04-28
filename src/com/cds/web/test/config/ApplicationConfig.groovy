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
import com.cds.web.test.domain.JavaDomainObject;

@Configuration
@EnableWebMvc
public class ApplicationConfig {

	@Bean
	DataSource dataSource()
	{
		new SingleConnectionDataSource()
	}
	
	@Bean
	JdbcTemplate jdbcTemplate()
	{
		new JdbcTemplate(dataSource())
	}
	
	@Bean
	JavaDomainObject domainObject()
	{
		JavaDomainObject dobj = new JavaDomainObject();
		dobj.name = "Frank"
		dobj.address ="123 Main St"
		
		JavaDomainObject obj2 = new JavaDomainObject( [name: "Frank", address: "123 Elm Drive"] )
		
		return dobj;
	}
	
	@Bean
	TestDao testDao()
	{
		new TestDao()
	}
}
