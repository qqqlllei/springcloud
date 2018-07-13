package com.qqlei.cloud.auth.security;

import com.alibaba.fastjson.JSONObject;
import com.qqlei.cloud.auth.security.vo.SysUserAuthentication;
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

	private static String REQUEST_CLIENT_ID = "clientId";

	@Autowired
	private ClientDetailsService clientDetailsService;

	@Autowired
	private DefaultTokenServices defaultTokenServices;


	@Autowired
	private AuthClientProperties authClientProperties;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private JwtAccessTokenConverter jwtAccessTokenConverter;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

		SysUserAuthentication sysUserAuthentication = new SysUserAuthentication();
		BeanUtils.copyProperties(authentication.getPrincipal(), sysUserAuthentication);
		String clientId = request.getParameter(REQUEST_CLIENT_ID);
		ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
		TokenRequest tokenRequest = new TokenRequest(null, authClientProperties.getAuthServerClientId(), clientDetails.getScope(), "custom");
		OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
		OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
		OAuth2AccessToken token =defaultTokenServices.createAccessToken(oAuth2Authentication);
		token = jwtAccessTokenConverter.enhance(token,oAuth2Authentication);
		String sessionKey = clientId+"_"+sysUserAuthentication.getId();
		stringRedisTemplate.opsForValue().set(sessionKey,JSONObject.toJSONString(sysUserAuthentication),clientDetails.getAccessTokenValiditySeconds(), TimeUnit.SECONDS);
		response.setContentType("application/json;charset=UTF-8");
		Map<String,String> result = new HashMap<>();
		result.put("resultCode","0000");
		result.put("access_token",token.getValue());
		response.getWriter().write(JSONObject.toJSONString(token));



	}

}