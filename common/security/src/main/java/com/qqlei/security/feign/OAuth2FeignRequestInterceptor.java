package com.qqlei.security.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;

import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.util.Assert;

/**
 * The class O auth 2 feign request interceptor.
 *
 * @author paascloud.net @gmail.com
 */
public class OAuth2FeignRequestInterceptor implements RequestInterceptor {
	private static final String BEARER_TOKEN_TYPE = "bearer";

	private final OAuth2RestTemplate oAuth2RestTemplate;

	/**
	 * Instantiates a new O auth 2 feign request interceptor.
	 *
	 * @param oAuth2RestTemplate the o auth 2 rest template
	 */
	OAuth2FeignRequestInterceptor(OAuth2RestTemplate oAuth2RestTemplate) {
		Assert.notNull(oAuth2RestTemplate, "Context can not be null");
		this.oAuth2RestTemplate = oAuth2RestTemplate;
	}

	/**
	 * Apply.
	 *
	 * @param template the template
	 */
	@Override
	public void apply(RequestTemplate template) {
		System.out.println("===========>Constructing Header {"+HttpHeaders.AUTHORIZATION+"} for Token {"+BEARER_TOKEN_TYPE+"}");
		template.header(HttpHeaders.AUTHORIZATION, String.format("%s %s", BEARER_TOKEN_TYPE, oAuth2RestTemplate.getAccessToken().toString()));
	}
}
