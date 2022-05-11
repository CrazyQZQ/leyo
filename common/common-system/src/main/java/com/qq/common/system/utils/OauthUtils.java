package com.qq.common.system.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qq.common.core.constant.TokenConstants;
import com.qq.common.core.utils.ServletUtils;
import com.qq.common.core.utils.StringUtils;
import com.qq.common.core.utils.sign.Base64;
import com.qq.common.system.pojo.SysUser;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/15
 **/
@Slf4j
public class OauthUtils {
    /**
     * 获取当前请求登录用户的详细信息,GlobalAuthenticationFilter将用户信息放入request中
     * gateway模块无法使用，因为gateway模块使用webflux，而webflux没有request
     */
    public static SysUser getCurrentUser() {
        HttpServletRequest request = ServletUtils.getRequest();
        String token = request.getHeader(TokenConstants.TOKEN_NAME);
        if (StringUtils.isNotBlank(token)) {
            // 解密
            String json = new String(Base64.decode(token));
            JSONObject jsonObject = JSON.parseObject(json);
            // 获取用户身份信息、权限信息
            String principal = jsonObject.getString(TokenConstants.PRINCIPAL_NAME);
            String userId = jsonObject.getString(TokenConstants.USER_ID);
            String jti = jsonObject.getString(TokenConstants.JTI);
            Long expireIn = jsonObject.getLong(TokenConstants.EXPR);
            JSONArray tempJsonArray = jsonObject.getJSONArray(TokenConstants.AUTHORITIES_NAME);
            // 权限
            String[] authorities = tempJsonArray.toArray(new String[0]);
            // 放入LoginVal,本质是userDetailService.loadUserByUsername(username) 返回的SecurityUser
            SysUser loginUser = new SysUser();
            loginUser.setUserId(Long.valueOf(StringUtils.nvl(userId, "-1")));
            loginUser.setUserName(principal);
            loginUser.setAuthorities(authorities);
            loginUser.setJti(jti);
            loginUser.setExpireIn(expireIn);
            log.info("获取当前登录用户信息：{}", JSON.toJSONString(loginUser));
            return loginUser;
        } else {
            log.info("获取当前登录用户信息：token为空");
            return null;
        }
    }

    /**
     * 获取当前请求登录用户名
     * @return
     */
    public static String getCurrentUserName() {
        SysUser user = getCurrentUser();
        if (user != null) {
            return user.getUserName();
        }
        return "admin";
    }
}
