package com.macyscart.demo.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.macyscart.demo.dao.AccountDAO;
import com.macyscart.demo.dao.CartLineInfoDAO;
import com.macyscart.demo.dao.ProductInfoDAO;
import com.macyscart.demo.entity.Account;
import com.macyscart.demo.entity.CartLineInfo;
import com.macyscart.demo.model.CustomerInfo;
import com.macyscart.demo.model.ProductInfo;

@Component
public class CartInfoServiceImpl implements CartInfoService{
	
	@Autowired
	CartLineInfoDAO cartLineInfoDAO;
	
	@Autowired
	ProductInfoDAO productInfoDAO;
	
	@Autowired
	AccountDAO accountDAO;
	
	private int orderNum;
 
    private CustomerInfo customerInfo;
    
    private final List<CartLineInfo> cartLines = new ArrayList<CartLineInfo>();
 
    public CartInfoServiceImpl() {
 
    }
 
    public int getOrderNum() {
        return orderNum;
    }
 
    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }
 
    public CustomerInfo getCustomerInfo() {
        return customerInfo;
    }
 
    public void setCustomerInfo(CustomerInfo customerInfo) {
        this.customerInfo = customerInfo;
    }
 
    public List<CartLineInfo> getCartLines() {
        return this.cartLines;
    }
 
    private CartLineInfo findLineByCode(String code) {
        for (CartLineInfo line : this.cartLines) {
            if (line.getProductInfo().getCode().equals(code)) {
                return line;
            }
        }
        return null;
    }
 
    @Override
    public void addProduct(ProductInfo productInfo, int quantity, UserDetails userDetails, boolean daoCall) {
        CartLineInfo line = this.findLineByCode(productInfo.getCode());
        boolean newLine = false;
        CartLineInfo userLine = null;
        
		/*
		 * if(daoCall) { try { userLine =
		 * cartLineInfoDAO.findByAccountAndProductInfo(accountDAO.findByUserName(
		 * userDetails.getUsername()), productInfo); } catch(Exception e) { newLine =
		 * true; } if(null == userLine) { newLine = true; } }
		 */
        if (line == null) {
            line = new CartLineInfo();
            line.setQuantity(0);
            line.setProductInfo(productInfo);
            this.cartLines.add(line);
            newLine = true;
        }
        int newQuantity = line.getQuantity() + quantity;
        if (newQuantity <= 0) {
            this.cartLines.remove(line);
        } else {
            line.setQuantity(newQuantity);
            
        }
        
        
        if(daoCall) {
        try {
        	List<CartLineInfo> userLineInfo = cartLineInfoDAO.findByAccount(accountDAO.findByUserName(userDetails.getUsername()));
        	if(userLineInfo.isEmpty()) {
        		line = new CartLineInfo();
                line.setQuantity(quantity);
                line.setProductInfo(productInfo);
                this.cartLines.add(line);
                newLine = true;
        	}
        		
        	for (CartLineInfo cartLineInfo : userLineInfo) {
				if(cartLineInfo.getProductInfo().getCode() == productInfo.getCode()) {
					newLine =false;
					break;
				} 
			}
        	
        	if(newLine) {
        	line.setId(cartLineInfoDAO.findMaxId() + 1);
        	productInfo.setId(productInfoDAO.findMaxId() + 1);
        	}
        	line.setAccount(accountDAO.findByUserName(userDetails.getUsername()));
        productInfoDAO.save(line.getProductInfo());
        cartLineInfoDAO.save(line);
        }catch(Exception e) {
        	e.printStackTrace();
        } }
        
        
    }
 
    public void validate() {
 
    }
 
    public void updateProduct(String code, int quantity, Account account, boolean b) {
        CartLineInfo line = this.findLineByCode(code);
        ProductInfo pi = null;
        if(b) {
        	List<CartLineInfo> cInfo = cartLineInfoDAO.findByAccount(account);
        	
        	for (CartLineInfo cartLineInfo : cInfo) {
				if(cartLineInfo.getProductInfo().getCode().equals(code)) {
					pi = cartLineInfo.getProductInfo();
				}
			}
        
        }
        if (line != null) {
            if (quantity <= 0) {
            	if(b) {
            	productInfoDAO.delete(pi);
            	cartLineInfoDAO.delete(cartLineInfoDAO.findByProductInfo(pi));
            	}
                this.cartLines.remove(line);
            } else {
                line.setQuantity(quantity);
                if(b) {
                	CartLineInfo carLine = cartLineInfoDAO.findByAccountAndProductInfo(account, pi);
                	carLine.setQuantity(quantity);
                cartLineInfoDAO.save(carLine);
                }
            }
        }
    }
 
    @Override
    public void removeProduct(ProductInfo productInfo,Account account, boolean b) {
        CartLineInfo line = this.findLineByCode(productInfo.getCode());
        
        ProductInfo pi = null;
        if(b) {
        	List<CartLineInfo> cInfo = cartLineInfoDAO.findByAccount(account);
        	
        	for (CartLineInfo cartLineInfo : cInfo) {
				if(cartLineInfo.getProductInfo().getCode().equals(productInfo.getCode())) {
					pi = cartLineInfo.getProductInfo();
				}
			}
        
        }
        if (line != null) {
        	if(b) {
        	productInfoDAO.delete(pi);
        	cartLineInfoDAO.delete(cartLineInfoDAO.findByProductInfo(pi));
        	}
            this.cartLines.remove(line);
        }
    }
 
    public boolean isEmpty() {
        return this.cartLines.isEmpty();
    }
 
    public boolean isValidCustomer() {
        return this.customerInfo != null && this.customerInfo.isValid();
    }
 
    public int getQuantityTotal() {
        int quantity = 0;
        for (CartLineInfo line : this.cartLines) {
            quantity += line.getQuantity();
        }
        return quantity;
    }
 
    public double getAmountTotal() {
        double total = 0;
        for (CartLineInfo line : this.cartLines) {
            total += line.getAmount();
        }
        return total;
    }
 
    @Override
    public void updateQuantity(CartInfoServiceImpl cartForm, Account account, boolean b) {
        if (cartForm != null) {
            List<CartLineInfo> lines = cartForm.getCartLines();
            for (CartLineInfo line : lines) {
                this.updateProduct(line.getProductInfo().getCode(), line.getQuantity(), account, b);
            }
        }
 
    }
 
}
