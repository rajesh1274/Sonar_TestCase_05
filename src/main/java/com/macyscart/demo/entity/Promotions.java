package com.macyscart.demo.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Promotions {
	
	@Id
	private Long id;
	
	private String promotionLevel;
	
	private String promotionLevelId;
	
	private String promortionDescription;
	
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPromotionLevel() {
		return promotionLevel;
	}

	public void setPromotionLevel(String promotionLevel) {
		this.promotionLevel = promotionLevel;
	}

	public String getPromotionLevelId() {
		return promotionLevelId;
	}

	public void setPromotionLevelId(String promotionLevelId) {
		this.promotionLevelId = promotionLevelId;
	}

	public String getPromortionDescription() {
		return promortionDescription;
	}

	public void setPromortionDescription(String promortionDescription) {
		this.promortionDescription = promortionDescription;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
