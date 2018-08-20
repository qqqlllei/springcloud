package com.qqlei.cloud.provider.user.service;

import com.qqlei.cloud.provider.user.dao.UserMapper;
import com.qqlei.cloud.provider.user.domain.User;
import com.reliable.message.client.annotation.MqProducerStore;
import com.reliable.message.model.domain.MqMessageData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2018/8/11 0011.
 */
@Service
public class UserMessageService {

    @Autowired
    private UserMapper userMapper;

    @MqProducerStore
    public int saveUserAndSendMessage(MqMessageData mqMessageData , User user){
        return userMapper.saveUser(user);
    }
}
