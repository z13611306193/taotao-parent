package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
*  商品管理Service
* <p>Title:ItemServiceImpl</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/3 21:03
* @version 1.0
*/
@Service
public class ItemServiceImpl implements ItemService {

    @Resource
    private TbItemMapper tbItemMapper;

    @Resource
    private TbItemDescMapper tbItemDescMapper;

    @Resource
    private TbItemParamItemMapper tbItemParamItemMapper;

    @Value("${SEARCH_IMPORTALL_URL}")
    private String SEARCH_IMPORTALL_URL;

    @Override
    public TbItem getItemById(Long id) {

        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);

        List<TbItem> tbItems = tbItemMapper.selectByExample(example);
        if (tbItems != null && tbItems.size() > 0) {
            return tbItems.get(0);
        }
        return null;
    }

    /**
     * 查询商品列表
     * @param page
     * @param rows
     * @return
     */
    @Override
    public EUDateGridResult getItemList(int page, int rows) {
        //构造查询条件
        TbItemExample example = new TbItemExample();
        //分页
        PageHelper.startPage(page,rows);
        //查询商品列表
        List<TbItem> items = tbItemMapper.selectByExample(example);
        //创建返回值对象
        EUDateGridResult result = new EUDateGridResult();
        result.setRows(items);
        //取记录总条数
        PageInfo<TbItem> pageInfo = new PageInfo<>(items);
        result.setTotal(pageInfo.getTotal());
        //返回数据
        return result;
    }

    /**
     * 添加商品
     * @param item
     * @param desc
     * @return
     * @throws Exception
     */
    @Override
    public TaotaoResult createItem(TbItem item,String desc,String itemParam) throws Exception{
        //补全item
        //生成商品ID
        long itemId = IDUtils.genItemId();
        item.setId(itemId);
        //商品状态 1-正常 2-下架 3-删除
        item.setStatus((byte) 1);
        //创建时间/更新时间
        item.setCreated(new Date());
        item.setUpdated(new Date());
        //插入到数据库
        tbItemMapper.insert(item);
        TaotaoResult result = insertItemDesc(itemId, desc);
        if(result.getStatus() != 200)
            throw new Exception();
        result = insertItemParamItem(itemId,itemParam);
        if(result.getStatus() != 200)
            throw new Exception();

        try {
            //同步到索引库
            HttpClientUtil.doGet(SEARCH_IMPORTALL_URL);
            System.out.println("同步solr数据");
        } catch (Exception e) {
            e.printStackTrace();
            //发送邮件或者短信通知管理员,或者技术
        }

        return TaotaoResult.ok() ;
    }

    /**
     * 插入商品描述
     * @param desc
     * @return
     */
    private TaotaoResult insertItemDesc(Long itemId, String desc){
        TbItemDesc itemDesc = new TbItemDesc();
        itemDesc.setItemId(itemId);
        itemDesc.setItemDesc(desc);
        itemDesc.setCreated(new Date());
        itemDesc.setUpdated(new Date());
        tbItemDescMapper.insert(itemDesc);
        return TaotaoResult.ok();
    }

    /**
     * 插入商品规格
     * @param itemId
     * @param itemParam
     * @return
     */
    private TaotaoResult insertItemParamItem(Long itemId,String itemParam){
        TbItemParamItem tbItemParamItem = new TbItemParamItem();
        tbItemParamItem.setItemId(itemId);
        tbItemParamItem.setParamData(itemParam);
        tbItemParamItem.setCreated(new Date());
        tbItemParamItem.setUpdated(new Date());
        tbItemParamItemMapper.insert(tbItemParamItem);
        return TaotaoResult.ok();
    }
}
