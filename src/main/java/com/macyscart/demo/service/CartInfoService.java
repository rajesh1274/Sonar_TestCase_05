package com.macyscart.demo.service;

import org.springframework.security.core.userdetails.UserDetails;

import com.macyscart.demo.entity.Account;
import com.macyscart.demo.model.ProductInfo;

public interface CartInfoService {

	void addProduct(ProductInfo productInfo, int quantity, UserDetails userDetails, boolean daoCall);
	
	public void updateQuantity(CartInfoServiceImpl cartForm, Account account, boolean b);

	void removeProduct(ProductInfo productInfo, Account account, boolean b);

}
