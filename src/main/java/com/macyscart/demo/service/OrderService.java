package com.macyscart.demo.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface OrderService {
	
	void saveOrder(CartInfoServiceImpl cartInfo, UserDetails userDetails);

}
