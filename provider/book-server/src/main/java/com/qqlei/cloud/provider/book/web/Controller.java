package com.qqlei.cloud.provider.book.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/4/14 0014.
 */
@RestController
public class Controller {

    @RequestMapping("/book")
    public String user(HttpServletRequest request){
        String time = request.getParameter("name");
        return "success:book-server"+request.getServerPort()+":time="+time;
    }
}
