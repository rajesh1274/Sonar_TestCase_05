package com.macyscart.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.macyscart.demo.entity.Order;
import com.macyscart.demo.entity.OrderDetail;

@Repository
public interface OrderDetailDAO extends JpaRepository<OrderDetail,String>{
	
	List<OrderDetail> findByOrder(Order order);

}
