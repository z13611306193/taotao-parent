package com.taotao.service;

import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;


public interface ContentService {

    EUDateGridResult getContentListByCategoryId(Long categoryId,Integer page, Integer rows);

    TaotaoResult insertContent(TbContent content);
}
