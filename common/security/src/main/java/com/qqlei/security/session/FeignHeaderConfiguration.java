package com.qqlei.security.session;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 李雷 on 2018/7/19.
 */
@Configuration
public class FeignHeaderConfiguration {

    @Bean
    public FeginHearderRequestInterceptor basicAuthRequestInterceptor() {
        return new FeginHearderRequestInterceptor();
    }
}
