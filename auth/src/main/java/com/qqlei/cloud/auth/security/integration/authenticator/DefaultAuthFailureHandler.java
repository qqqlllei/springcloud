package com.qqlei.cloud.auth.security.integration.authenticator;

import com.qqlei.cloud.auth.security.integration.AuthFailureHandler;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by 李雷 on 2018/8/9.
 */
@Component
public class DefaultAuthFailureHandler implements AuthFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

    }
}
