package com.macyscart.demo.controller;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
import com.macyscart.demo.dao.OrderDAO;
import com.macyscart.demo.dao.OrderDetailDAO;
import com.macyscart.demo.dao.ProductDAO;
import com.macyscart.demo.entity.Order;
import com.macyscart.demo.entity.OrderDetail;
import com.macyscart.demo.entity.Product;
import com.macyscart.demo.form.ProductForm;
import com.macyscart.demo.model.OrderInfo;
import com.macyscart.demo.validator.ProductFormValidator;
 
@Controller
@Transactional
public class AdminController {
 
   @Autowired
   private OrderDAO orderDAO;
 
   @Autowired
   private ProductDAO productDAO;
 
   @Autowired
   private ProductFormValidator productFormValidator;

   @Autowired
   private OrderDetailDAO orderDetailDAO;
   
   @Autowired
   private AccountDAO accountDAO;
 
   @InitBinder
   public void myInitBinder(WebDataBinder dataBinder) {
      Object target = dataBinder.getTarget();
      if (target == null) {
         return;
      }
      System.out.println("Target=" + target);
 
      if (target.getClass() == ProductForm.class) {
         dataBinder.setValidator(productFormValidator);
      }
   }
 
   // GET: Show Login Page
   @RequestMapping(value = { "/admin/login" }, method = RequestMethod.GET)
   public String login(Model model) {
 
      return "login";
   }
 
   @RequestMapping(value = { "/admin/accountInfo" }, method = RequestMethod.GET)
   public String accountInfo(Model model) {
 
      UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      System.out.println(userDetails.getPassword());
      System.out.println(userDetails.getUsername());
      System.out.println(userDetails.isEnabled());
 
      model.addAttribute("userDetails", userDetails);
      return "accountInfo";
   }
 
   @RequestMapping(value = { "/admin/orderList" }, method = RequestMethod.GET)
   public String orderList(Model model, //
         @RequestParam(value = "page", defaultValue = "1") String pageStr) {
      int page = 1;
      try {
         page = Integer.parseInt(pageStr);
      } catch (Exception e) {
      }
      final int MAX_RESULT = 5;
      final int MAX_NAVIGATION_PAGE = 10;
      
      UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      
		/*
		 * PaginationResult<OrderInfo> paginationResult // =
		 * orderDAO.listOrderInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE);
		 * 
		  */ model.addAttribute("paginationResult", orderDAO.findByAccount(accountDAO.findByUserName(userDetails.getUsername())));
		
      return "orderList";
   }
 
   // GET: Show product.
   @RequestMapping(value = { "/admin/product" }, method = RequestMethod.GET)
   public String product(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
      ProductForm productForm = null;
 
      if (code != null && code.length() > 0) {
         Product product = productDAO.findByCode(code);
         if (product != null) {
            productForm = new ProductForm(product);
         }
      }
      if (productForm == null) {
         productForm = new ProductForm();
         productForm.setNewProduct(true);
      }
      model.addAttribute("productForm", productForm);
      return "product";
   }
 
   // POST: Save product
   @RequestMapping(value = { "/admin/product" }, method = RequestMethod.POST)
   public String productSave(Model model, //
         @ModelAttribute("productForm") @Validated ProductForm productForm, //
         BindingResult result, //
         final RedirectAttributes redirectAttributes) {
 
      if (result.hasErrors()) {
        // return "product";
      }
      try {
    	  
    	  String code = productForm.getCode();
    	  
          Product product = null;
   
          boolean isNew = false;
          if (code != null) {
              product = productDAO.findByCode(code);
          }
          if (product == null) {
              isNew = true;
              product = new Product();
              product.setCreateDate(new Date());
          }
          product.setCode(code);
          product.setName(productForm.getName());
          product.setPrice(productForm.getPrice());
   
          if (productForm.getFileData() != null) {
              byte[] image = null;
              try {
                  image = productForm.getFileData().getBytes();
              } catch (IOException e) {
              }
              if (image != null && image.length > 0) {
                  product.setImage(image);
              }
          }
    	  
    	  
         productDAO.save(product);
      } catch (Exception e) {
			/*
			 * Throwable rootCause = ExceptionUtils.unwrapInvocationTargetException(e)
			 * String message = rootCause.getMessage();
			 */
         model.addAttribute("errorMessage", "admin product");
         // Show product form.
         return "product";
      }
 
      return "redirect:/productList";
   }
 
   @RequestMapping(value = { "/admin/order" }, method = RequestMethod.GET)
   public String orderView(Model model, @RequestParam("orderId") String orderId) {
	   Optional<Order> order = null;
      OrderInfo orderInfo = new OrderInfo();
      if (orderId != null) {
         order = orderDAO.findById(orderId);
      }
      if (order == null) {
         return "redirect:/admin/orderList";
      }
      
          List<OrderDetail> details = this.orderDetailDAO.findByOrder(order.get());
		  orderInfo.setDetails(details);
		 
 
      model.addAttribute("orderInfo", order.get());
      
      model.addAttribute("orderDetail", details);
 
      return "order";
   }
 
}