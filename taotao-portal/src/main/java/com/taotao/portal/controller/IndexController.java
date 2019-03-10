package com.taotao.portal.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.portal.service.ContentService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
*  页面展示Controller
* <p>Title:IndexController</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/6 16:16
* @version 1.0
*/
@Controller
public class IndexController {

    @Resource
    private ContentService contentServiceImpl;

    @RequestMapping("/index")
    public String showIndex(Model model) {
        String adJson = contentServiceImpl.getContentList();
        model.addAttribute("ad1", adJson);

        return "index";
    }


    @RequestMapping(value = "/httpclient/post",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
    @ResponseBody
    public String testPost(String username,String password){
        System.out.println(username+password);
        //return TaotaoResult.ok(username+password);
        return "{ username:"+username+",password:"+password+"}";
    }
}
