package com.macyscart.demo.dao;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.macyscart.demo.entity.Product;
import com.macyscart.demo.entity.Store;
import com.macyscart.demo.entity.StoreProduct;



@Repository
public interface StoreProductDAO extends JpaRepository<StoreProduct, Long>{
	
	List<StoreProduct> findByStoreAndProduct(Store store, Product product);
	

}
