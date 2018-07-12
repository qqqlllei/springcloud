package com.qqlei.cloud.auth.security.integration.authenticator.sms;

import com.qqlei.cloud.auth.fegin.UserFegin;
import com.qqlei.cloud.auth.security.integration.IntegrationAuthentication;
import com.qqlei.cloud.auth.security.integration.authenticator.AbstractPreparableIntegrationAuthenticator;
import com.qqlei.cloud.auth.security.vo.SysUserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class SmsIntegrationAuthenticator extends AbstractPreparableIntegrationAuthenticator {

    @Autowired
    private UserFegin userFegin;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final static String SMS_AUTH_TYPE = "sms";

    @Override
    public SysUserAuthentication authenticate(IntegrationAuthentication integrationAuthentication) {

        String code = integrationAuthentication.getAuthParameter("password");
        String phone = integrationAuthentication.getUsername();

        //通过手机号码查询用户
        SysUserAuthentication sysUserAuthentication = this.userFegin.findUserByPhoneNumber(phone);
        if (sysUserAuthentication != null) {
            sysUserAuthentication.setPassword(passwordEncoder.encode(code));
        }
        return sysUserAuthentication;
    }

    @Override
    public void prepare(IntegrationAuthentication integrationAuthentication) {
        String smsCode = integrationAuthentication.getAuthParameter("password");
        String username = integrationAuthentication.getAuthParameter("username");
        System.out.println(smsCode+":"+username);
        // 结合登陆逻辑，去制定地方获取存入缓存里的code ，然后对比phone 和 code
//        stringRedisTemplate.opsForValue().get("")
//        Result<Boolean> result = verificationCodeClient.validate(smsToken, smsCode, username);
//        if (!result.getData()) {
//            throw new OAuth2Exception("验证码错误或已过期");
//        }
    }

    @Override
    public boolean support(IntegrationAuthentication integrationAuthentication) {
        return SMS_AUTH_TYPE.equals(integrationAuthentication.getAuthType());
    }
}
