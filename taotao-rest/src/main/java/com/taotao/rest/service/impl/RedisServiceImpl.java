package com.taotao.rest.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.ExceptionUtil;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
*  redis缓存同步Service
* <p>Title:RedisServiceImpl</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/9 23:21
* @version 1.0
*/
@Service
public class RedisServiceImpl implements RedisService {

    @Resource
    private JedisClient jedisClient;

    @Value("INDEX_CONTENT_REDIS_KEY")
    private String INDEX_CONTENT_REDIS_KEY;

    @Value("${INDEX_CATEGORY_REDIS_HKEY}")
    private String INDEX_CATEGORY_REDIS_HKEY;

    @Value("${INDEX_CATEGORY_REDIS_KEY}")
    private String INDEX_CATEGORY_REDIS_KEY;

    @Override
    public TaotaoResult syncContent(Long contentCid) {
        try {
            jedisClient.hdel(INDEX_CONTENT_REDIS_KEY, contentCid + "");
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult syncItemCat() {
        try {
            jedisClient.hdel(INDEX_CATEGORY_REDIS_HKEY,INDEX_CATEGORY_REDIS_KEY);
        } catch (Exception e) {
            e.printStackTrace();
            return TaotaoResult.build(500,ExceptionUtil.getStackTrace(e));
        }
        return TaotaoResult.ok();
    }
}
