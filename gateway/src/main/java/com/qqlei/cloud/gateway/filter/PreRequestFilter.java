package com.qqlei.cloud.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.qqlei.cloud.gateway.RestResponseUtil;
import com.qqlei.cloud.gateway.exception.BaseException;
import com.qqlei.cloud.gateway.fegin.AuthFegin;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

import java.util.Map;

import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * Created by Administrator on 2018/4/14 0014.
 */
@Component
public class PreRequestFilter extends ZuulFilter{

    private static final String LOGIN_URI = "/authentication/form";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private AuthFegin authFegin;

    @Override
    public String filterType() {
        return  PRE_TYPE;
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
        String requestURI = request.getRequestURI();

        if (requestURI.contains(LOGIN_URI)) return null;


        String token = StringUtils.substringAfter(request.getHeader(HttpHeaders.AUTHORIZATION), "Bearer ");

        if (StringUtils.isEmpty(token)) {
            RestResponseUtil.noTokenResponse(requestContext);
        }

        Map<String, Object> authMap =  authFegin.checkToken(token);


//        stringRedisTemplate.opsForValue().get()

        return null;
    }
}
