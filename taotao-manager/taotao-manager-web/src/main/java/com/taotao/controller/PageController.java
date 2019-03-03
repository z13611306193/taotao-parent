package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
*  页面跳转
* <p>Title:PageController</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/3 22:00
* @version 1.0
*/
@Controller
public class PageController {

    /**
     * 打开首页
     * @return
     */
    @RequestMapping(value = "/")
    public String showIndex(){
        return "index";
    }

    /**
     * 展示其他页面
     *  技术点:通过传入的名称,配合视图解析器直接跳转
     *  就不用有多少页面写多少Controller,提高开发效率
     * @param page
     * @return
     */
    @RequestMapping(value = "/{page}")
    public String showPages(@PathVariable String page){
        return page;
    }

}
