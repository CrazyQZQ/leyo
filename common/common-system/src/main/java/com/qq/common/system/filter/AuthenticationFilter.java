package com.qq.common.system.filter;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.qq.common.core.constant.AuthConstants;
import com.qq.common.core.constant.TokenConstants;
import com.qq.common.core.utils.StringUtils;
import com.qq.common.core.utils.sign.Base64;
import com.qq.common.system.pojo.SysUser;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author 公众号：码猿技术专栏
 * 微服务过滤器，解密网关传递的用户信息，将其放入request中，便于后期业务方法直接获取用户信息
 */
@Deprecated
// @Component
public class AuthenticationFilter extends OncePerRequestFilter {
    /**
     * 具体方法主要分为两步
     * 1. 解密网关传递的信息
     * 2. 将解密之后的信息封装放入到request中
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头中的加密的用户信息
        String token = request.getHeader(TokenConstants.TOKEN_NAME);
        if (StringUtils.isNotBlank(token)) {
            //解密
            String json = new String(Base64.decode(token));
            JSONObject jsonObject = JSON.parseObject(json);
            //获取用户身份信息、权限信息
            String principal = jsonObject.getString(TokenConstants.PRINCIPAL_NAME);
            String userId = jsonObject.getString(TokenConstants.USER_ID);
            String jti = jsonObject.getString(TokenConstants.JTI);
            Long expireIn = jsonObject.getLong(TokenConstants.EXPR);
            JSONArray tempJsonArray = jsonObject.getJSONArray(TokenConstants.AUTHORITIES_NAME);
            //权限
            String[] authorities = tempJsonArray.toArray(new String[0]);
            //放入LoginVal
            SysUser loginUser = new SysUser();
            loginUser.setUserId(Long.valueOf(StringUtils.nvl(userId, "-1")));
            loginUser.setUserName(principal);
            loginUser.setAuthorities(authorities);
            loginUser.setJti(jti);
            loginUser.setExpireIn(expireIn);
            //放入request的attribute中
            request.setAttribute(AuthConstants.LOGIN_VAL_ATTRIBUTE, loginUser);
        }
        filterChain.doFilter(request, response);
    }
}