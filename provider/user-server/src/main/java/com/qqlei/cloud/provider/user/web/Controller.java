package com.qqlei.cloud.provider.user.web;

import com.qqlei.security.session.SysUserAuthentication;
import com.qqlei.security.session.UserSessionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/4/14 0014.
 */
@RestController
@RequestMapping("/userApi")
public class Controller {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/user")
    public String user(HttpServletRequest request){
        return "success:user-server"+request.getServerPort();
    }

    @RequestMapping(value="/findUserByUsername",method= RequestMethod.GET)
    SysUserAuthentication findUserByUsername(@RequestParam("name") String name){
        SysUserAuthentication sysUserAuthentication = new SysUserAuthentication();
        sysUserAuthentication.setName(name);
        sysUserAuthentication.setPassword(passwordEncoder.encode("123456"));
        sysUserAuthentication.setId(1L);
        System.out.println("findUserByUsername");
        return sysUserAuthentication;
    }



    @RequestMapping(value="/findUserByPhoneNumber",method= RequestMethod.GET)
    SysUserAuthentication findUserByPhoneNumber(@RequestParam("phone") String phone){
        SysUserAuthentication sysUserAuthentication = new SysUserAuthentication();
        sysUserAuthentication.setName("lilei");
        sysUserAuthentication.setPhoneNumber(phone);
        sysUserAuthentication.setPassword(passwordEncoder.encode("123456"));
        sysUserAuthentication.setId(1L);
        return sysUserAuthentication;
    }



    @RequestMapping(value="/findUserByOpenId",method= RequestMethod.GET)
    SysUserAuthentication findUserByOpenId(@RequestParam("openId") String openId){
        SysUserAuthentication sysUserAuthentication = new SysUserAuthentication();
        sysUserAuthentication.setName("lilei");
        sysUserAuthentication.setUsername(openId);
        sysUserAuthentication.setPassword(passwordEncoder.encode("123456"));
        sysUserAuthentication.setId(1L);
        return sysUserAuthentication;
    }



}
