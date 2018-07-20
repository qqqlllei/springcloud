package com.qqlei.security.dingding;

import com.alibaba.fastjson.JSONObject;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import static com.qqlei.security.dingding.DingUrlConstant.*;

/**
 * Created by 李雷 on 2018/7/20.
 */
@FeignClient(url = DingUrlConstant.DING_BASE_URL)
public interface DingFegin {

    @RequestMapping(value = DING_TOKEN_OF_SNS_URI,method = RequestMethod.GET)
    JSONObject getSnsToken(@RequestParam("appid") String appid,@RequestParam("appsecret") String appsecret);

    @RequestMapping(value =DING_PERSISTENT_CODE_OF_SNS_URI,method = RequestMethod.POST)
    JSONObject getSnsPersistentCode(@RequestParam("access_token") String accessToken,@RequestParam("tmp_auth_code") String authCode);

    @RequestMapping(value = DING_TOKEN_OF_COM_URI,method = RequestMethod.GET)
    JSONObject getComToken(@RequestParam("corpid") String corpid,@RequestParam("corpsecret") String corpsecret);

    @RequestMapping(value = DING_GET_USERID_URI,method = RequestMethod.GET)
    JSONObject getUseridByUnionid(@RequestParam("access_token") String accessToken,@RequestParam("unionid") String unionid);

    @RequestMapping(value = DING_GET_USERINFO_URI,method = RequestMethod.GET)
    JSONObject getUserInfoByUserId(@RequestParam("access_token") String accessToken,@RequestParam("userid") String userid);
}
