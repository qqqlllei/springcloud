package com.qqlei.cloud.provider.user.web;

import com.qqlei.cloud.provider.user.domain.User;
import com.qqlei.cloud.provider.user.fegin.BookFegin;
import com.qqlei.cloud.provider.user.service.UserService;
import com.qqlei.security.session.SysUserAuthentication;
import com.qqlei.security.session.UserSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Administrator on 2018/7/14 0014.
 */
@RestController
public class UserController {

    @Autowired
    private BookFegin bookFegin;

    @Autowired
    private UserService userService;

    @RequestMapping("/getCurrentSessionInfo")
    String getCurrentSessionInfo(@RequestParam("name") String name){
        SysUserAuthentication sysUserAuthentication =  UserSessionContext.get();
        if(sysUserAuthentication !=null){
            name=sysUserAuthentication.getName();
        }
        return bookFegin.helloService(name);
    }

    @RequestMapping("/saveUser")
    public void saveUser(@RequestParam("id") long id,@RequestParam("name") String name){
        User user = new User();
        user.setName(name);
        user.setId(id);
        userService.saveUser(user);
    }
}
