package com.qqlei.cloud.auth.security.integration.authenticator.dingding;

import com.qqlei.cloud.auth.fegin.UserFegin;
import com.qqlei.cloud.auth.security.constants.SecurityConstant;
import com.qqlei.cloud.auth.security.dingding.DingTokenServer;
import com.qqlei.cloud.auth.security.integration.IntegrationAuthentication;
import com.qqlei.cloud.auth.security.integration.authenticator.IntegrationAuthenticator;
import com.qqlei.cloud.auth.security.vo.SysUserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 李雷 on 2018/7/24.
 */
@Service
public class DingDingIntegrationAuthenticator implements IntegrationAuthenticator {



    @Autowired
    private DingTokenServer dingTokenServer;

    @Autowired
    private UserFegin userFegin;

    @Override
    public SysUserAuthentication authenticate(IntegrationAuthentication integrationAuthentication) {
        String phone = integrationAuthentication.getUsername();
        SysUserAuthentication sysUserAuthentication = userFegin.findUserByPhoneNumber(phone);
        return sysUserAuthentication;
    }

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication) {
        String code = integrationAuthentication.getAuthParameter(SecurityConstant.DING_DING_LOGIN_CODE_PARAM_NAME);
        String phone = dingTokenServer.getUserPhoneByAuthCode(code);
        integrationAuthentication.setUsername(phone);
    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return SecurityConstant.DING_DING_AUTH_TYPE.equals(integrationAuthentication.getAuthType());
    }

    @Override
    public void complete(IntegrationAuthentication integrationAuthentication) {

    }
}
