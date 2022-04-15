package com.qq.common.system.utils;

import com.qq.common.core.constant.AuthConstants;
import com.qq.common.core.utils.ServletUtils;
import com.qq.common.system.pojo.SysUser;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/15
 **/
public class OauthUtils {
    /**
     * 获取当前请求登录用户的详细信息
     */
    public static SysUser getCurrentUser(){
        HttpServletRequest request = ServletUtils.getRequest();
        return (SysUser) request.getAttribute(AuthConstants.LOGIN_VAL_ATTRIBUTE);
    }
}
