package com.cds.web.test.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class TestDao {

	@Autowired JdbcTemplate jdbcTemplate;
}
