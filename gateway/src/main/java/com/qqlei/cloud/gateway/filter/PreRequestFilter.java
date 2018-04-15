package com.qqlei.cloud.gateway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2018/4/14 0014.
 */
@Component
public class PreRequestFilter extends ZuulFilter{
    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
       RequestContext context = RequestContext.getCurrentContext();
       HttpServletRequest request =  context.getRequest();

        System.out.println(request.getMethod()+"-"+request.getRequestURL().toString());
        return null;
    }
}
