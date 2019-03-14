package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.CookieUtils;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.CartItem;
import com.taotao.portal.service.CartService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
*  购物车Service
* <p>Title:CartServiceImpl</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/13 20:24
* @version 1.0
*/
@Service
public class CartServiceImpl implements CartService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${ITEM_INFO_URL}")
    private String ITEM_INFO_URL;

    @Value("${TT_CART}")
    private String TT_CART;

    /**
     * 添加购物车商品
     * @param itemId
     * @param num
     * @return
     */
    @Override
    public TaotaoResult addCartItem(Long itemId, int num,
                                    HttpServletRequest request,
                                    HttpServletResponse response) {
        //取商品信息
        CartItem cartItem = null;
        //取购物车商品列表
        List<CartItem> cartItemList = getCartItemList(request);
        //判断购物车列表中是否存在此商品
        for (CartItem cItem:cartItemList){
            if (cItem.getId()==itemId) {
                //增加商品数量
                cItem.setNum(cItem.getNum()+num);
                cartItem = cItem;
                break;
            }
        }
        if (cartItem==null) {
            cartItem = new CartItem();
            //根据商品ID查询商品基本信息
            String json = HttpClientUtil.doGet(REST_BASE_URL + ITEM_INFO_URL + itemId);
            //把Json转换为Java对象
            TaotaoResult result = TaotaoResult.formatToPojo(json, TbItem.class);
            if(result.getStatus()==200){
                TbItem item = (TbItem) result.getData();
                cartItem.setId(item.getId());
                cartItem.setTitle(item.getTitle());
                cartItem.setImage(item.getImage()==null?"":item.getImage().split(",")[0]);
                cartItem.setNum(num);
                cartItem.setPrice(item.getPrice());
            }
            //添加到购物车列表
            cartItemList.add(cartItem);
        }
        //把购物车列表重新写回Cookie
        CookieUtils.setCookie(request,response,TT_CART,JsonUtils.objectToJson(cartItemList),true);
        return TaotaoResult.ok();
    }

    /**
     * 从Cookie中取商品列表
     * @return
     */
    private List<CartItem> getCartItemList(HttpServletRequest request){
        //从Cookie中取商品列表,解码:默认使用UTF-8编码
        String cartJson = CookieUtils.getCookieValue(request, TT_CART, true);
        if(cartJson == null)
            return new ArrayList<>();
        try {
            //把Json转换成商品列表
            List<CartItem> list = JsonUtils.jsonToList(cartJson, CartItem.class);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    /**
     * 查看购物车
     * @param request
     * @param response
     * @return
     */
    @Override
    public List<CartItem> getCartItemList(HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> list = getCartItemList(request);
        return list;
    }

    /**
     * 修改购物车商品数量
     * @param itemId
     * @param num
     * @param request
     * @param response
     * @return
     */
    @Override
    public TaotaoResult updateCartItemNum(Long itemId, int num, HttpServletRequest request, HttpServletResponse response) {
        List<CartItem> cartItemList = getCartItemList(request);
        for (CartItem cItem:cartItemList){
            if (cItem.getId()==itemId) {
                //增加商品数量
                cItem.setNum(num);
                break;
            }
        }
        CookieUtils.setCookie(request,response,TT_CART,JsonUtils.objectToJson(cartItemList),true);
        return TaotaoResult.ok();
    }

    /**
     * 删除购物车商品
     * @param itemId
     * @param request
     * @param response
     * @return
     */
    @Override
    public TaotaoResult deleteCartItemById(Long itemId,
                                           HttpServletRequest request,
                                           HttpServletResponse response) {
        List<CartItem> cartItemList = getCartItemList(request);
        for (CartItem cItem:cartItemList){
            if (cItem.getId()==itemId) {
                cartItemList.remove(cItem);
                break;
            }
        }
        CookieUtils.setCookie(request,response,TT_CART,JsonUtils.objectToJson(cartItemList),true);
        return TaotaoResult.ok();
    }
}
