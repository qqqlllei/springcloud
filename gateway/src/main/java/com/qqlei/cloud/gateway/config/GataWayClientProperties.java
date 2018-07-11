package com.qqlei.cloud.gateway.config;

import com.qqlei.cloud.gateway.entity.GataWayRoute;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/7/11 0011.
 */
@ConfigurationProperties(prefix = "zuul.client")
@Component
public class GataWayClientProperties {

    private GataWayRoute[] clients = {};

    public GataWayRoute[] getClients() {
        return clients;
    }

    public void setClients(GataWayRoute[] clients) {
        this.clients = clients;
    }
}
