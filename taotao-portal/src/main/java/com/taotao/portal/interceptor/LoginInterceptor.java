package com.taotao.portal.interceptor;

import com.taotao.common.utils.CookieUtils;
import com.taotao.pojo.TbUser;
import com.taotao.portal.service.UserService;
import com.taotao.portal.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {



    @Resource
    private UserServiceImpl userServiceImpl;
    /**
     * Handler执行之前
     * 返回值决定这个Handler是否执行
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //判断用户是否登录
        //从Cookie中取Token
        String token = CookieUtils.getCookieValue(request, userServiceImpl.TT_TOKEN);
        //根据Token换取用户信息,调用sso系统的接口
        TbUser user = userServiceImpl.getUserByToken(token);
        //取不到用户信息
        if(null == user){
            // 跳转到登录页面,把用户请求的URL作为参数传递给登录页面
            response.sendRedirect(userServiceImpl.SSO_BASE_URL+userServiceImpl.SSO_PAGE_LOGIN+"?" +
                    "redirect="+request.getRequestURL());
            //返回FALSE
            return false;
        }
        //取到用户信息,放行
        //返回TRUE
        return true;
    }

    /**
     * 返回ModelAndView之前
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    /**
     * 返回ModelAndView之后
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
