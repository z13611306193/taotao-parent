package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.portal.pojo.Order;
import com.taotao.portal.service.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 订单Service
 * <p>Title:OrderServiceImpl</p>
 * <p>Description: </p>
 * <p>Company:www.w.com</p>
 *
 * @author ZYGisComputer
 * @version 1.0
 * @date 2019/3/14 22:26
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Value("${ORDER_BASE_URL}")
    private String ORDER_BASE_URL;

    @Value("${ORDER_CREATE_URL}")
    private String ORDER_CREATE_URL;

    @Override
    public String createOrder(Order order) {
        //调用taotao-order的服务提交订单
        String json = HttpClientUtil.doPostJson(ORDER_BASE_URL + ORDER_CREATE_URL, JsonUtils.objectToJson(order));
        //把JSON转换为TaotaoResult
        TaotaoResult result = TaotaoResult.format(json);
        if (result.getStatus() == 200) {
            Object orderId = result.getData();
            return orderId.toString();
        }
        return "";
    }
}
