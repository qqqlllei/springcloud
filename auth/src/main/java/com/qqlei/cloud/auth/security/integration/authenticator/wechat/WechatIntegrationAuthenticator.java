package com.qqlei.cloud.auth.security.integration.authenticator.wechat;

import com.alibaba.fastjson.JSONObject;
import com.qqlei.cloud.auth.exception.BusinessException;
import com.qqlei.cloud.auth.exception.ExceptionCodeEnum;
import com.qqlei.cloud.auth.fegin.LoginAbstractFegin;
import com.qqlei.cloud.auth.fegin.OutWechatFegin;
import com.qqlei.cloud.auth.fegin.WechatFegin;
import com.qqlei.cloud.auth.security.constants.SecurityConstant;
import com.qqlei.cloud.auth.security.integration.IntegrationAuthentication;
import com.qqlei.cloud.auth.security.integration.authenticator.IntegrationAuthenticator;
import com.qqlei.cloud.auth.security.vo.SysUserAuthentication;
import com.qqlei.cloud.auth.util.ApplicationContextHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by 李雷 on 2018/7/10.
 */
@Service
public class WechatIntegrationAuthenticator implements IntegrationAuthenticator {


    private static final String WECHAT_OPENID_PARAM_NAME="openid";

    @Autowired
    private WechatFegin wechatFegin;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public SysUserAuthentication authenticate(IntegrationAuthentication integrationAuthentication)  {

        String openId = integrationAuthentication.getUsername();

        String password = integrationAuthentication.getAuthParameter(SecurityConstant.AUTH_AUTHORIZED_GRANT_PASSWORD);

        LoginAbstractFegin loginAbstractFegin = ApplicationContextHelper.getBean(integrationAuthentication.getFindUserClassName(), LoginAbstractFegin.class);
        SysUserAuthentication sysUserAuthentication=loginAbstractFegin.findUserById(openId);

        if (sysUserAuthentication != null) {
            sysUserAuthentication.setPassword(passwordEncoder.encode(password));
        }

        return sysUserAuthentication;
    }

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication,Map<String,Object> additionalInformation) {
        String password = integrationAuthentication.getAuthParameter(SecurityConstant.AUTH_AUTHORIZED_GRANT_PASSWORD);


        String oAuth2AccessToken = wechatFegin.oauth2getAccessToken(String.valueOf(additionalInformation.get(SecurityConstant.WECHAT_APPID_PARAM_NAME)),
                String.valueOf(additionalInformation.get(SecurityConstant.WECHAT_SECRET_PARAM_NAME)),
                password,
                SecurityConstant.OAUTH2_GET_ACCESS_TOKEN_GRANT_TYPE);
        JSONObject wechatResponse = JSONObject.parseObject(oAuth2AccessToken);
        if(wechatResponse.containsKey(SecurityConstant.WECHAT_REQUEST_ERROR_FLAG)){
            throw new OAuth2Exception(wechatResponse.toJSONString());
        }

        String openId =  wechatResponse.getString(WECHAT_OPENID_PARAM_NAME);
        integrationAuthentication.setUsername(openId);
    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return SecurityConstant.WECHAT_AUTH_TYPE.equals(integrationAuthentication.getAuthType());
    }

    @Override
    public void complete(IntegrationAuthentication integrationAuthentication) {

    }
}
