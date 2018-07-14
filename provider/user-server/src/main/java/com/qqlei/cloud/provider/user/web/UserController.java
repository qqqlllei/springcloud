package com.qqlei.cloud.provider.user.web;

import com.qqlei.cloud.provider.user.fegin.BookFegin;
import com.qqlei.security.session.SysUserAuthentication;
import com.qqlei.security.session.UserSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/7/14 0014.
 */
@RestController
public class UserController {

    @Autowired
    private BookFegin bookFegin;

    @RequestMapping("/getCurrentSessionInfo")
    SysUserAuthentication getCurrentSessionInfo(){
        SysUserAuthentication sysUserAuthentication =  UserSessionContext.get();
        System.out.println(bookFegin.helloService(sysUserAuthentication.getName()));
        return sysUserAuthentication;
    }
}
