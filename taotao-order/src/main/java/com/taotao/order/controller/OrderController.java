package com.taotao.order.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.order.pojo.Order;
import com.taotao.order.service.OrderService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
*  订单Controller
* <p>Title:OrderController</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/14 20:32
* @version 1.0
*/
@Controller
public class OrderController {

    @Resource
    private OrderService orderServiceImpl;

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public TaotaoResult createOrder(@RequestBody Order order){
        try {
            TaotaoResult result = orderServiceImpl.createOrder(order, order.getOrderItems(), order.getOrderShipping());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
    }

}
