package com.qqlei.cloud.provider.book.message;

import com.alibaba.fastjson.JSONObject;
import com.qqlei.cloud.provider.book.service.MessageService;
import com.reliable.message.model.domain.ClientMessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Created by 李雷 on 2018/10/8.
 */
@Component
public class KafkaMessage {

    @Value("${spring.application.name}")
    private String appName;

    private static final String saveUserTopic="saveUser-book-server";


    @Autowired
    private MessageService messageService;

    @KafkaListener(topics = {saveUserTopic})
    public void saveUser(String message){

        ClientMessageData clientMessageData = JSONObject.parseObject(message, ClientMessageData.class);
        messageService.consumerMessage(clientMessageData);

    }

}
