package com.qqlei.cloud.auth.security.integration.authenticator.sms;

import com.alibaba.fastjson.JSONObject;
import com.qqlei.cloud.auth.security.constants.SecurityConstant;
import com.qqlei.cloud.auth.security.integration.AuthSuccessHandler;
import com.qqlei.cloud.auth.security.vo.SysUserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by 李雷 on 2018/8/17.
 */
@Component
public class SmsAuthenticationSuccessHandler implements AuthSuccessHandler {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired
    private ResourceServerTokenServices resourceServerTokenServices;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication, OAuth2AccessToken token, OAuth2Authentication oAuth2Authentication, SysUserAuthentication sysUserAuthentication, ClientDetails clientDetails) throws ServletException, IOException {

        String appToken = request.getParameter(SecurityConstant.APP_TOKEN_NAME);
        OAuth2AccessToken oAuth2AccessToken = resourceServerTokenServices.readAccessToken(appToken);
        Map<String, Object> additionalInformation =  oAuth2AccessToken.getAdditionalInformation();
        String currentOpenId = String.valueOf(additionalInformation.get(SecurityConstant.WECHAT_OPENID_PARAM_NAME));

        String oldOpenId = sysUserAuthentication.getOpenId();

        if(!currentOpenId.equals(oldOpenId)){
            // send message or update info TODO
        }

        Map<String,Object> tokenAdditionalInformation = token.getAdditionalInformation();
        String sessionKey = clientDetails.getClientId()+"_"+sysUserAuthentication.getPhone();
        tokenAdditionalInformation.put(SecurityConstant.AUTH_SESSION_KEY,sessionKey);
        String tokenValue =token.getValue();
        token = jwtAccessTokenConverter.enhance(token,oAuth2Authentication);
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(sysUserAuthentication);
        jsonObject.put(SecurityConstant.TOKEN_VALUE,tokenValue);
        stringRedisTemplate.opsForValue().set(sessionKey,jsonObject.toJSONString(),clientDetails.getAccessTokenValiditySeconds(), TimeUnit.SECONDS);
        response.setContentType("application/json;charset=UTF-8");
        Map<String,String> result = new HashMap<>();
        result.put("resultCode",SecurityConstant.AUTH_LOGIN_SUCCESS_STATUS);
        result.put("appToken",token.getValue());
        response.getWriter().write(JSONObject.toJSONString(result));
    }
}
