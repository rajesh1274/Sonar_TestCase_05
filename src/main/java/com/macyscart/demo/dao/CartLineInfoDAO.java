package com.macyscart.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.macyscart.demo.entity.Account;
import com.macyscart.demo.entity.CartLineInfo;
import com.macyscart.demo.model.ProductInfo;

@Repository
public interface CartLineInfoDAO extends JpaRepository<CartLineInfo, Long>{
	
	@Query("SELECT coalesce(max(ch.id), 0) FROM CartLineInfo ch")
	  long findMaxId();

	List<CartLineInfo> findByAccount(Account account);

	CartLineInfo findByAccountAndProductInfo(Account account, ProductInfo productInfo);

	void deleteAllByAccount(Account findByUserName);

	CartLineInfo findByProductInfo(ProductInfo pi);
	
	

}
