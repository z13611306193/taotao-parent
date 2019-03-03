package com.taotao.common.pojo;

import java.util.List;

/**
*  EasyUI DataGrid 数据格式
* <p>Title:EUDateGridResult</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/3 23:19
* @version 1.0
*/
public class EUDateGridResult {

    //为了防止记录非常多使用long
    private Long total;

    //不使用T,直接使用?代表任意类型
    private List<?> rows;

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
}
