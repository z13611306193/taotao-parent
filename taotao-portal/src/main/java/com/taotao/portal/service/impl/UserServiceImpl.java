package com.taotao.portal.service.impl;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 用户管理Service
 * <p>Title:UserServiceImpl</p>
 * <p>Description: </p>
 * <p>Company:www.w.com</p>
 *
 * @author ZYGisComputer
 * @version 1.0
 * @date 2019/3/13 19:14
 */
@Service
public class UserServiceImpl implements UserService {

    @Value("${SSO_BASE_URL}")
    public String SSO_BASE_URL;

    @Value("${SSO_USER_TOKEN}")
    private String SSO_USER_TOKEN;

    @Value("${TT_TOKEN}")
    public String TT_TOKEN;

    @Value("${SSO_PAGE_LOGIN}")
    public String SSO_PAGE_LOGIN;

    @Override
    public TbUser getUserByToken(String token) {
        try {
            //调用SSO的系统服务,根据Token取用户信息
            String json = HttpClientUtil.doGet(SSO_BASE_URL + SSO_USER_TOKEN + token);
            //把Json转换为TaotaoResult
            TaotaoResult result = TaotaoResult.formatToPojo(json, TbUser.class);
            if (result.getStatus() == 200) {
                TbUser tbUser = (TbUser) result.getData();
                return tbUser;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
