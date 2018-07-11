package com.qqlei.cloud.gateway.config;

import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * Created by Administrator on 2018/7/11 0011.
 */
@Configuration
public class DynamicRouteConfiguration {

    private Registration registration;
    private DiscoveryClient discovery;
    private ZuulProperties zuulProperties;
    private ServerProperties server;
    private StringRedisTemplate redisTemplate;

    public DynamicRouteConfiguration(Registration registration, DiscoveryClient discovery,
                                     ZuulProperties zuulProperties, ServerProperties server, StringRedisTemplate redisTemplate) {
        this.registration = registration;
        this.discovery = discovery;
        this.zuulProperties = zuulProperties;
        this.server = server;
        this.redisTemplate = redisTemplate;
    }

    @Bean
    public DynamicRouteLocator dynamicRouteLocator() {
        return new DynamicRouteLocator(server.getServletPrefix()
                , discovery
                , zuulProperties
                , registration
                , redisTemplate);
    }

}
