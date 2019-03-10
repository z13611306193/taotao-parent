package com.taotao.rest.service.impl;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.rest.dao.JedisClient;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
*  商品分类服务
* <p>Title:ItemCatServiceImpl</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/6 18:54
* @version 1.0
*/
@Service
public class ItemCatServiceImpl implements ItemCatService {

    @Resource
    private TbItemCatMapper tbItemCatMapper;

    @Resource
    private JedisClient jedisClient;

    @Value("${INDEX_CATEGORY_REDIS_HKEY}")
    private String INDEX_CATEGORY_REDIS_HKEY;

    @Value("${INDEX_CATEGORY_REDIS_KEY}")
    private String INDEX_CATEGORY_REDIS_KEY;

    @Override
    public CatResult getItemCatList() {
        //从缓存中读取商品分类
        try {
            String result = jedisClient.hget(INDEX_CATEGORY_REDIS_HKEY, INDEX_CATEGORY_REDIS_KEY);
            if(!StringUtils.isBlank(result)){
                CatResult catResult = JsonUtils.jsonToPojo(result, CatResult.class);
                return catResult;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        CatResult catResult = new CatResult();
        catResult.setData(getCatList(0L));

        //插入到缓存中
        try {
            String cacheString = JsonUtils.objectToJson(catResult);
            jedisClient.hset(INDEX_CATEGORY_REDIS_HKEY,INDEX_CATEGORY_REDIS_KEY,cacheString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return catResult;
    }

    /**
     * 根据ParentId递归查询所有子分类
     * @param parentId
     * @return
     */
    private List<?> getCatList(Long parentId){
        //创建查询条件
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
        //返回值list
        List resultList = new ArrayList<>();
        //向list中添加节点
        //控制数据
        int count = 0;
        for (TbItemCat tbItemCat : list) {
            //判断是否为父节点
            if (tbItemCat.getIsParent()) {
                CatNode catNode = new CatNode();
                if (parentId == 0) {
                    catNode.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
                } else {
                    catNode.setName(tbItemCat.getName());
                }
                catNode.setUrl("/products/"+tbItemCat.getId()+".html");
                catNode.setItem(getCatList(tbItemCat.getId()));

                resultList.add(catNode);

                //控制数据第一层只取14条
                count ++ ;
                if(parentId == 0&&count >= 14){
                    break;
                }

                //如果是叶子节点
            } else {
                resultList.add("/products/"+tbItemCat.getId()+".html|" + tbItemCat.getName());
            }
        }
        return resultList;
    }
}
