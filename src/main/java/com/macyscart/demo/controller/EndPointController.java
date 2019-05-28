package com.macyscart.demo.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.macyscart.demo.dao.AccountDAO;
import com.macyscart.demo.dao.CartLineInfoDAO;
import com.macyscart.demo.dao.OrderDAO;
import com.macyscart.demo.dao.OrderDetailDAO;
import com.macyscart.demo.dao.ProductDAO;
import com.macyscart.demo.dao.PromotionDAO;
import com.macyscart.demo.dao.StoreDAO;
import com.macyscart.demo.dao.StoreProductDAO;
import com.macyscart.demo.entity.CartLineInfo;
import com.macyscart.demo.entity.Order;
import com.macyscart.demo.entity.OrderDetail;
import com.macyscart.demo.entity.Product;
import com.macyscart.demo.entity.Promotions;
import com.macyscart.demo.entity.Store;
import com.macyscart.demo.entity.StoreProduct;
import com.macyscart.demo.model.ProductInfo;

@RestController
public class EndPointController {

	@Autowired
	ProductDAO productDAO;

	@Autowired
	StoreDAO storeDAO;

	@Autowired
	AccountDAO accountDAO;

	@Autowired
	StoreProductDAO storeProductDAO;

	@Autowired
	CartLineInfoDAO cartLineInfoDAO;

	@Autowired
	OrderDAO orderDAO;

	@Autowired
	OrderDetailDAO orderDetailDAO;
	
	@Autowired
	PromotionDAO promotionDAO;

	@RequestMapping(value = "/StoreProduct/{productName}", method = RequestMethod.GET)
	public List<StoreProduct> getProductByName(
			@PathVariable(value = "productName") String productName) {

		//UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Long defalultStore = accountDAO.findByUserName("Sudha").getDefaultStore();

		Optional<Store> store = storeDAO.findById(defalultStore);

		Product product = productDAO.findByNameContainingIgnoreCase(productName);

		if (null == store || null == product)
			return null;

		return storeProductDAO.findByStoreAndProduct(store.get(), product);

	}

	@RequestMapping(value = "/StoreProduct/{productName}/{storeName}", method = RequestMethod.GET)
	public List<StoreProduct> getProductByName(@PathVariable(value = "productName") String productName,
			@PathVariable(value = "storeName") String storeName) {
		Store st = storeDAO.findByNameContainingIgnoreCase(storeName);
		Product product = productDAO.findByNameContainingIgnoreCase(productName);

		if (null == st || null == product)
			return null;

		return storeProductDAO.findByStoreAndProduct(st, product);

	}

	@RequestMapping(value = "/cartDetails", method = RequestMethod.GET)
	public List<CartLineInfo> getCartDetails() {

		// UserDetails userDetails = (UserDetails)
		// SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		Long defalultStore = accountDAO.findByUserName("Sudha").getDefaultStore();

		Optional<Store> store = storeDAO.findById(defalultStore);

		List<CartLineInfo> cartList = new ArrayList<CartLineInfo>();
		if (null == store) {
			return null;
		}

		List<CartLineInfo> list = cartLineInfoDAO.findByAccount(accountDAO.findByUserName("Sudha"));

		for (CartLineInfo cartLineInfo : list) {
			List<StoreProduct> sp = storeProductDAO.findByStoreAndProduct(store.get(), productDAO.findByNameContainingIgnoreCase(cartLineInfo.getProductInfo().getName()));
			CartLineInfo cLineInfo = new CartLineInfo();
			ProductInfo pi = new ProductInfo();

			if (sp == null || sp.isEmpty()) {
				cLineInfo.setQuantity(0);
				pi.setName(cartLineInfo.getProductInfo().getName());
				pi.setPrice(cartLineInfo.getProductInfo().getPrice());

			} else {
				for (StoreProduct storeProdcut : sp) {

					if (cartLineInfo.getProductInfo().getCode().equalsIgnoreCase(storeProdcut.getProduct().getCode())) {

						cLineInfo.setQuantity(Integer.valueOf(storeProdcut.getAvailableQty().toString()));
						pi.setCode(storeProdcut.getProduct().getCode());
						pi.setName(storeProdcut.getProduct().getName());
						pi.setPrice(storeProdcut.getProduct().getPrice());

					}

				}
				
			}
			
			cLineInfo.setAccount(accountDAO.findByUserName("Sudha"));
			cLineInfo.setProductInfo(pi);
			cartList.add(cLineInfo);
		}

		return cartList;
	}
	
	@RequestMapping(value = "/cartDetails/{storeName}", method = RequestMethod.GET)
	public List<CartLineInfo> getCartDetailsByStore(@PathVariable(name = "storeName") String storeName) {

		//UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Store store = storeDAO.findByNameContainingIgnoreCase(storeName);
		List<CartLineInfo> cartList = new ArrayList<CartLineInfo>();
		if(null == store) { return null;
		}
		
		List<CartLineInfo> list = cartLineInfoDAO.findByAccount(accountDAO.findByUserName("Sudha"));
		for (CartLineInfo cartLineInfo : list) {
			List<StoreProduct> sp = storeProductDAO.findByStoreAndProduct(store, productDAO.findByNameContainingIgnoreCase(cartLineInfo.getProductInfo().getName()));
			CartLineInfo cLineInfo = new CartLineInfo();
			ProductInfo pi = new ProductInfo();
			
			
			if(sp == null || sp.isEmpty()) {
				cLineInfo.setQuantity(0);
				pi.setName(cartLineInfo.getProductInfo().getName());
				pi.setPrice(cartLineInfo.getProductInfo().getPrice());
				
			} else {
				for (StoreProduct storeProdcut : sp) {
					
					if(cartLineInfo.getProductInfo().getCode().equalsIgnoreCase(storeProdcut.getProduct().getCode())) {
						
						  cLineInfo.setQuantity(Integer.valueOf(storeProdcut.getAvailableQty().toString()));
						  pi.setCode(storeProdcut.getProduct().getCode()); pi.setName(storeProdcut.getProduct().getName());
						  pi.setPrice(storeProdcut.getProduct().getPrice());
						 
					}
					
				}
				
			}
			cLineInfo.setAccount(accountDAO.findByUserName("Sudha"));
			cLineInfo.setProductInfo(pi);
			cartList.add(cLineInfo);			
		}
		
		return cartList;
	}

	@RequestMapping(value = "/custOrderDetails", method = RequestMethod.GET)
	public List<OrderDetail> getOrderDetailsByCustomer() {

		//UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		List<Order> order = orderDAO.findByAccount(accountDAO.findByUserName("Sudha"));

		return orderDetailDAO.findByOrder(order.get(0));

	}

	@RequestMapping(value = "/OrderDetails/{orderNum}", method = RequestMethod.GET)
	public Order getOrderDetailsById(@PathVariable(value = "orderNum") Long orderNum) {

		Order order = orderDAO.findByOrderNum(orderNum.intValue());

		Date myDate = order.getOrderDate();
		SimpleDateFormat sm = new SimpleDateFormat("dd MMMM yyyy");

		order.setOrderDateWithFormat(sm.format(myDate));

		return order;

	}

	@RequestMapping(value = "/latestOrderStatus", method = RequestMethod.GET)
	public Order getLatestOrder() {

		//UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Order order = orderDAO.findFirst1ByAccountOrderByOrderDateDesc(accountDAO.findByUserName("Sudha"));
		Date myDate = order.getOrderDate();
		SimpleDateFormat sm = new SimpleDateFormat("dd MMMM yyyy");

		order.setOrderDateWithFormat(sm.format(myDate));
		
		return order;

	}
	
	@RequestMapping(value = "/promotions/{promotionLevel}", method = RequestMethod.GET)
	public List<Promotions> getPromotions(@PathVariable(name = "promotionLevel") String promotionLevel) {

		return promotionDAO.findByPromotionLevelIdContainingIgnoreCase(promotionLevel);

	}
	
	@RequestMapping(value = "/promotions", method = RequestMethod.GET)
	public List<Promotions> getLatestPromotions() {

		return promotionDAO.findLast3ByOrderByIdDesc();

	}

}
