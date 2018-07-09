package com.qqlei.cloud.provider.user.web;

import org.springframework.web.bind.annotation.RequestMapping;
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
}
