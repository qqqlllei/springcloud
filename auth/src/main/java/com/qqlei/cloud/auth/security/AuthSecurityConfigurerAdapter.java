package com.qqlei.cloud.auth.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * Created by 李雷 on 2018/7/12.
 */
@Configuration
@EnableWebSecurity
public class AuthSecurityConfigurerAdapter extends WebSecurityConfigurerAdapter {

    @Autowired
    private PcAuthenticationSuccessHandler pcAuthenticationSuccessHandler;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry registry =
                http.formLogin().loginPage("/authentication/require")
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
