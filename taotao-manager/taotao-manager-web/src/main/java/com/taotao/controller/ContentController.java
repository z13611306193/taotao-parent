package com.taotao.controller;

import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 内容管理Controller
 * <p>Title:ContentController</p>
 * <p>Description: </p>
 * <p>Company:www.w.com</p>
 *
 * @author ZYGisComputer
 * @version 1.0
 * @date 2019/3/7 14:40
 */
@Controller
@RequestMapping("content")
public class ContentController {

    @Resource
    private ContentService contentServiceImpl;

    @RequestMapping(value = "/query/list", produces = "application/json;charset=utf-8")
    @ResponseBody
    public EUDateGridResult updateContentCategory(Long categoryId, @RequestParam(defaultValue = "1", value = "page") Integer page, Integer rows) {
        EUDateGridResult result = contentServiceImpl.getContentListByCategoryId(categoryId, page, rows);
        return result;
    }

    @RequestMapping(value = "/save", produces = "application/json;charset=utf-8")
    @ResponseBody
    public TaotaoResult updateContentCategory(TbContent content) {
        TaotaoResult taotaoResult = contentServiceImpl.insertContent(content);
        return taotaoResult;
    }


}
