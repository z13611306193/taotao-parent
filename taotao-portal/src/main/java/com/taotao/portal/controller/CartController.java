package com.taotao.portal.controller;


import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
*  购物车Controller
* <p>Title:CartController</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/13 21:33
* @version 1.0
*/
@Controller
@RequestMapping("/cart")
public class CartController {

    @Resource
    private CartService cartServiceImpl;

    @RequestMapping(value = "/add/{itemId}")
    public String addCartItem(@PathVariable Long itemId,
                              @RequestParam(defaultValue = "1")Integer num,
                              HttpServletRequest request,
                              HttpServletResponse response){
        TaotaoResult result = cartServiceImpl.addCartItem(itemId, num, request, response);
        return "redirect:/cart/success.html";
    }

    @RequestMapping("/success")
    public String showCartSuccess(){
        return "cartSuccess";
    }

    @RequestMapping("/cart")
    public String showCart(HttpServletRequest request,
                           HttpServletResponse response,
                           Model model){
        List<CartItem> cartItemList = cartServiceImpl.getCartItemList(request, response);
        model.addAttribute("cartList",cartItemList);
        return "cart";
    }

    @RequestMapping(value = "/update/{itemId}")
    @ResponseBody
    public TaotaoResult updateCartItemNum(@PathVariable Long itemId,
                              Integer num,
                              HttpServletRequest request,
                              HttpServletResponse response){
        TaotaoResult result = cartServiceImpl.updateCartItemNum(itemId, num, request, response);
        return result;
    }

    @RequestMapping(value = "/delete/{itemId}")
    public String delCartItemById(@PathVariable Long itemId,
                                  HttpServletRequest request,
                                  HttpServletResponse response){
        cartServiceImpl.deleteCartItemById(itemId, request, response);
        return "redirect:/cart/cart.html";
    }
}
