package com.qqlei.cloud.auth.security.integration.authenticator.wechat;

import com.alibaba.fastjson.JSONObject;
import com.qqlei.cloud.auth.fegin.UserFegin;
import com.qqlei.cloud.auth.fegin.WechatFegin;
import com.qqlei.cloud.auth.security.integration.IntegrationAuthentication;
import com.qqlei.cloud.auth.security.integration.authenticator.IntegrationAuthenticator;
import com.qqlei.cloud.auth.security.vo.SysUserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
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

    private final static String WECHAT_AUTH_TYPE = "wechat";

    private final static String WECHAT_LOGIN_CODE_PARAM_NAME="code";
    private final static String WECHAT_CLIENT_ID_PARAM_NAME="clientId";

    private static final String WECHAT_APPID_PARAM_NAME="wechatAppId";

    private static final String WECHAT_SECRET_PARAM_NAME="wechatSecret";

    private static final String OAUTH2_GET_ACCESS_TOKEN_GRANT_TYPE="authorization_code";

    private static final String WECHAT_OPENID_PARAM_NAME="openId";

    private static final String WECHAT_REQUEST_SUCCESS_FLAG="0";

    private static final String WECHAT_REQUEST_ERROR_FLAG="errcode";

    @Autowired
    private UserFegin userFegin;

    @Autowired
    private WechatFegin wechatFegin;

    @Autowired
    private ClientDetailsService clientDetailsService;



    @Override
    public SysUserAuthentication authenticate(IntegrationAuthentication integrationAuthentication) {

        String openId = integrationAuthentication.getUsername();
        SysUserAuthentication sysUserAuthentication = userFegin.findUserByOpenId(openId);
        return sysUserAuthentication;
    }

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication) {
        String code = integrationAuthentication.getAuthParameter(WECHAT_LOGIN_CODE_PARAM_NAME);
        String clientId = integrationAuthentication.getAuthParameter(WECHAT_CLIENT_ID_PARAM_NAME);
        ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
        Map<String,Object> additionalInformation =  clientDetails.getAdditionalInformation();
        String oAuth2AccessToken = wechatFegin.oauth2getAccessToken(String.valueOf(additionalInformation.get(WECHAT_APPID_PARAM_NAME)),
                String.valueOf(additionalInformation.get(WECHAT_SECRET_PARAM_NAME)),
                code,
                OAUTH2_GET_ACCESS_TOKEN_GRANT_TYPE);
        JSONObject wechatResponse = JSONObject.parseObject(oAuth2AccessToken);
        if(wechatResponse.containsKey(WECHAT_REQUEST_ERROR_FLAG)){
            throw new OAuth2Exception(wechatResponse.toJSONString());
        }

        String openId =  wechatResponse.getString(WECHAT_OPENID_PARAM_NAME);
        integrationAuthentication.setUsername(openId);
    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return WECHAT_AUTH_TYPE.equals(integrationAuthentication.getAuthType());
    }

    @Override
    public void complete(IntegrationAuthentication integrationAuthentication) {

    }
}
