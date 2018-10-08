package com.qqlei.cloud.provider.book;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.kafka.annotation.EnableKafka;

/**
 * Created by Administrator on 2018/4/14 0014.
 */

@SpringBootApplication
@EnableDiscoveryClient
@EnableKafka
@MapperScan(basePackages = {"com.reliable.message.client.dao"})
@EnableFeignClients(basePackages = {"com.reliable.message.client.feign"})
public class BookApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookApplication.class,args);
    }
}
