package com.taotao.controller;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
*  CMS内容管理分类Controller
* <p>Title:ContentCategoryController</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/6 21:50
* @version 1.0
*/
@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

    @Resource
    private ContentCategoryService contentCategoryServiceImpl;

    @RequestMapping(value = "/list",produces = "application/json;charset=utf-8")
    @ResponseBody
    public List<EUTreeNode> getContentCatList(@RequestParam(value="id", defaultValue="0")Long parentId) {
        List<EUTreeNode> list = contentCategoryServiceImpl.getCategoryList(parentId);
        return list;
    }

    @RequestMapping(value = "/create",produces = "application/json;charset=utf-8")
    @ResponseBody
    public TaotaoResult createContentCategory(Long parentId, String name) {
        TaotaoResult result = contentCategoryServiceImpl.insertContentCategory(parentId, name);
        return result;
    }

    @RequestMapping(value = "/delete",produces = "application/json;charset=utf-8")
    @ResponseBody
    public TaotaoResult deleteContentCategory(Long id) {
        TaotaoResult result = contentCategoryServiceImpl.deleteContentCategoryById(id);
        return result;
    }

    @RequestMapping(value = "/update",produces = "application/json;charset=utf-8")
    @ResponseBody
    public TaotaoResult updateContentCategory(Long id,String name) {
        TaotaoResult result = contentCategoryServiceImpl.updateContentCategoryNameById(id, name);
        return result;
    }

}
