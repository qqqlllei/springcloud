package com.qqlei.cloud.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Created by 李雷 on 2018/7/12.
 */
@Component
public class AuthTokenPostFilter extends ZuulFilter {

    private static final String AUTH_URI = "/auth";

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public String filterType() {
        return "post";
    }

    @Override
    public int filterOrder() {
        return 3;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {

        try {

            RequestContext requestContext = RequestContext.getCurrentContext();
            HttpServletRequest request = requestContext.getRequest();
            String requestURI = request.getRequestURI();
            if (!requestURI.contains(AUTH_URI)) {
                return null;
            }

            if(requestContext.getResponseStatusCode() == 200){
                InputStream stream = requestContext.getResponseDataStream();
                String body = StreamUtils.copyToString(stream, Charset.forName("UTF-8"));
                JSONObject jsonObject = JSONObject.parseObject(body);
                String access_token = jsonObject.getString("access_token");
                requestContext.setResponseBody(body);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
