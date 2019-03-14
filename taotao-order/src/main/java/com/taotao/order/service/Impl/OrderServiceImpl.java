package com.taotao.order.service.Impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbOrderItemMapper;
import com.taotao.mapper.TbOrderMapper;
import com.taotao.mapper.TbOrderShippingMapper;
import com.taotao.order.dao.JedisClient;
import com.taotao.order.service.OrderService;
import com.taotao.pojo.TbOrder;
import com.taotao.pojo.TbOrderItem;
import com.taotao.pojo.TbOrderShipping;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 订单管理Service
 * <p>Title:OrderServiceImpl</p>
 * <p>Description: </p>
 * <p>Company:www.w.com</p>
 *
 * @author ZYGisComputer
 * @version 1.0
 * @date 2019/3/14 19:23
 */
@Service
public class OrderServiceImpl implements OrderService {

    @Resource
    private TbOrderMapper tbOrderMapper;

    @Resource
    private TbOrderItemMapper tbOrderItemMapper;

    @Resource
    private TbOrderShippingMapper tbOrderShippingMapper;

    @Resource
    private JedisClient jedisClient;

    @Value("ORDER_GEN_KEY")
    private String ORDER_GEN_KEY;

    @Value("${ORDER_INIT_ID}")
    private String ORDER_INIT_ID;

    @Value("${ORDER_DETAIL_GEN_KEY}")
    private String ORDER_DETAIL_GEN_KEY;

    @Override
    public TaotaoResult createOrder(TbOrder order, List<TbOrderItem> itemList, TbOrderShipping orderShipping) {
        //向订单表中插入记录
        //获得订单号
        String string = jedisClient.get(ORDER_GEN_KEY);
        if (StringUtils.isBlank(string)) {
            jedisClient.set(ORDER_GEN_KEY, ORDER_INIT_ID);
        }
        Long orderId = jedisClient.incr(ORDER_GEN_KEY);
        //补全订单POJO属性
        order.setOrderId(orderId + "");
        //状态：1、未付款，2、已付款，3、未发货，4、已发货，5、交易成功，6、交易关闭
        order.setStatus(1);
        Date date = new Date();
        order.setCreateTime(date);
        order.setUpdateTime(date);
        //0:未评价,1:已评价
        order.setBuyerRate(0);
        //向订单表插入数据
        tbOrderMapper.insert(order);
        //插入订单明细
        for (TbOrderItem orderItem : itemList) {
            //获取订单明细id
            Long orderDetailId = jedisClient.incr(ORDER_DETAIL_GEN_KEY);
            //补全订单明细POJO属性
            orderItem.setId(orderDetailId+"");
            orderItem.setOrderId(orderId+"");
            //插入到订单明细表
            tbOrderItemMapper.insert(orderItem);
        }
        //插入物流表
        //补全物流POJO属性
        orderShipping.setOrderId(orderId+"");
        orderShipping.setCreated(date);
        orderShipping.setUpdated(date);
        tbOrderShippingMapper.insert(orderShipping);
        //返回TaotaoResult
        return TaotaoResult.ok(orderId);
    }
}
