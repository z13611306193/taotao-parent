package com.taotao.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
*  测试分页插件
* <p>Title:TestPageHelper</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/3 22:52
* @version 1.0
*/
public class TestPageHelper {

    /**
     * 测试分页插件
     */
    @Test
    public void test(){
        //创建一个Spring容器
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
        //从Spring容器中获得Mapper的代理对象
        TbItemMapper mapper = applicationContext.getBean(TbItemMapper.class);
        //执行查询并分页
        TbItemExample example = new TbItemExample();
        //分页处理
        PageHelper.startPage(1,10 );
        //取商品列表
        List<TbItem> items = mapper.selectByExample(example);
        items.forEach((item) -> System.out.println(item.getTitle()) );
        //取分页信息
        PageInfo<TbItem> pageInfo = new PageInfo<>(items);
        long total = pageInfo.getTotal();
        System.out.println(total);
    }
}
