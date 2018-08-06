package com.qqlei.cloud.auth.fegin;

import com.qqlei.cloud.auth.security.vo.SysUserAuthentication;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by 李雷 on 2018/7/24.
 */
@FeignClient("out-wechat")
public interface OutWechatFegin {

    @RequestMapping(value="/channelStaff/findUserByOpenId")
    SysUserAuthentication findUserByOpenId(@RequestParam("openId") String openId);

}
