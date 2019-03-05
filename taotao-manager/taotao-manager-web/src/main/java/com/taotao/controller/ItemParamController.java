package com.taotao.controller;

import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
*  商品规格参数模板管理Controller
* <p>Title:ItemParamController</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/5 16:54
* @version 1.0
*/
@Controller
@RequestMapping("/item/param")
public class ItemParamController {

    @Resource
    private ItemParamService itemParamServiceImpl;

    @RequestMapping(value = "/query/itemcatid/{itemCatId}",produces = "application/json;charset=utf-8")
    @ResponseBody
    public TaotaoResult getItemParamByCid(@PathVariable Long itemCatId){
        TaotaoResult result = itemParamServiceImpl.getItemParamById(itemCatId);
        return result;
    }

    @RequestMapping(value = "/save/{cid}",produces = "application/json;charset=utf-8")
    @ResponseBody
    public TaotaoResult getItemParamByCid(@PathVariable Long cid,String paramData){
        TbItemParam itemParam = new TbItemParam();
        itemParam.setItemCatId(cid);
        itemParam.setParamData(paramData);
        TaotaoResult result = itemParamServiceImpl.insertItemParam(itemParam);
        return result;
    }

    @RequestMapping(value = "/list",produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDateGridResult getItemParamList(Integer page,Integer rows){
        EUDateGridResult itemParamList = itemParamServiceImpl.getItemParamList(page, rows);
        return itemParamList;
    }





}
