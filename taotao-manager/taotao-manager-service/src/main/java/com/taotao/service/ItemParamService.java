package com.taotao.service;

import com.taotao.common.pojo.EUDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

public interface ItemParamService {

    TaotaoResult getItemParamById(Long cid);

    TaotaoResult insertItemParam(TbItemParam itemParam);

    EUDateGridResult getItemParamList(int page, int rows);
}
