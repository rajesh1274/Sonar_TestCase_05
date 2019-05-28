package com.macyscart.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.macyscart.demo.dao.CartLineInfoDAO;

@Component
public class TestService {
	
	@Autowired
	CartLineInfoDAO test1;
	
	public void test() {
		test1.findAll();
	}

}
