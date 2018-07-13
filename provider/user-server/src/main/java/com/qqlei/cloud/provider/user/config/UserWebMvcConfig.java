package com.qqlei.cloud.provider.user.config;

import com.qqlei.cloud.provider.user.interceptor.UserSessionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by 李雷 on 2018/7/13.
 */
@Configuration
public class UserWebMvcConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private UserSessionInterceptor userSessionInterceptor;
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(userSessionInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
}
