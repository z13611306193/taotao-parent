package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import com.taotao.service.ItemService;
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

    @Override
    public TaotaoResult createItem(TbItem item) {
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
        return TaotaoResult.ok() ;
    }
}
