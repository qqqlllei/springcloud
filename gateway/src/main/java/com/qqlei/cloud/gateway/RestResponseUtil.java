package com.qqlei.cloud.gateway;

import com.alibaba.fastjson.JSONObject;
import com.netflix.zuul.context.RequestContext;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 李雷 on 2018/7/13.
 */
public class RestResponseUtil {

    private static String NO_TOKEN_RESULT_CODE="1111";
    private static String NO_TOKEN_RESULT_MSG="未登录";
    private static String CONTENT_TYPE="application/json;charset=UTF-8";

    public static void noTokenResponse(RequestContext requestContext){
        requestContext.setResponseStatusCode(200);
        requestContext.setSendZuulResponse(false);
        requestContext.getResponse().setContentType(CONTENT_TYPE);
        Map<String,String> result = new HashMap<>();
        result.put("resultCode",NO_TOKEN_RESULT_CODE);
        result.put("resultMsg",NO_TOKEN_RESULT_MSG);
        requestContext.setResponseBody(JSONObject.toJSONString(ResponseEntity.ok(result)));
    }
}
