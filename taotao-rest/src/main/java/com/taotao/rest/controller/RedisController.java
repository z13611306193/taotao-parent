package com.taotao.rest.controller;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.service.RedisService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
*  redis缓存同步Controller
* <p>Title:RedisController</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/9 23:33
* @version 1.0
*/
@Controller
@RequestMapping("/cache/sync")
public class RedisController {

    @Resource
    private RedisService redisServiceImpl;

    @RequestMapping("/content/{contentCid}")
    @ResponseBody
    public TaotaoResult contentCacheSync(@PathVariable Long contentCid){
        return redisServiceImpl.syncContent(contentCid);
    }

    @RequestMapping("/itemCat")
    @ResponseBody
    public TaotaoResult itemCatCacheSync(){
        return redisServiceImpl.syncItemCat();
    }

}
