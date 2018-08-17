package com.qqlei.cloud.auth.security.integration.authenticator.sms;

import com.qqlei.cloud.auth.fegin.LoginAbstractFegin;
import com.qqlei.cloud.auth.security.constants.SecurityConstant;
import com.qqlei.cloud.auth.security.integration.IntegrationAuthentication;
import com.qqlei.cloud.auth.security.integration.authenticator.AbstractPreparableIntegrationAuthenticator;
import com.qqlei.cloud.auth.security.vo.SysUserAuthentication;
import com.qqlei.cloud.auth.util.ApplicationContextHelper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SmsIntegrationAuthenticator extends AbstractPreparableIntegrationAuthenticator {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ResourceServerTokenServices resourceServerTokenServices;



    @Override
    public SysUserAuthentication authenticate(IntegrationAuthentication integrationAuthentication) {

        String code = integrationAuthentication.getAuthParameter(SecurityConstant.AUTH_AUTHORIZED_GRANT_PASSWORD);


        String phone = integrationAuthentication.getUsername();

        //get user by phone
        LoginAbstractFegin loginAbstractFegin = ApplicationContextHelper.getBean(integrationAuthentication.getFindUserClassName(), LoginAbstractFegin.class);
        SysUserAuthentication sysUserAuthentication=loginAbstractFegin.findUserById(phone);
        if (sysUserAuthentication != null) {
            sysUserAuthentication.setPassword(passwordEncoder.encode(code));
        }

        // wechat sms request params
        String appToken = integrationAuthentication.getAuthParameter(SecurityConstant.APP_TOKEN_NAME);
        if(StringUtils.isNotBlank(appToken)){
            OAuth2AccessToken oAuth2AccessToken = resourceServerTokenServices.readAccessToken(appToken);
            Map<String, Object> additionalInformation =  oAuth2AccessToken.getAdditionalInformation();
            sysUserAuthentication.setOpenid(String.valueOf(additionalInformation.get(SecurityConstant.WECHAT_OPENID_PARAM_NAME)));
        }
        return sysUserAuthentication;
    }

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication) {
        String smsCode = integrationAuthentication.getAuthParameter(SecurityConstant.AUTH_AUTHORIZED_GRANT_PASSWORD);
        String username = integrationAuthentication.getAuthParameter("phone");
        String code = stringRedisTemplate.opsForValue().get(username+"_"+smsCode);
        if (StringUtils.isBlank(code)) {
            throw new OAuth2Exception("验证码错误或已过期");
        }
    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return SecurityConstant.SMS_AUTH_TYPE.equals(integrationAuthentication.getAuthType());
    }
}
