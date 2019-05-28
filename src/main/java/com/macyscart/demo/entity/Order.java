package com.macyscart.demo.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

 
@Entity
@Table(name = "Orders", //
        uniqueConstraints = { @UniqueConstraint(columnNames = "Order_Num") })
public class Order implements Serializable {
 
    private static final long serialVersionUID = -2576670215015463100L;
 
    @Id
    @Column(name = "ID", length = 50, nullable = false)
    private String id;
 
    @Column(name = "Order_Date", nullable = false)
    private Date orderDate;
 
    @Column(name = "Order_Num", nullable = false)
    private int orderNum;
 
    @Column(name = "Amount", nullable = false)
    private double amount;
 
    @Column(name = "order_status", length = 128, nullable = false)
    private String orderStatus;
    
    @Transient
    private String orderDateWithFormat;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "USER_ID", nullable = false, //
            foreignKey = @ForeignKey(name = "ORDER_DETAIL_ACT_FK"))
    private Account account;
    
   
 
    public String getOrderDateWithFormat() {
		return orderDateWithFormat;
	}

	public void setOrderDateWithFormat(String orderDateWithFormat) {
		this.orderDateWithFormat = orderDateWithFormat;
	}

	public String getId() {
        return id;
    }
 
    public void setId(String id) {
        this.id = id;
    }
 
    public Date getOrderDate() {
        return orderDate;
    }
 
    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }
 
    public int getOrderNum() {
        return orderNum;
    }
 
    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
 
    public double getAmount() {
        return amount;
    }
 
    public void setAmount(double amount) {
        this.amount = amount;
    }
 
    public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
}