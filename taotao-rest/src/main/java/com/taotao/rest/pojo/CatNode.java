package com.taotao.rest.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
* 分类节点
* <p>Title:CatNode</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/6 18:44
* @version 1.0
*/
public class CatNode {

    /**
     * @JsonProperty 生成JSON字符串的时候转换的KEY
     */
    @JsonProperty("n")
    private String name;

    @JsonProperty("u")
    private String url;

    @JsonProperty("i")
    private List<?> item;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<?> getItem() {
        return item;
    }

    public void setItem(List<?> item) {
        this.item = item;
    }
}
