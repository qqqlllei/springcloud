package com.qqlei.cloud.auth.security;

import com.qqlei.cloud.auth.security.integration.IntegrationAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Created by 李雷 on 2018/7/12.
 */
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER - 1)
@Configuration
@EnableWebSecurity
public class AuthSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private PcAuthenticationSuccessHandler pcAuthenticationSuccessHandler;

    @Autowired
    private IntegrationAuthenticationFilter integrationAuthenticationFilter;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.addFilterBefore(integrationAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry =
                http.formLogin()
                        .loginProcessingUrl("/authentication/form")
                        .successHandler(pcAuthenticationSuccessHandler)
                        .and()
                        .authorizeRequests();
        registry.antMatchers("/authentication/**").permitAll();
        registry.anyRequest().authenticated()
                .and()
                .csrf().disable();
    }
}
