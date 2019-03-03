package com.taotao.controller;

import com.taotao.pojo.TbItem;
import com.taotao.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
*  商品管理Controller
* <p>Title:ItemController</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/3 21:06
* @version 1.0
*/
@Controller
public class ItemController {

    @Resource
    private ItemService itemServiceImpl;

    @RequestMapping(value = "/item/{itemId}",produces = "application/json;charset=utf-8")
    @ResponseBody
    public TbItem getItemById(@PathVariable Long itemId){
        TbItem item = itemServiceImpl.getItemById(itemId);
        return item;
    }

}
