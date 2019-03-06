package com.taotao.rest.pojo;

import java.util.List;

/**
*  用于返回的分类节点集合配合CatNode使用
* <p>Title:CatResult</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/6 18:49
* @version 1.0
*/
public class CatResult {

    private List<?> data;

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
