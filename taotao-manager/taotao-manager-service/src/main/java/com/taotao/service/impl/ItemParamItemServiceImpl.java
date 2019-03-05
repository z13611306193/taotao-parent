package com.taotao.service.impl;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.service.ItemParamItemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
*  商品规格参数数据Service
* <p>Title:ItemParamItemServiceImpl</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/5 21:17
* @version 1.0
*/
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {

    @Resource
    private TbItemParamItemMapper tbItemParamItemMapper;

    @Override
    public String getItemParamItemByItemId(Long itemId) {
        TbItemParamItemExample tbItemParamItemExample = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = tbItemParamItemExample.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = tbItemParamItemMapper.selectByExampleWithBLOBs(tbItemParamItemExample);
        if(list == null || list.size() == 0)
            return "";
        //取规格参数
        TbItemParamItem itemParamItem = list.get(0);
        String paramData = itemParamItem.getParamData();
        //把规格参数JSON数据转换为java对象
        List<Map> jsonList = JsonUtils.jsonToList(paramData, Map.class);
        //生成HTML片段
        StringBuffer sb = new StringBuffer();
        sb.append("<div data-tab=\"item\" class=\"hide\" style=\"display: block;\">\n");
        sb.append("    <div class=\"Ptable\">\n");
        sb.append("        <div class=\"Ptable-item\">\n");
        for (Map m1:jsonList) {
            sb.append("            <h3>"+m1.get("group")+"</h3>\n");
            sb.append("            <dl>\n");
            sb.append("                <dl class=\"clearfix\" style=\"margin:0\">\n");
            List<Map> list2 = (List<Map>)m1.get("params");
            for (Map m2 : list2) {
                sb.append("                    <dt>"+m2.get("k")+"</dt><dd>"+m2.get("v")+"</dd>\n");
            }
            sb.append("                </dl>\n");
            sb.append("            </dl>\n");
        }
        sb.append("        </div>\n");
        sb.append("    </div>\n");
        sb.append("</div>");
        return sb.toString();
    }
}
