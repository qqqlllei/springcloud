package com.qqlei.cloud.auth.fegin;

import com.qqlei.cloud.auth.security.vo.SysUserAuthentication;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by 李雷 on 2018/7/24.
 */
@FeignClient("out-wechat")
public interface OutWechatFegin {

    @RequestMapping(value="/channelStaff/findUserByOpenId/{openId}")
    SysUserAuthentication findUserByOpenId(@PathVariable("openId") String openId);

}
