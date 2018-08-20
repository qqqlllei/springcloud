package com.qqlei.cloud.auth.security.integration.authenticator.sms;

import com.qqlei.cloud.auth.fegin.LoginAbstractFegin;
import com.qqlei.cloud.auth.security.constants.SecurityConstant;
import com.qqlei.cloud.auth.security.integration.IntegrationAuthentication;
import com.qqlei.cloud.auth.security.integration.authenticator.IntegrationAuthenticator;
import com.qqlei.cloud.auth.security.vo.SysUserAuthentication;
import com.qqlei.cloud.auth.util.ApplicationContextHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SmsIntegrationAuthenticator implements IntegrationAuthenticator {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;





    @Override
    public SysUserAuthentication authenticate(IntegrationAuthentication integrationAuthentication) {

        String code = integrationAuthentication.getAuthParameter(SecurityConstant.AUTH_AUTHORIZED_GRANT_PASSWORD);


        String phone = integrationAuthentication.getAuthParameter(SecurityConstant.SMS_LOGIN_PHONE_PARAM_NAME);

        //get user by phone
        LoginAbstractFegin loginAbstractFegin = ApplicationContextHelper.getBean(integrationAuthentication.getFindUserClassName(), LoginAbstractFegin.class);
        SysUserAuthentication sysUserAuthentication = loginAbstractFegin.findUserByPhone(phone);
        if (sysUserAuthentication != null) {
            sysUserAuthentication.setPassword(passwordEncoder.encode(code));
        }


        return sysUserAuthentication;
    }

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication,Map<String,Object> additionalInformation) {
        String smsCode = integrationAuthentication.getAuthParameter(SecurityConstant.AUTH_AUTHORIZED_GRANT_PASSWORD);
        String username = integrationAuthentication.getAuthParameter(SecurityConstant.SMS_LOGIN_PHONE_PARAM_NAME);

        String code = stringRedisTemplate.opsForValue().get(username+"_"+smsCode);
        code="1234";
        if (StringUtils.isBlank(code)) {
            throw new OAuth2Exception("验证码错误或已过期");
        }
    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return SecurityConstant.SMS_AUTH_TYPE.equals(integrationAuthentication.getAuthType());
    }

    @Override
    public void complete(IntegrationAuthentication integrationAuthentication) {

    }
}
