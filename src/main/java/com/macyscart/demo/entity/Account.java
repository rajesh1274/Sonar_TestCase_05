package com.macyscart.demo.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
 
@Entity
@Table(name = "Accounts")
public class Account implements Serializable {
 
    private static final long serialVersionUID = -2054386655979281969L;
 
    public static final String ROLE_MANAGER = "MANAGER";
    public static final String ROLE_EMPLOYEE = "EMPLOYEE";
 
    @Id
    private Long id;
   
    @Column(name = "User_Name", length = 20, nullable = false)
    private String userName;
 
    @Column(name = "Encryted_Password", length = 128, nullable = false)
    private String encrytedPassword;
 
    @Column(name = "Active", length = 1, nullable = false)
    private boolean active;
 
    @Column(name = "User_Role", length = 20, nullable = false)
    private String userRole;
    
    @Column(name = "Customer_Address", length = 255, nullable = false)
    private String customerAddress;
 
    @Column(name = "Customer_Email", length = 128, nullable = false)
    private String customerEmail;
 
    @Column(name = "Customer_Phone", length = 128, nullable = false)
    private String customerPhone;
    
    @Column(name = "default_store", length = 128, nullable = false)
    private Long defaultStore;
    
    private String defaultStoreName;
 
    public String getDefaultStoreName() {
		return defaultStoreName;
	}

	public void setDefaultStoreName(String defaultStoreName) {
		this.defaultStoreName = defaultStoreName;
	}

	public String getUserName() {
        return userName;
    }
 
    public void setUserName(String userName) {
        this.userName = userName;
    }
 
    public String getEncrytedPassword() {
        return encrytedPassword;
    }
 
    public void setEncrytedPassword(String encrytedPassword) {
        this.encrytedPassword = encrytedPassword;
    }
 
    public boolean isActive() {
        return active;
    }
 
    public void setActive(boolean active) {
        this.active = active;
    }
 
    public String getUserRole() {
        return userRole;
    }
 
    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
    
 	public String getCustomerAddress() {
		return customerAddress;
	}

	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}

	public String getCustomerEmail() {
		return customerEmail;
	}

	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}

	public String getCustomerPhone() {
		return customerPhone;
	}

	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}

	
	public Long getDefaultStore() {
		return defaultStore;
	}

	public void setDefaultStore(Long defaultStore) {
		this.defaultStore = defaultStore;
	}

	@Override
	public String toString() {
		return "Account [userName=" + userName + ", encrytedPassword=" + encrytedPassword + ", active=" + active
				+ ", userRole=" + userRole + ", customerAddress=" + customerAddress + ", customerEmail=" + customerEmail
				+ ", customerPhone=" + customerPhone + ", defaultStore=" + defaultStore + "]";
	}

	/*
	 * @Override public String toString() { return "[" + this.userName + "," +
	 * this.encrytedPassword + "," + this.userRole + "]"; }
	 */
	
	
 
}