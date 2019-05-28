package com.macyscart.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.macyscart.demo.entity.Account;
import com.macyscart.demo.entity.Order;
import com.macyscart.demo.entity.OrderDetail;

@Repository
public interface OrderDAO extends JpaRepository<Order, String>{

	
	/*
	 * OrderInfo getOrderInfo(String orderId);
	 * 
	 * List<OrderDetailInfo> listOrderDetailInfos(String orderId);
	 */
	@Query("SELECT coalesce(max(ch.orderNum), 0) FROM Order ch")
	  int findMaxOrderNum();
	
	Order save(Order order);

	List<Order> findByAccount(Account findByUserName);

	Order findByOrderNum(int orderId);

	Order findLast1ByAccount(Account findByUserName);

	Order findFirst1ByAccountOrderByOrderDateDesc(Account findByUserName);
	
	///Order findById(String Order);
	  
	/*
	 * PaginationResult<OrderInfo> listOrderInfo(int page, int mAX_RESULT, int
	 * mAX_NAVIGATION_PAGE);
	 */
	 

}
