//package com.qqlei.cloud.provider.user.config;
//
//import com.qqlei.security.session.LoginSessionInterceptor;
//import feign.RequestInterceptor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Enumeration;
//
///**
// * Created by 李雷 on 2018/7/13.
// */
//@Configuration
//public class UserWebMvcConfig extends WebMvcConfigurerAdapter {
//
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginSessionInterceptor()).excludePathPatterns("/userApi/**").addPathPatterns("/**");
//        super.addInterceptors(registry);
//    }
//
//    @Bean
//    public LoginSessionInterceptor loginSessionInterceptor(){
//        return new LoginSessionInterceptor();
//    }
//}
