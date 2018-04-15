package com.qqlei.cloud.auth.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Administrator on 2018/4/15 0015.
 */
@ConfigurationProperties(prefix = "qqlei.security")
public class SecurityProperties {

    /**
     * 浏览器环境配置
     */
    private BrowserProperties browser = new BrowserProperties();

    /**
     * OAuth2认证服务器配置
     */
    private OAuth2Properties oauth2 = new OAuth2Properties();

    /**
     * Gets browser.
     *
     * @return the browser
     */
    public BrowserProperties getBrowser() {
        return browser;
    }

    /**
     * Sets browser.
     *
     * @param browser the browser
     */
    public void setBrowser(BrowserProperties browser) {
        this.browser = browser;
    }

    /**
     * Gets oauth 2.
     *
     * @return the oauth 2
     */
    public OAuth2Properties getOauth2() {
        return oauth2;
    }

    /**
     * Sets oauth 2.
     *
     * @param oauth2 the oauth 2
     */
    public void setOauth2(OAuth2Properties oauth2) {
        this.oauth2 = oauth2;
    }
}
