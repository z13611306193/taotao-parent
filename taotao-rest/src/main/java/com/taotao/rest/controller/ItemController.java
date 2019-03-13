package com.taotao.rest.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 商品信息管理Controller
 * <p>Title:ItemController</p>
 * <p>Description: </p>
 * <p>Company:www.w.com</p>
 *
 * @author ZYGisComputer
 * @version 1.0
 * @date 2019/3/11 15:43
 */
@Controller
@RequestMapping("item")
public class ItemController {

    @Resource
    private ItemService itemServiceImpl;

    @RequestMapping("/info/{itemId}")
    @ResponseBody
    public TaotaoResult getItemBaseInfo(@PathVariable Long itemId) {
        TaotaoResult result = itemServiceImpl.getItemBaseInfo(itemId);
        return result;
    }

    @RequestMapping("/desc/{itemId}")
    @ResponseBody
    public TaotaoResult getItemDesc(@PathVariable Long itemId) {
        TaotaoResult result = itemServiceImpl.getItemDesc(itemId);
        return result;
    }

    @RequestMapping("/param/{itemId}")
    @ResponseBody
    public TaotaoResult getItemParam(@PathVariable Long itemId) {
        TaotaoResult result = itemServiceImpl.getItemParam(itemId);
        return result;
    }


}
