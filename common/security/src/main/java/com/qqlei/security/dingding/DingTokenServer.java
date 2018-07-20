package com.qqlei.security.dingding;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * Created by 李雷 on 2018/7/20.
 */
public class DingTokenServer {

    public static final String GET_TOKEN_SUCCESS="ok";

    public static final int TOKEN_TIME_OUT=110;


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private DingFegin dingFegin;

    @Autowired
    private DingDingProperties dingDingProperties;

    public String getSnsToken(){
        String accessToken = stringRedisTemplate.opsForValue().get("ding_ding_sns_token_"+dingDingProperties.getAppid());
        if(accessToken ==null){
            JSONObject result = dingFegin.getSnsToken(dingDingProperties.getAppid(),dingDingProperties.getAppsecret());
            if(result.containsKey("errmsg") && GET_TOKEN_SUCCESS.equals(result.getString("errmsg"))){
                accessToken= result.getString("access_token");
                stringRedisTemplate.opsForValue().set("ding_ding_sns_token_"+dingDingProperties.getAppid(),accessToken,TOKEN_TIME_OUT, TimeUnit.MINUTES);
            }
        }

        return accessToken;
    }


    public JSONObject getSnsPersistentCode(String authCode){
        String accessToken = getSnsToken();
        return dingFegin.getSnsPersistentCode(accessToken,authCode);
    }


    public String getComToken(){
        String accessToken = stringRedisTemplate.opsForValue().get("ding_ding_com_token_"+dingDingProperties.getCorpid());
        if(accessToken ==null){
            JSONObject result = dingFegin.getComToken(dingDingProperties.getCorpid(),dingDingProperties.getCorpsecret());
            if(result.containsKey("errmsg") && GET_TOKEN_SUCCESS.equals(result.getString("errmsg"))){
                accessToken= result.getString("access_token");
                stringRedisTemplate.opsForValue().set("ding_ding_com_token_"+dingDingProperties.getCorpid(),accessToken,TOKEN_TIME_OUT, TimeUnit.MINUTES);
            }
        }

        return accessToken;
    }

    public String getUserIdByUnionid(String unionid){
        String accessToken = getComToken();
        JSONObject result = dingFegin.getUseridByUnionid(accessToken,unionid);
        if(result.containsKey("errmsg") && GET_TOKEN_SUCCESS.equals(result.getString("errmsg"))){
            return result.getString("userid");

        }
        return null;
    }

    public JSONObject getUserInfoByUserId(String userId){
        String accessToken = getComToken();
        return dingFegin.getUserInfoByUserId(accessToken,userId);
    }



    public String getUserPhoneByAuthCode(String authCode){
       JSONObject persistentCode =  getSnsPersistentCode(authCode);
       String unionid =  persistentCode.getString("unionid");
       String userId =  getUserIdByUnionid(unionid);
       JSONObject userInfo =  getUserInfoByUserId(userId);
       return userInfo.getString("mobile");
    }

}
