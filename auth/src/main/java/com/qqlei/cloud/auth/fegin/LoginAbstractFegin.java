package com.qqlei.cloud.auth.fegin;

import com.qqlei.cloud.auth.security.vo.SysUserAuthentication;

/**
 * Created by 李雷 on 2018/8/10.
 */
public interface LoginAbstractFegin {

    SysUserAuthentication findUserById(String id);

    SysUserAuthentication findUserByPhone(String phone);
}
