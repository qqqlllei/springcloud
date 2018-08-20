package com.qqlei.cloud.auth.security.integration.authenticator;

import com.qqlei.cloud.auth.fegin.UserFegin;
import com.qqlei.cloud.auth.security.constants.SecurityConstant;
import com.qqlei.cloud.auth.security.integration.IntegrationAuthentication;
import com.qqlei.cloud.auth.security.vo.SysUserAuthentication;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Primary
public class UsernamePasswordAuthenticator implements IntegrationAuthenticator {

    @Autowired
    private UserFegin userFegin;

    @Override
    public SysUserAuthentication authenticate(IntegrationAuthentication integrationAuthentication) {
        SysUserAuthentication sysUserAuthentication = userFegin.findUserByUsername(integrationAuthentication.getUsername());
        return sysUserAuthentication;
    }

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication,Map<String,Object> additionalInformation) {

    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {

        return (SecurityConstant.AUTH_AUTHORIZED_GRANT_PASSWORD.equals(integrationAuthentication.getAuthType())
                || StringUtils.isEmpty(integrationAuthentication.getAuthType()));
    }

    @Override
    public void complete(IntegrationAuthentication integrationAuthentication) {

    }
}
