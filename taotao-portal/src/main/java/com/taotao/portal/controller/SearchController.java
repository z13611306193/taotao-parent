package com.taotao.portal.controller;

import com.taotao.portal.pojo.SearchResult;
import com.taotao.portal.service.SearchService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

/**
*  商品搜索Controller
* <p>Title:SearchController</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/11 11:24
* @version 1.0
*/
@Controller
public class SearchController {

    @Resource
    private SearchService searchServiceImpl;

    @RequestMapping("/search")
    public String search(@RequestParam("q")String queryString, @RequestParam(defaultValue="1")Integer page, Model model) {
        if (queryString != null) {
            try {
                queryString = new String(queryString.getBytes("iso8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        SearchResult searchResult = searchServiceImpl.search(queryString, page);
        //向页面传递参数
        model.addAttribute("query", queryString);
        model.addAttribute("totalPages", searchResult.getPageCount());
        model.addAttribute("itemList", searchResult.getItemList());
        model.addAttribute("page", page);

        return "search";

    }

}
