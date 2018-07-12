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

@Component("propertiesClientDetailsService")
public class PropertiesClientDetailsServiceImpl implements ClientDetailsService {

	private Logger logger = LoggerFactory.getLogger(PropertiesClientDetailsServiceImpl.class);

	private ClientDetailsService clientDetailsService;

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
				builder.withClient(client.getClientId())
						.secret(client.getClientSecret())
						.authorizedGrantTypes("password")
						.accessTokenValiditySeconds(client.getAccessTokenValidateSeconds())
						.refreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds())
						.scopes(client.getScope());
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
