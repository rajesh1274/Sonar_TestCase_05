package com.macyscart.demo.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.macyscart.demo.entity.Store;



@Repository
public interface StoreDAO extends JpaRepository<Store, Long>{
	
	Optional<Store> findById(Long id);
	
	Store findByName(String name);

	Store findByNameContainingIgnoreCase(String storeName);

}
