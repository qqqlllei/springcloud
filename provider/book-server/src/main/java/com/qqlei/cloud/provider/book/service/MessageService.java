package com.qqlei.cloud.provider.book.service;

import com.alibaba.fastjson.JSONObject;
import com.reliable.message.client.annotation.MqConsumerStore;
import com.reliable.message.model.domain.ClientMessageData;
import org.springframework.stereotype.Service;

/**
 * Created by 李雷 on 2018/10/8.
 */
@Service
public class MessageService {

    @MqConsumerStore
    public String consumerMessage(ClientMessageData clientMessageData) {

        System.out.println(JSONObject.toJSONString(clientMessageData));
        return "CONSUME_SUCCESS";
    }
}
