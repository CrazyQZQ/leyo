package com.qq.oauth2.server.controller;

import com.qq.common.core.constant.AuthConstants;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.redis.service.RedisService;
import com.qq.common.system.pojo.SysUser;
import com.qq.common.system.utils.OauthUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/18
 **/
@Controller
@RequestMapping("oauth")
@Slf4j
public class LogController {

    @Autowired
    private RedisService redisService;

    @Autowired
    AuthorizationServerTokenServices authorizationServerTokenServices;


    @RequestMapping("login")
    public String login() {
        return "login";
    }

    @PostMapping("logout")
    @ResponseBody
    public AjaxResult logout() {
        SysUser currentUser = OauthUtils.getCurrentUser();
        // 将当前登录用户的token加入redis黑名单，过期时间与token过期时间一致
        redisService.setCacheObject(AuthConstants.JTI_KEY_PREFIX + currentUser.getJti(), "", currentUser.getExpireIn(), TimeUnit.SECONDS);
        return AjaxResult.success("退出成功");
    }
}
