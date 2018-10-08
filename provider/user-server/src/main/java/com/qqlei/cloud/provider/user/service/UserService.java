package com.qqlei.cloud.provider.user.service;

import com.alibaba.fastjson.JSONObject;
import com.qqlei.cloud.provider.user.dao.UserMapper;
import com.qqlei.cloud.provider.user.domain.User;
import com.reliable.message.model.domain.ClientMessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Random;

/**
 * Created by Administrator on 2018/8/11 0011.
 */
@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserMessageService userMessageService;


    @Transactional
    public int saveUser(User user){
        ClientMessageData mqMessageData = new ClientMessageData();
        mqMessageData.setId(Long.valueOf(new Random().nextInt()));
        mqMessageData.setMessageTopic("saveUser");
        mqMessageData.setVersion(0);
        JSONObject jsonObject = (JSONObject) JSONObject.toJSON(user);
        mqMessageData.setMessageBody(jsonObject.toJSONString());
        return userMessageService.saveUserAndSendMessage(mqMessageData,user);
    }



}
