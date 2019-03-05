package com.taotao.controller;

import com.taotao.service.ItemParamItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
*  展示商品规格参数Controller
* <p>Title:ItemParamItemController</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/5 21:52
* @version 1.0
*/
@Controller
public class ItemParamItemController {

    @Resource
    private ItemParamItemService itemParamItemServiceImpl;

    @RequestMapping(value = "/showItem/{itemId}")
    public String showItemParam(@PathVariable Long itemId, Model model){
        String itemParamHtml = itemParamItemServiceImpl.getItemParamItemByItemId(itemId);
        model.addAttribute("itemParam",itemParamHtml);
        return "item";
    }

}
