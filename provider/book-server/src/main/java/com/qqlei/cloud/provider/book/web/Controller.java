package com.qqlei.cloud.provider.book.web;

import com.alibaba.fastjson.JSONObject;
import com.qqlei.security.session.SysUserAuthentication;
import com.qqlei.security.session.UserSessionContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by Administrator on 2018/4/14 0014.
 */
@RestController
public class Controller {

    @RequestMapping("/book")
    public String user(HttpServletRequest request) throws UnknownHostException {
        String time = request.getParameter("name");
        SysUserAuthentication session =  UserSessionContext.get();

//        try {
//            Thread.sleep(1000L*60);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        System.out.println("book-server: ip="+ InetAddress.getLocalHost().getHostAddress());
        return "success:book-server"+request.getServerPort()+":time="+time+":session"+ JSONObject.toJSONString(session);
    }
}
