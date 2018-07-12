package com.qqlei.cloud.gateway.fegin;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by 李雷 on 2018/7/12.
 */
@FeignClient("auth-server")
public interface AuthFegin {

    @RequestMapping(value = { "/checkToken" }, produces = "application/json")
    Map<String, Object> checkToken();
}
