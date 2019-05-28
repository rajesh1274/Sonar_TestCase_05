package com.macyscart.demo.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.macyscart.demo.entity.Product;

@Entity
public class ProductInfo {
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Id
	private Long id;
	
    private String code;
    private String name;
    private double price;
    private String imgSrc;
    
    
 
    public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public ProductInfo() {
    }
 
    public ProductInfo(Product product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.price = product.getPrice();
        this.imgSrc = product.getImgSrc();
    }
 
    // Using in JPA/Hibernate query
    public ProductInfo(String code, String name, double price) {
        this.code = code;
        this.name = name;
        this.price = price;
    }
 
    public String getCode() {
        return code;
    }
 
    public void setCode(String code) {
        this.code = code;
    }
 
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public double getPrice() {
        return price;
    }
 
    public void setPrice(double price) {
        this.price = price;
    }
 
}
