package com.qqlei.cloud.auth.fegin;

import com.qqlei.cloud.auth.security.vo.SysUserAuthentication;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by 李雷 on 2018/7/10.
 */
@FeignClient("user-server")
public interface UserFegin {
    @RequestMapping(value="/findUserByUsername")
    SysUserAuthentication findUserByUsername(@RequestParam("name") String name);
}
