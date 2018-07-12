package com.qqlei.cloud.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.qqlei.cloud.gateway.fegin.AuthFegin;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/14 0014.
 */
@Component
public class PreRequestFilter extends ZuulFilter{

    @Autowired
    private JwtTokenStore jwtTokenStore;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String token = StringUtils.substringAfter(request.getHeader(HttpHeaders.AUTHORIZATION), "Bearer ");

        if (StringUtils.isEmpty(token)) {
            return null;
        }

        OAuth2AccessToken oAuth2AccessToken = jwtTokenStore.readAccessToken(token);

        System.out.println(oAuth2AccessToken);
        return null;
    }
}
