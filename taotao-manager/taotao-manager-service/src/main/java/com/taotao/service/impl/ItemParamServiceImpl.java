package com.taotao.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.service.ItemParamService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
*  商品规格参数模板Service
* <p>Title:ItemParamServiceImpl</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/5 16:46
* @version 1.0
*/
@Service
public class ItemParamServiceImpl implements ItemParamService {

    @Resource
    private TbItemParamMapper tbItemParamMapper;

    /**
     * 根据分类ID查询模板
     * @param cid
     * @return
     */
    @Override
    public TaotaoResult getItemParamById(Long cid) {
        TbItemParamExample example = new TbItemParamExample();
        TbItemParamExample.Criteria criteria = example.createCriteria();
        criteria.andItemCatIdEqualTo(cid);
        List<TbItemParam> tbItemParams = tbItemParamMapper.selectByExampleWithBLOBs(example);
        if(tbItemParams!=null&&tbItemParams.size()>0)
            return TaotaoResult.ok(tbItemParams.get(0));
        return TaotaoResult.ok();
    }

    /**
     * 保存商品规格参数模板到数据库
     * @param itemParam
     * @return
     */
    @Override
    public TaotaoResult insertItemParam(TbItemParam itemParam) {
        //补全数据
        itemParam.setCreated(new Date());
        itemParam.setUpdated(new Date());
        tbItemParamMapper.insert(itemParam);
        return TaotaoResult.ok();
    }

    @Override
    public EUDateGridResult getItemParamList(int page, int rows) {
        //构造查询条件
        //TbItemParamExample example = new TbItemParamExample();
        //分页
        PageHelper.startPage(page,rows);
        //查询商品列表
        //List<TbItemParam> items = tbItemParamMapper.selectByExample(example);
        List<TbItemParam> items = tbItemParamMapper.selectItemParamList();
        //创建返回值对象
        EUDateGridResult result = new EUDateGridResult();
        result.setRows(items);
        //取记录总条数
        PageInfo<TbItemParam> pageInfo = new PageInfo<>(items);
        result.setTotal(pageInfo.getTotal());
        //返回数据
        return result;
    }
}
