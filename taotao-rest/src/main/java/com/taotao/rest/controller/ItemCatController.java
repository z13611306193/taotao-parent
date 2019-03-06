package com.taotao.rest.controller;

import com.taotao.common.utils.JsonUtils;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
*  商品分类列表Controller
* <p>Title:ItemCatController</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/6 19:26
* @version 1.0
*/
@Controller
public class ItemCatController {

    @Resource
    private ItemCatService itemCatServiceImpl;

    @RequestMapping(value = "/itemcat/list",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getItemCatList(String callback){
        CatResult itemCatList = itemCatServiceImpl.getItemCatList();
        String json = JsonUtils.objectToJson(itemCatList);
        String result = callback + "(" + json + ");";
        return result;
    }
}
