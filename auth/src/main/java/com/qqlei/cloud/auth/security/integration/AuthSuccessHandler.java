package com.qqlei.cloud.auth.security.integration;

import com.qqlei.cloud.auth.security.vo.SysUserAuthentication;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 李雷 on 2018/8/8.
 */
public interface AuthSuccessHandler {

    void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                 Authentication authentication,OAuth2AccessToken token,OAuth2Authentication oAuth2Authentication,
                                 SysUserAuthentication sysUserAuthentication,ClientDetails clientDetails) throws ServletException, IOException ;
}
