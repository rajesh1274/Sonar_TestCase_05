package com.macyscart.demo.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.macyscart.demo.dao.AccountDAO;
import com.macyscart.demo.dao.CartLineInfoDAO;
import com.macyscart.demo.dao.OrderDAO;
import com.macyscart.demo.dao.ProductDAO;
import com.macyscart.demo.dao.StoreDAO;
import com.macyscart.demo.dao.StoreProductDAO;
import com.macyscart.demo.entity.Account;
import com.macyscart.demo.entity.Product;
import com.macyscart.demo.form.CustomerForm;
import com.macyscart.demo.model.CustomerInfo;
import com.macyscart.demo.model.ProductInfo;
import com.macyscart.demo.service.CartInfoService;
import com.macyscart.demo.service.CartInfoServiceImpl;
import com.macyscart.demo.service.OrderService;
import com.macyscart.demo.utils.Utils;
import com.macyscart.demo.validator.CustomerFormValidator;
 
@Controller
@Transactional
public class MainController {
 
   @Autowired
   private OrderDAO orderDAO;
 
   @Autowired
   private ProductDAO productDAO;
   
	@Autowired
	StoreDAO storeDAO;
	
	@Autowired
	AccountDAO accountDAO;
	
	@Autowired
	StoreProductDAO storeProductDAO;
 
   @Autowired
   private CustomerFormValidator customerFormValidator;
   
   @Autowired
   CartLineInfoDAO cartLineInfoDAO;
   
   @Autowired
   private OrderService orderService;
   
   @Autowired
   private CartInfoService cartInfoService;
   
   @InitBinder
   public void myInitBinder(WebDataBinder dataBinder) {
      Object target = dataBinder.getTarget();
      if (target == null) {
         return;
      }
      System.out.println("Target=" + target);
 
      // Case update quantity in cart
      // (@ModelAttribute("cartForm") @Validated CartInfo cartForm)
      if (target.getClass() == CartInfoServiceImpl.class) {
 
      }
 
      // Case save customer information.
      // (@ModelAttribute @Validated CustomerInfo customerForm)
      else if (target.getClass() == CustomerForm.class) {
         dataBinder.setValidator(customerFormValidator);
      }
 
   }
 
   @RequestMapping("/403")
   public String accessDenied() {
      return "/403";
   }
 
   @RequestMapping("/")
   public String home() {
      return "index";
   }
 
   // Product List
   @RequestMapping({ "/productList" })
   public String listProductHandler(Model model, //
         @RequestParam(value = "name", defaultValue = "") String likeName,
         @RequestParam(value = "page", defaultValue = "1") int page) {
      final int maxResult = 5;
      final int maxNavigationPage = 10;
 
      List<Product> result = productDAO.findAllByOrderByCodeAsc();
 
      model.addAttribute("paginationProducts", result);
      return "productList";
   }
 
   @RequestMapping({ "/buyProduct" })
   public String listProductHandler(HttpServletRequest request, Model model, //
         @RequestParam(value = "code", defaultValue = "") String code) {
	   
	   UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
      Product product = null;
      if (code != null && code.length() > 0) {
         product = productDAO.findByCode(code);
      }
      if (product != null) {
 
         //
         CartInfoServiceImpl cartInfo = Utils.getCartInSession(request);
 
         ProductInfo productInfo = new ProductInfo(product);
 
         cartInfoService.addProduct(productInfo, 1, userDetails, true);
         cartInfo.addProduct(productInfo, 1, userDetails, false);
      }
 
      return "redirect:/shoppingCart";
   }
 
   @RequestMapping({ "/shoppingCartRemoveProduct" })
   public String removeProductHandler(HttpServletRequest request, Model model, //
         @RequestParam(value = "code", defaultValue = "") String code) {
      Product product = null;
      if (code != null && code.length() > 0) {
         product = productDAO.findByCode(code);
      }
      if (product != null) {
    	  UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	  Account account =  accountDAO.findByUserName(userDetails.getUsername());
 
         CartInfoServiceImpl cartInfo = Utils.getCartInSession(request);
 
         ProductInfo productInfo = new ProductInfo(product);
 
         cartInfoService.removeProduct(productInfo, account, true);
         cartInfo.removeProduct(productInfo, account, false);
 
      }
 
      return "redirect:/shoppingCart";
   }
 
   // POST: Update quantity for product in cart
   @RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.POST)
   public String shoppingCartUpdateQty(HttpServletRequest request, //
         Model model, //
         @ModelAttribute("cartForm") CartInfoServiceImpl cartForm) {
	   
	   UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	  Account account =  accountDAO.findByUserName(userDetails.getUsername());
 
      CartInfoServiceImpl cartInfo = Utils.getCartInSession(request);
      cartInfoService.updateQuantity(cartForm, account, true);
      cartInfo.updateQuantity(cartForm, account, false);
 
      return "redirect:/shoppingCart";
   }
 
   // GET: Show cart.
   @RequestMapping(value = { "/shoppingCart" }, method = RequestMethod.GET)
   public String shoppingCartHandler(HttpServletRequest request, Model model) {
      CartInfoServiceImpl myCart = Utils.getCartInSession(request);
 
      model.addAttribute("cartForm", myCart);
      return "shoppingCart";
   }
 
   // GET: Enter customer information.
   @RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.GET)
   public String shoppingCartCustomerForm(HttpServletRequest request, Model model) {
 
		/*
		 * CartInfo cartInfo = Utils.getCartInSession(request);
		 * 
		 * if (cartInfo.isEmpty()) {
		 * 
		 * return "redirect:/shoppingCart"; } CustomerInfo customerInfo =
		 * cartInfo.getCustomerInfo();
		 * 
		 * CustomerForm customerForm = new CustomerForm(customerInfo);
		 * 
		 * model.addAttribute("customerForm", customerForm);
		 */
	   
	   UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		  Account account =  accountDAO.findByUserName(userDetails.getUsername());
		  
		  CustomerForm cf = new CustomerForm();
		  cf.setAddress(account.getCustomerAddress());
		  cf.setEmail(account.getCustomerEmail());
		  cf.setName(account.getUserName());
		  cf.setPhone(account.getCustomerPhone());
		  
	   CartInfoServiceImpl myCart = Utils.getCartInSession(request);
	   model.addAttribute("cartForm", myCart);
	   model.addAttribute("cartLine", cf);
 
      return "shoppingCartCustomer";
   }
 
   // POST: Save customer information.
   @RequestMapping(value = { "/shoppingCartCustomer" }, method = RequestMethod.POST)
   public String shoppingCartCustomerSave(HttpServletRequest request, //
         Model model, //
         @ModelAttribute("customerForm") @Validated CustomerForm customerForm, //
         BindingResult result, //
         final RedirectAttributes redirectAttributes) {
 
		/*
		 * if (result.hasErrors()) { customerForm.setValid(false); // Forward to reenter
		 * customer info. return "shoppingCartCustomer"; }
		 */
 
      customerForm.setValid(true);
      CartInfoServiceImpl cartInfo = Utils.getCartInSession(request);
      CustomerInfo customerInfo = new CustomerInfo(customerForm);
      cartInfo.setCustomerInfo(customerInfo);
 
      return "redirect:/shoppingCartConfirmation";
   }
 
   // GET: Show information to confirm.
   @RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.GET)
   public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
      CartInfoServiceImpl cartInfo = Utils.getCartInSession(request);
 
      if (cartInfo == null || cartInfo.isEmpty()) {
 
         return "redirect:/shoppingCart";
      } else if (!cartInfo.isValidCustomer()) {
 
         return "redirect:/shoppingCartCustomer";
      }
      model.addAttribute("myCart", cartInfo);
 
      return "shoppingCartConfirmation";
   }
 
   // POST: Submit Cart (Save)
   @RequestMapping(value = { "/shoppingCartConfirmation" }, method = RequestMethod.POST)
 
   public String shoppingCartConfirmationSave(HttpServletRequest request, Model model) {
      CartInfoServiceImpl cartInfo = Utils.getCartInSession(request);
      UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      if (cartInfo.isEmpty()) {
 
         return "redirect:/shoppingCart";
      } else if (!cartInfo.isValidCustomer()) {
 
         return "redirect:/shoppingCartCustomer";
      }
      try {
    	  orderService.saveOrder(cartInfo, userDetails);
      } catch (Exception e) {
 
         return "shoppingCartConfirmation";
      }
 
      // Remove Cart from Session.
      Utils.removeCartInSession(request);
 
      // Store last cart.
      Utils.storeLastOrderedCartInSession(request, cartInfo);
 
      return "redirect:/shoppingCartFinalize";
   }
 
   @RequestMapping(value = { "/shoppingCartFinalize" }, method = RequestMethod.GET)
   public String shoppingCartFinalize(HttpServletRequest request, Model model) {
 
      CartInfoServiceImpl lastOrderedCart = Utils.getLastOrderedCartInSession(request);
      
      cartLineInfoDAO.findMaxId();
 
      if (lastOrderedCart == null) {
         return "redirect:/shoppingCart";
      }
      model.addAttribute("lastOrderedCart", orderDAO.findMaxOrderNum());
      return "shoppingCartFinalize";
   }
 
   @RequestMapping(value = { "/productImage" }, method = RequestMethod.GET)
   public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
         @RequestParam("code") String code) throws IOException {
      Product product = null;
      if (code != null) {
         product = productDAO.findByCode(code);
      }
      if (product != null && product.getImage() != null) {
         response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
         response.getOutputStream().write(product.getImage());
      }
      response.getOutputStream().close();
   }
   
   
   @RequestMapping(value = "/", method = RequestMethod.GET)
	public List<Product> getProductList() {
		return productDAO.findAll();

	}
 
}