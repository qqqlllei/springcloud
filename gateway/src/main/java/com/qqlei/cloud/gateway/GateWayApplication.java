package com.qqlei.cloud.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Created by Administrator on 2018/4/14 0014.
 */
@SpringBootApplication
@EnableZuulProxy
@EnableDiscoveryClient
@EnableFeignClients
public class GateWayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GateWayApplication.class,args);
    }

    @Bean
    public JwtTokenStore jwtTokenStore(){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey("jwtSigningKey");
        return new JwtTokenStore(converter);
    }
}
