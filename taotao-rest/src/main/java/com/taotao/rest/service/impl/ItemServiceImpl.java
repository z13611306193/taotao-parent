package com.taotao.rest.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.service.ItemService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品信息管理Service
 * <p>Title:ItemServiceImpl</p>
 * <p>Description: </p>
 * <p>Company:www.w.com</p>
 *
 * @author ZYGisComputer
 * @version 1.0
 * @date 2019/3/11 15:39
 */
@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    private TbItemMapper tbItemMapper;

    @Resource
    private JedisClient jedisClient;

    @Resource
    private TbItemDescMapper tbItemDescMapper;

    @Resource
    private TbItemParamItemMapper tbItemParamItemMapper;

    @Value("${REDIS_ITEM_KEY}")
    private String REDIS_ITEM_KEY;

    @Value("${REDIS_ITEM_EXPIRE}")
    private Integer REDIS_ITEM_EXPIRE;

    @Override
    public TaotaoResult getItemBaseInfo(Long itemId) {
        try {
            //添加缓存逻辑
            //从缓存中读取信息,商品ID对应的信息
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":base");
            //判断是否有值
            if (!StringUtils.isBlank(json)) {
                //把json转换成java对象
                TbItem item = JsonUtils.jsonToPojo(json, TbItem.class);
                return TaotaoResult.ok(item);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //根据商品id查询商品信息
        TbItem item = tbItemMapper.selectByPrimaryKey(itemId);

        try {
            //把商品信息写入到缓存
            jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":base", JsonUtils.objectToJson(item));
            //设置key的有效期
            jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":base", REDIS_ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //使用TaotaoResult包装一下
        return TaotaoResult.ok(item);
    }


    @Override
    public TaotaoResult getItemDesc(Long itemId) {

        try {
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":desc");
            if(!StringUtils.isBlank(json)){
                TbItemDesc itemDesc = JsonUtils.jsonToPojo(json, TbItemDesc.class);
                return TaotaoResult.ok(itemDesc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        TbItemDesc itemDesc = tbItemDescMapper.selectByPrimaryKey(itemId);

        try {
            jedisClient.set(REDIS_ITEM_KEY+":"+itemId+":desc",JsonUtils.objectToJson(itemDesc));
            jedisClient.expire(REDIS_ITEM_KEY+":"+itemId+":desc",REDIS_ITEM_EXPIRE);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return TaotaoResult.ok(itemDesc);
    }

    @Override
    public TaotaoResult getItemParam(Long itemId) {
        //添加缓存
        try {
            //添加缓存逻辑
            //从缓存中取商品信息，商品id对应的信息
            String json = jedisClient.get(REDIS_ITEM_KEY + ":" + itemId + ":param");
            //判断是否有值
            if (!StringUtils.isBlank(json)) {
                //把json转换成java对象
                TbItemParamItem paramItem = JsonUtils.jsonToPojo(json, TbItemParamItem.class);
                return TaotaoResult.ok(paramItem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //根据商品id查询规格参数
        //设置查询条件
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        //执行查询
        List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(example);
        if (list != null && list.size()>0) {
            TbItemParamItem paramItem = list.get(0);
            try {
                //把商品信息写入缓存
                jedisClient.set(REDIS_ITEM_KEY + ":" + itemId + ":param", JsonUtils.objectToJson(paramItem));
                //设置key的有效期
                jedisClient.expire(REDIS_ITEM_KEY + ":" + itemId + ":param", REDIS_ITEM_EXPIRE);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return TaotaoResult.ok(paramItem);
        }
        return TaotaoResult.build(400, "无此商品规格");
    }
}
