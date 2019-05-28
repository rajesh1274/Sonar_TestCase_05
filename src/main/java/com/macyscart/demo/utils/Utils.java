package com.macyscart.demo.utils;

import javax.servlet.http.HttpServletRequest;

import com.macyscart.demo.service.CartInfoServiceImpl;

 
public class Utils {
 
   // Products in the cart, stored in Session.
   public static CartInfoServiceImpl getCartInSession(HttpServletRequest request) {
 
      CartInfoServiceImpl cartInfo = (CartInfoServiceImpl) request.getSession().getAttribute("myCart");
 
    
      if (cartInfo == null) {
         cartInfo = new CartInfoServiceImpl();
          
         request.getSession().setAttribute("myCart", cartInfo);
      }
 
      return cartInfo;
   }
 
   public static void removeCartInSession(HttpServletRequest request) {
      request.getSession().removeAttribute("myCart");
   }
 
   public static void storeLastOrderedCartInSession(HttpServletRequest request, CartInfoServiceImpl cartInfo) {
      request.getSession().setAttribute("lastOrderedCart", cartInfo);
   }
 
   public static CartInfoServiceImpl getLastOrderedCartInSession(HttpServletRequest request) {
      return (CartInfoServiceImpl) request.getSession().getAttribute("lastOrderedCart");
   }
    
}
