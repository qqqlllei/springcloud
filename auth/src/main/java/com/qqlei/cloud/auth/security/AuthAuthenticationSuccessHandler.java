package com.qqlei.cloud.auth.security;

import com.alibaba.fastjson.JSONObject;
import com.qqlei.cloud.auth.security.constants.SecurityConstant;
import com.qqlei.cloud.auth.security.integration.AuthSuccessHandler;
import com.qqlei.cloud.auth.security.vo.SysUserAuthentication;
import com.qqlei.cloud.auth.util.ApplicationContextHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class AuthAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {



	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	private DefaultTokenServices defaultTokenServices;



	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

		SysUserAuthentication sysUserAuthentication = new SysUserAuthentication();
		BeanUtils.copyProperties(authentication.getPrincipal(), sysUserAuthentication);
		String clientId = request.getParameter(SecurityConstant.REQUEST_CLIENT_ID);
		ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
		TokenRequest tokenRequest = new TokenRequest(null, clientId, clientDetails.getScope(), "custom");
		OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
		OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
		Map<String, Object> additionalInformation = clientDetails.getAdditionalInformation();
		OAuth2AccessToken token =defaultTokenServices.createAccessToken(oAuth2Authentication);
		String authSuccessHandlerBeanName =String.valueOf(additionalInformation.get(SecurityConstant.AUTH_SUCCESS_HANDLER));
		AuthSuccessHandler authenticationSuccessHandler =  ApplicationContextHelper.getBean(authSuccessHandlerBeanName,AuthSuccessHandler.class);
		authenticationSuccessHandler.onAuthenticationSuccess(request,response,authentication,token,oAuth2Authentication,sysUserAuthentication,clientDetails);


	}

}
