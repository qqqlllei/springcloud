package com.qqlei.cloud.auth.security.integration.authenticator;


import com.qqlei.cloud.auth.security.integration.IntegrationAuthentication;
import com.qqlei.cloud.auth.security.vo.SysUserAuthentication;

import java.util.Map;

public interface IntegrationAuthenticator {

    /**
     * 处理集成认证
     * @param integrationAuthentication
     * @return
     */
    SysUserAuthentication authenticate(IntegrationAuthentication integrationAuthentication) ;


    /**
     * 进行预处理
     * @param integrationAuthentication
     */
    void prepare(IntegrationAuthentication integrationAuthentication,Map<String,Object> additionalInformation);

     /**
     * 判断是否支持集成认证类型
     * @param integrationAuthentication
     * @return
     */
    boolean support(IntegrationAuthentication integrationAuthentication);

    /** 认证结束后执行
     * @param integrationAuthentication
     */
    void complete(IntegrationAuthentication integrationAuthentication);

}
