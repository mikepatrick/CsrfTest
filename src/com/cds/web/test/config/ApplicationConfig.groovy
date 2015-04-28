package com.cds.web.test.config;

import groovy.transform.CompileStatic

import javax.sql.DataSource

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.SingleConnectionDataSource
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

import com.cds.web.test.dao.TestDao
import com.cds.web.test.domain.DomainObject

@Configuration
@PropertySource("classpath:/com/cds/web/test/config/CsrfGuard.properties")
@EnableWebMvc
@CompileStatic
public class ApplicationConfig extends WebMvcConfigurerAdapter {

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
	
//	@Bean 
//	public PropertyPlaceholderConfigurer propertyConfig() {
//		PropertyPlaceholderConfigurer config = new PropertyPlaceholderConfigurer()
//		config.setLocations(new Resource[] {new ClassPathResource("CsrfGuard.properties") } )
//		return config	
//	}
	
	@Bean
	public PropertySourcesPlaceholderConfigurer propertyConfig() {
		new PropertySourcesPlaceholderConfigurer()
	}
	
	@Bean
	TestDao testDao()
	{
		new TestDao()
	}
}
