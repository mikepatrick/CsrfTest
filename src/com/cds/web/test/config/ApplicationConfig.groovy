package com.cds.web.test.config;

import javax.sql.DataSource

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.SingleConnectionDataSource
import org.springframework.web.servlet.config.annotation.EnableWebMvc

import com.cds.web.test.dao.TestDao
import com.cds.web.test.domain.DomainObject
import com.cds.web.test.domain.JavaDomainObject

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
	DomainObject domainObject()
	{
		DomainObject dobj = new DomainObject();
		dobj.name = "Frank"
		dobj.address ="123 Main St"
		
		DomainObject obj2 = new DomainObject( [name: "Frank", address: "123 Elm Drive"] )
		
		return dobj;
	}
	
	@Bean
	TestDao testDao()
	{
		new TestDao()
	}
}
