package com.macyscart.demo.service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.macyscart.demo.dao.AccountDAO;
import com.macyscart.demo.dao.CartLineInfoDAO;
import com.macyscart.demo.dao.OrderDAO;
import com.macyscart.demo.dao.OrderDetailDAO;
import com.macyscart.demo.dao.ProductDAO;
import com.macyscart.demo.dao.ProductInfoDAO;
import com.macyscart.demo.entity.CartLineInfo;
import com.macyscart.demo.entity.Order;
import com.macyscart.demo.entity.OrderDetail;
import com.macyscart.demo.entity.Product;

@Component
public class OrderServiceImpl implements OrderService{

	@Autowired
	OrderDAO orderDAO;
	
	@Autowired
	ProductDAO productDAO;
	
	@Autowired
	OrderDetailDAO orderDetailDAO;
	
	@Autowired
	AccountDAO accountDAO;
	
	@Autowired
	CartLineInfoDAO cartLineInfoDAO;
	
	@Autowired
	ProductInfoDAO productInfoDAO;
	
	
	@Override
	public void saveOrder(CartInfoServiceImpl cartInfo, UserDetails userDetails) {
		 Order order = new Order();
		 
	        order.setId(UUID.randomUUID().toString());
	        order.setOrderNum(orderDAO.findMaxOrderNum() + 1);
	        order.setOrderDate(new Date());
	        order.setAmount(cartInfo.getAmountTotal());
	 
		/*
		 * CustomerInfo customerInfo = cartInfo.getCustomerInfo();
		 * order.setCustomerName(customerInfo.getName());
		 * order.setCustomerEmail(customerInfo.getEmail());
		 * order.setCustomerPhone(customerInfo.getPhone());
		 * order.setCustomerAddress(customerInfo.getAddress());
		 */
	        order.setAccount(accountDAO.findByUserName(userDetails.getUsername()));
	        order.setOrderStatus("Received");
	        
	        orderDAO.save(order);
	        
	        List<CartLineInfo> lines = cartInfo.getCartLines();
	        
	        for (CartLineInfo line : lines) {
	            OrderDetail detail = new OrderDetail();
	            detail.setId(UUID.randomUUID().toString());
	            detail.setOrder(order);
	            detail.setAmount(line.getAmount());
	            detail.setPrice(line.getProductInfo().getPrice());
	            detail.setQuanity(line.getQuantity());
	 
	            String code = line.getProductInfo().getCode();
	            Product product = productDAO.findByCode(code);
	            detail.setProduct(product);
	 
	            orderDetailDAO.saveAndFlush(detail);
	        }
	        
	        List<CartLineInfo> list = cartLineInfoDAO.findByAccount(accountDAO.findByUserName(userDetails.getUsername()));
	        list.forEach(line -> {
	        	productInfoDAO.delete(line.getProductInfo());
	        	
	        });
	        
	        
	        cartLineInfoDAO.deleteAllByAccount(accountDAO.findByUserName(userDetails.getUsername()));
	        
		
		/*
		 * productInfoDAO.deleteAllBy();(cartLineInfoDAO.findByAccount(order.getAccount(
		 * )).get(0).getProductInfo());
		 * 
		 * cartLineInfoDAO.deleteById(cartLineInfoDAO.findByAccount(order.getAccount()).
		 * get(0).getId());
		 */
		 
		
	}

}
