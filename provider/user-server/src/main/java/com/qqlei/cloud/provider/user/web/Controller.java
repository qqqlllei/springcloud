package com.qqlei.cloud.provider.user.web;

import com.qqlei.cloud.provider.user.security.vo.SysUserAuthentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/4/14 0014.
 */
@RestController
public class Controller {

    @RequestMapping("/user")
    public String user(HttpServletRequest request){
        return "success:user-server"+request.getServerPort();
    }

    @RequestMapping(value="/findUserByUsername",method= RequestMethod.GET)
    SysUserAuthentication findUserByUsername(@RequestParam("name") String name){
        SysUserAuthentication sysUserAuthentication = new SysUserAuthentication();
        sysUserAuthentication.setName(name);
        sysUserAuthentication.setPassword("123456");
        sysUserAuthentication.setId(1L);
        return sysUserAuthentication;
    }
}
