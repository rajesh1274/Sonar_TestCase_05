package com.macyscart.demo.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class StoreProduct {

	@Id
	private Long id;

	@ManyToOne
	@JoinColumn(name = "store")
	private Store store;

	@ManyToOne
	@JoinColumn(name = "product")
	private Product product;

	private Long availableQty;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Long getAvailableQty() {
		return availableQty;
	}

	public void setAvailableQty(Long availableQty) {
		this.availableQty = availableQty;
	}

}
