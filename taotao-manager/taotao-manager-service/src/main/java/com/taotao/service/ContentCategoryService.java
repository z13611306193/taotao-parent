package com.taotao.service;

import com.taotao.common.pojo.EUTreeNode;
import com.taotao.common.pojo.TaotaoResult;

import java.util.List;

public interface ContentCategoryService {

    List<EUTreeNode> getCategoryList(Long parentId);

    TaotaoResult insertContentCategory(Long parentId,String name);

    TaotaoResult deleteContentCategoryById(Long id);

    TaotaoResult updateContentCategoryNameById(Long id,String name);
}
