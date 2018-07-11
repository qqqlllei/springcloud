/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */

package com.qqlei.cloud.gateway.config;

import com.qqlei.cloud.gateway.entity.GataWayRoute;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.discovery.DiscoveryClientRouteLocator;

import java.util.*;

public class DynamicRouteLocator extends DiscoveryClientRouteLocator {

    private Logger logger = LoggerFactory.getLogger(DynamicRouteLocator.class);

    private ZuulProperties properties;

    private GataWayClientProperties gataWayClientProperties;

    public DynamicRouteLocator(String servletPath, DiscoveryClient discovery, ZuulProperties properties,
                               ServiceInstance localServiceInstance,GataWayClientProperties gataWayClientProperties) {
        super(servletPath, discovery, properties, localServiceInstance);
        this.properties = properties;
        this.gataWayClientProperties= gataWayClientProperties;
    }


    @Override
    protected LinkedHashMap<String, ZuulProperties.ZuulRoute> locateRoutes() {
        LinkedHashMap<String, ZuulProperties.ZuulRoute> routesMap = new LinkedHashMap<>();
        //读取properties配置、eureka默认配置
        routesMap.putAll(super.locateRoutes());
        logger.debug("初始默认的路由配置完成");
        routesMap.putAll(locateRoutesFromDb());
        LinkedHashMap<String, ZuulProperties.ZuulRoute> values = new LinkedHashMap<>();
        for (Map.Entry<String, ZuulProperties.ZuulRoute> entry : routesMap.entrySet()) {
            String path = entry.getKey();
            if (!path.startsWith("/")) {
                path = "/" + path;
            }
            if (StringUtils.isNotBlank(this.properties.getPrefix())) {
                path = this.properties.getPrefix() + path;
                if (!path.startsWith("/")) {
                    path = "/" + path;
                }
            }
            values.put(path, entry.getValue());
        }
        return values;
    }

    private Map<String, ZuulProperties.ZuulRoute> locateRoutesFromDb() {
        Map<String, ZuulProperties.ZuulRoute> routes = new LinkedHashMap<>();

        List<GataWayRoute> results = Arrays.asList(gataWayClientProperties.getClients());
        for (GataWayRoute result : results) {
            if (StringUtils.isBlank(result.getPath()) && StringUtils.isBlank(result.getUrl())) {
                continue;
            }

            ZuulProperties.ZuulRoute zuulRoute = new ZuulProperties.ZuulRoute();
            zuulRoute.setId(result.getServiceId());
            zuulRoute.setPath(result.getPath());
            zuulRoute.setServiceId(result.getServiceId());
            zuulRoute.setRetryable(("0".equals(result.getRetryable()) ? Boolean.FALSE : Boolean.TRUE));
            zuulRoute.setStripPrefix(("0".equals(result.getStripPrefix()) ? Boolean.FALSE : Boolean.TRUE));
            zuulRoute.setUrl(result.getUrl());
            List<String> sensitiveHeadersList=null;
            if(StringUtils.isNotBlank(result.getSensitiveHeadersList())){
                sensitiveHeadersList = Arrays.asList(result.getSensitiveHeadersList().split(","));
            }

            if (sensitiveHeadersList != null) {
                Set<String> sensitiveHeaderSet = new HashSet<>();
                sensitiveHeadersList.forEach(sensitiveHeader -> sensitiveHeaderSet.add(sensitiveHeader));
                zuulRoute.setSensitiveHeaders(sensitiveHeaderSet);
                zuulRoute.setCustomSensitiveHeaders(true);
            }

            logger.debug("自定义的路由配置,path：{}，serviceId:{}", zuulRoute.getPath(), zuulRoute.getServiceId());
            routes.put(zuulRoute.getPath(), zuulRoute);
        }
        return routes;
    }
}
