package com.macyscart.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.macyscart.demo.model.ProductInfo;

@Entity
@Table(name = "CART_LINE")
public class CartLineInfo {
	 
	@Id
	private Long id;
	
	@ManyToOne 
	@JoinColumn(name = "productInfo")
    private ProductInfo productInfo;
    
    private int quantity;
    
    @ManyToOne
    @JoinColumn(name = "account")
    private Account account;
  
    public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public CartLineInfo() {
        this.quantity = 0;
    }
  
    public ProductInfo getProductInfo() {
        return productInfo;
    }
  
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setProductInfo(ProductInfo productInfo) {
        this.productInfo = productInfo;
    }
  
    public int getQuantity() {
        return quantity;
    }
  
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
  
    public double getAmount() {
        return this.productInfo.getPrice() * this.quantity;
    }
     
}
