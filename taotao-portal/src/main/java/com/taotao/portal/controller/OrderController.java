package com.taotao.portal.controller;

import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.CartService;
import com.taotao.portal.service.OrderService;
import org.joda.time.DateTime;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* 订单Controller
* <p>Title:OrderController</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/14 21:37
* @version 1.0
*/
@Controller
@RequestMapping("/order")
public class OrderController {

    @Resource
    private CartService cartServiceImpl;

    @Resource
    private OrderService orderServiceImpl;

    @RequestMapping(value = "/order-cart")
    public String showOrderCart(HttpServletRequest request,
                                HttpServletResponse response,
                                Model model){
        List<CartItem> cartItemList = cartServiceImpl.getCartItemList(request, response);
        model.addAttribute("cartList",cartItemList);
        return "order-cart";
    }

    @RequestMapping(value = "/create")
    public String createOrder(Order order,Model model){
        try {
            String orderId = orderServiceImpl.createOrder(order);
            model.addAttribute("orderId",orderId);
            model.addAttribute("payment",order.getPayment());
            //new DateTime().plusDays(3).toString("yyyy-MM-dd"):取当前时间加3天并且格式化成yyyy-MM-dd
            model.addAttribute("date",new DateTime().plusDays(3).toString("yyyy-MM-dd"));
            return "success";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("message","创建订单失败,请稍后重试...");
            return "error/exception";
        }
    }
}
