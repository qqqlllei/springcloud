package com.qqlei.cloud.provider.user.config;

import com.reliable.message.client.annotation.MqConsumerStore;
import com.reliable.message.client.service.MqMessageService;
import com.reliable.message.client.service.impl.MqMessageServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2018/8/11 0011.
 */
@Component
public class ReliableMessageConfig {

    @Bean
    public MqMessageService mqMessageService(){
        return new MqMessageServiceImpl();
    }
}
