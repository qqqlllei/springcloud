package com.qqlei.cloud.auth.security;

import com.qqlei.cloud.auth.security.constants.SecurityConstant;
import com.qqlei.cloud.auth.security.integration.AuthSuccessHandler;
import com.qqlei.cloud.auth.security.vo.SysUserAuthentication;
import com.qqlei.cloud.auth.util.ApplicationContextHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@Component
public class AuthAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	private DefaultTokenServices defaultTokenServices;

	@Autowired

	private AuthClientProperties authClientProperties;



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
		OAuth2AccessToken token =defaultTokenServices.createAccessToken(oAuth2Authentication);
		String authType = request.getParameter(SecurityConstant.AUTH_TYPE_PARM_NAME);
		String authSuccessHandlerBeanName =  getSuccessHandlerByType(authType);
		AuthSuccessHandler authenticationSuccessHandler =  ApplicationContextHelper.getBean(authSuccessHandlerBeanName,AuthSuccessHandler.class);
		authenticationSuccessHandler.onAuthenticationSuccess(request,response,authentication,token,oAuth2Authentication,sysUserAuthentication,clientDetails);


	}

	private String getSuccessHandlerByType(String type){
		List<Map<String, String>> handlers =  authClientProperties.getHandlers();
		for (Map<String,String> map: handlers) {
			if(map.get(SecurityConstant.AUTH_TYPE_PARM_NAME).equals(type)){
				return map.get(SecurityConstant.AUTH_SUCCESS_HANDLER);
			}
		}

		return SecurityConstant.AUTH_DEFAULT_SUCCESS_HANDLER;
	}

}
