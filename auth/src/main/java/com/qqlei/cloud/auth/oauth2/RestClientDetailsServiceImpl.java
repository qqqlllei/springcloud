package com.qqlei.cloud.auth.oauth2;

import com.qqlei.cloud.auth.config.OAuth2ClientProperties;
import com.qqlei.cloud.auth.config.SecurityProperties;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Created by Administrator on 2018/4/15 0015.
 */
@Component("clientDetailsService")
public class RestClientDetailsServiceImpl implements ClientDetailsService {

    private ClientDetailsService clientDetailsService;
    @Autowired
    private SecurityProperties securityProperties;

    @PostConstruct
    public void init() {
        InMemoryClientDetailsServiceBuilder builder = new InMemoryClientDetailsServiceBuilder();
        if (ArrayUtils.isNotEmpty(securityProperties.getOauth2().getClients())) {
            for (OAuth2ClientProperties client : securityProperties.getOauth2().getClients()) {
                builder.withClient(client.getClientId())
                        .secret(client.getClientSecret())
                        .authorizedGrantTypes("refresh_token", "password", "client_credentials")
                        .accessTokenValiditySeconds(client.getAccessTokenValidateSeconds())
                        .refreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds())
                        .scopes(client.getScope());
            }
        }
        try {
            clientDetailsService = builder.build();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        return clientDetailsService.loadClientByClientId(clientId);
    }
}
