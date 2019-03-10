package com.taotao.service.impl;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.service.ContentCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
*  CMS内容管理分类Service
* <p>Title:ContentCategoryServiceImpl</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/6 21:46
* @version 1.0
*/
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

    @Resource
    private TbContentCategoryMapper tbContentCategoryMapper;

    @Override
    public List<EUTreeNode> getCategoryList(Long parentId) {
        //根据parentid查询节点列表
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andParentIdEqualTo(parentId);
        //执行查询
        List<TbContentCategory> list = tbContentCategoryMapper.selectByExample(example);
        List<EUTreeNode> resultList = new ArrayList<>();
        for (TbContentCategory tbContentCategory : list) {
            //创建一个节点
            EUTreeNode node = new EUTreeNode();
            node.setId(tbContentCategory.getId());
            node.setText(tbContentCategory.getName());
            node.setState(tbContentCategory.getIsParent()?"closed":"open");

            resultList.add(node);
        }
        return resultList;
    }

    @Override
    public TaotaoResult insertContentCategory(Long parentId, String name) {
        //创建一个pojo
        TbContentCategory contentCategory = new TbContentCategory();
        contentCategory.setName(name);
        contentCategory.setIsParent(false);
        //'状态。可选值:1(正常),2(删除)',
        contentCategory.setStatus(1);
        contentCategory.setParentId(parentId);
        contentCategory.setSortOrder(1);
        contentCategory.setCreated(new Date());
        contentCategory.setUpdated(new Date());
        //添加记录
        tbContentCategoryMapper.insert(contentCategory);
        //查看父节点的isParent列是否为true，如果不是true改成true
        TbContentCategory parentCat = tbContentCategoryMapper.selectByPrimaryKey(parentId);
        //判断是否为true
        if(!parentCat.getIsParent()) {
            parentCat.setIsParent(true);
            //更新父节点
            tbContentCategoryMapper.updateByPrimaryKey(parentCat);
        }
        //返回结果
        return TaotaoResult.ok(contentCategory);
    }

    @Override
    public TaotaoResult deleteContentCategoryById(Long id) {
        TbContentCategory deleteContenCategory = tbContentCategoryMapper.selectByPrimaryKey(id);
        tbContentCategoryMapper.deleteByPrimaryKey(id);
        TbContentCategoryExample example1 = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria1 = example1.createCriteria();
        criteria1.andParentIdEqualTo(id);
        tbContentCategoryMapper.deleteByExample(example1);
        TbContentCategoryExample example2 = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria2 = example1.createCriteria();
        criteria1.andParentIdEqualTo(deleteContenCategory.getParentId());
        List<TbContentCategory> tbContentCategories = tbContentCategoryMapper.selectByExample(example2);
        if(tbContentCategories==null || tbContentCategories.size() == 0){
            TbContentCategory tbContentCategory = new TbContentCategory();
            tbContentCategory.setIsParent(false);
            TbContentCategoryExample example3 = new TbContentCategoryExample();
            TbContentCategoryExample.Criteria criteria3 = example1.createCriteria();
            criteria1.andIdEqualTo(deleteContenCategory.getParentId());
            tbContentCategoryMapper.updateByExampleSelective(tbContentCategory,example3);
        }
        return TaotaoResult.ok();
    }

    @Override
    public TaotaoResult updateContentCategoryNameById(Long id, String name) {
        TbContentCategory tbContentCategory = new TbContentCategory();
        tbContentCategory.setName(name);
        TbContentCategoryExample example = new TbContentCategoryExample();
        TbContentCategoryExample.Criteria criteria = example.createCriteria();
        criteria.andIdEqualTo(id);
        tbContentCategoryMapper.updateByExampleSelective(tbContentCategory,example);
        return TaotaoResult.ok();
    }
}
