package com.macyscart.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.macyscart.demo.model.ProductInfo;

@Repository
public interface ProductInfoDAO extends JpaRepository<ProductInfo, Long>{
	
	@Query("SELECT coalesce(max(ch.id), 0) FROM ProductInfo ch")
	  long findMaxId();
	
	List<ProductInfo> findByCode(String code);

}
