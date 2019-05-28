package com.macyscart.demo.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.macyscart.demo.entity.Promotions;

public interface PromotionDAO extends JpaRepository<Promotions, Long>{

	Promotions findByPromotionLevelId(String promotionLevel);

	List<Promotions> findLast3ByOrderByIdDesc();

	List<Promotions> findByPromotionLevelIdContainingIgnoreCase(String promotionLevel);

}
