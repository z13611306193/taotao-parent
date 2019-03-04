package com.taotao.controller;

import com.taotao.common.utils.JsonUtils;
import com.taotao.service.PictureService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Map;

/**
*  上传图片处理Controller
* <p>Title:PictureController</p>
* <p>Description: </p>
* <p>Company:www.w.com</p>
* @author ZYGisComputer
* @date 2019/3/4 17:22
* @version 1.0
*/
@Controller
public class PictureController {

    @Resource
    private PictureService pictureServiceImpl;

    @RequestMapping(value = "/pic/upload",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String pictureUpload(MultipartFile uploadFile){
        //为了保证浏览器的兼容性,把map转换为json字符串再返回
        Map result = pictureServiceImpl.uploadPicture(uploadFile);
        String json = JsonUtils.objectToJson(result);
        return json;
    }

}
