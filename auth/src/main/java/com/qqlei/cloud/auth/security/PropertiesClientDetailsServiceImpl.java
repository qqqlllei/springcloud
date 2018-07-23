package com.qqlei.cloud.auth.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Component("propertiesClientDetailsService")
public class PropertiesClientDetailsServiceImpl implements ClientDetailsService {

	private Logger logger = LoggerFactory.getLogger(PropertiesClientDetailsServiceImpl.class);

	private ClientDetailsService clientDetailsService;

	private static final String CLIENT_TYPE="wechat";

	@Autowired
	private AuthClientProperties authClientProperties;

	/**
	 * Init.
	 */
	@PostConstruct
	public void init() {
		InMemoryClientDetailsServiceBuilder builder = new InMemoryClientDetailsServiceBuilder();
		if (authClientProperties!=null && authClientProperties.getClients()!=null && authClientProperties.getClients().length>0) {
			for (OAuth2ClientProperties client : authClientProperties.getClients()) {
				Map<String,String> additionalInformation = new HashMap<>();
				if(CLIENT_TYPE.equals(client.getClientType())){
					additionalInformation.put("wechatAppId",client.getWechatAppId());
					additionalInformation.put("wechatSecret",client.getWechatSecret());
					additionalInformation.put("wechatToken",client.getWechatToken());
					additionalInformation.put("wechatAesKey",client.getWechatAesKey());
				}

				builder.withClient(client.getClientId())
						.secret(client.getClientSecret())
						.authorizedGrantTypes("password")
						.accessTokenValiditySeconds(client.getAccessTokenValidateSeconds())
						.refreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds())
						.scopes(client.getScope()).additionalInformation(additionalInformation);

			}
		}
		try {
			clientDetailsService = builder.build();
		} catch (Exception e) {
			logger.error("init={}", e.getMessage(), e);
		}
	}

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		return clientDetailsService.loadClientByClientId(clientId);
	}
}
