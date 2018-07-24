package com.qqlei.cloud.auth.controller;

import com.alibaba.fastjson.JSONObject;
import com.qqlei.cloud.auth.security.dingding.DingTokenServer;
import com.qqlei.security.wechat.WechatUrlHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by 李雷 on 2018/7/23.
 */
@RestController
@RequestMapping("/authentication")
public class WechatTokenController {

    private static final String WECHAT_APPID="wechatAppId";

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    private DingTokenServer dingTokenServer;

    @RequestMapping(value = "/getWeiXinCodeUrl/{clientId}")
    public String getWeiXinCodeUrl(@PathVariable("clientId") String  clientId){
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        Map<String,Object> additionalInformation =  clientDetails.getAdditionalInformation();
        String oauthUrl = WechatUrlHelper.oauth2buildAuthorizationUrl(String.valueOf(additionalInformation.get(WECHAT_APPID)));
        JSONObject result = new JSONObject();
        String[] urlAtrr = oauthUrl.split("&redirect_uri=");
        String start = "";
        String end = "";
        if(urlAtrr != null && !"".equals(urlAtrr)){
            start = urlAtrr[0];
            end = urlAtrr[1];
        }
        result.put("start",start);
        result.put("end",end);
        return result.toJSONString();
    }


    @RequestMapping(value = "/dingcalBack")
    public String dingcalBack(@RequestParam("code") String  code){
        String phone = dingTokenServer.getUserPhoneByAuthCode(code);

        System.out.println("=================phone:"+phone);
        return phone;
    }




}
