//package com.qqlei.cloud.provider.book.config;
//
//import com.qqlei.security.session.LoginSessionInterceptor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
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
//        registry.addInterceptor(loginSessionInterceptor()).addPathPatterns("/**");
//        super.addInterceptors(registry);
//    }
//
//    @Bean
//    public LoginSessionInterceptor loginSessionInterceptor(){
//        return new LoginSessionInterceptor();
//    }
//}
