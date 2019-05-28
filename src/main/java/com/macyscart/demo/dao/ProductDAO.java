package com.macyscart.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.macyscart.demo.entity.Product;

@Repository
public interface ProductDAO extends JpaRepository<Product, String>{

	Product findByCode(String code);

	Product save(Product product);

	Product findByNameContainingIgnoreCase(String productName);

	List<Product> findAllByOrderByCodeAsc();

	//PaginationResult<ProductInfo> queryProducts(int page, int maxResult, int maxNavigationPage, String likeName);

}
