package com.qqlei.security.session;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 李雷 on 2018/7/13.
 */
@Component
public class LoginSessionInterceptor implements HandlerInterceptor {

    private static String USER_SESSION="userSession";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String userSession = request.getHeader(USER_SESSION);
        SysUserAuthentication userAuthentication =JSONObject.parseObject(userSession, SysUserAuthentication.class);
        UserSessionContext.set(userAuthentication);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserSessionContext.clear();
    }
}
