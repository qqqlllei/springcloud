package com.qqlei.cloud.provider.user.fegin;

import com.qqlei.security.session.FeignHeaderConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by 李雷 on 2018/7/9.
 */

@FeignClient(value = "book-server",configuration = {FeignHeaderConfiguration.class})
public interface BookFegin {
    @RequestMapping(value="/book",method= RequestMethod.GET)
    String helloService(@RequestParam("name") String name);
}
