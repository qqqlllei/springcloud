package com.qqlei.cloud.auth.fegin;

import com.qqlei.cloud.auth.security.vo.SysUserAuthentication;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by 李雷 on 2018/8/13.
 */

@FeignClient("customer-wechat")
public interface CustomerWechatFegin extends LoginAbstractFegin{

    @Override
    @RequestMapping(value="/channelStaff/findUserByOpenId")
    SysUserAuthentication findUserById(@RequestParam("openId") String openId);

}
