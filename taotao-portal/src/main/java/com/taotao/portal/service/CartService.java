package com.taotao.portal.service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.pojo.CartItem;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface CartService {
    TaotaoResult addCartItem(Long itemId, int num,
                             HttpServletRequest request,
                             HttpServletResponse response);

    List<CartItem> getCartItemList(HttpServletRequest request,
                                   HttpServletResponse response);

    TaotaoResult updateCartItemNum(Long itemId, int num,
                                   HttpServletRequest request,
                                   HttpServletResponse response);

    TaotaoResult deleteCartItemById(Long itemId,
                                    HttpServletRequest request,
                                    HttpServletResponse response);
}
