package com.qqlei.cloud.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.jwt.crypto.sign.InvalidSignatureException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/7/8 0008.
 */
@RestController
public class UserWeb {

    @Autowired
    private ResourceServerTokenServices resourceServerTokenServices;

    @RequestMapping(value = { "/authentication/checkToken" })
    public Map<String, Object> user(@RequestParam("token") String token) {
        Map<String, Object> result = new HashMap<>();
        try{
            OAuth2AccessToken oAuth2AccessToken = resourceServerTokenServices.readAccessToken(token);

            if (oAuth2AccessToken == null) {
                result.put("resultCode","1111");
                result.put("resultMsg","Token= "+token+" 未识别");
                return result;
            }

            if (oAuth2AccessToken.isExpired()) {
                result.put("resultCode","2222");
                result.put("resultMsg","Token= "+token+" 解析失败");
                return result;
            }

            result.put("resultCode","0000");

            result = oAuth2AccessToken.getAdditionalInformation();
        }catch(Exception e){
            result.put("resultCode","3333");
            result.put("resultMsg","Token= "+token+" 已被损坏");
        }

        return result;
    }
}
