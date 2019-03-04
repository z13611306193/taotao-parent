package com.taotao.controller;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.service.ItemCatService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
*  商品分类管理Controller
* <p>Title:ItemCatController</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/4 10:29
* @version 1.0
*/
@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

    @Resource
    private ItemCatService itemCatServiceImpl;

    @RequestMapping(value = "/list",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<EUTreeNode> getCatList(@RequestParam(value = "id",defaultValue = "0") Long parentId){
        List<EUTreeNode> list = itemCatServiceImpl.getCatList(parentId);
        return list;
    }

}
