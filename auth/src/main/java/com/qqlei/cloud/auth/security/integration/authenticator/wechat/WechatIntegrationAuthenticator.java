package com.qqlei.cloud.auth.security.integration.authenticator.wechat;

import com.qqlei.cloud.auth.fegin.UserFegin;
import com.qqlei.cloud.auth.security.integration.IntegrationAuthentication;
import com.qqlei.cloud.auth.security.integration.authenticator.IntegrationAuthenticator;
import com.qqlei.cloud.auth.security.vo.SysUserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by 李雷 on 2018/7/10.
 */
@Service
public class WechatIntegrationAuthenticator implements IntegrationAuthenticator {

    @Autowired
    private UserFegin userFegin;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final static String WECHAT_AUTH_TYPE = "wechat";

    @Override
    public SysUserAuthentication authenticate(IntegrationAuthentication integrationAuthentication) {

        String password = integrationAuthentication.getAuthParameter("password");
        SysUserAuthentication sysUserAuthentication = userFegin.findUserByOpenId(password);
        if(sysUserAuthentication != null){
            sysUserAuthentication.setPassword(passwordEncoder.encode(password));
        }
        return sysUserAuthentication;
    }

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication) {

    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return WECHAT_AUTH_TYPE.equals(integrationAuthentication.getAuthType());
    }

    @Override
    public void complete(IntegrationAuthentication integrationAuthentication) {

    }
}
