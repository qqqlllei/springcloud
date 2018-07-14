package com.qqlei.security.session;

/**
 * Created by Administrator on 2018/7/14 0014.
 */
public class UserSessionContext {

    private static ThreadLocal<SysUserAuthentication> userSessionContextHolder = new ThreadLocal<SysUserAuthentication>();

    public static void set(SysUserAuthentication sysUserAuthentication){
        userSessionContextHolder.set(sysUserAuthentication);
    }

    public static SysUserAuthentication get(){
        return userSessionContextHolder.get();
    }

    public static void clear(){
        userSessionContextHolder.remove();
    }
}
