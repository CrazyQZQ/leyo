package com.qq.oauth2.demo.controller;

import com.qq.common.core.constant.AuthConstants;
import com.qq.common.core.constant.TokenConstants;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.system.pojo.SysUser;
import com.qq.common.system.utils.OauthUtils;
import com.qq.common_redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/18
 **/
@RestController
@RequestMapping("oauth")
public class LogController {

    @Autowired
    private RedisService redisService;

    @PostMapping("logout")
    public AjaxResult logout() {
        SysUser currentUser = OauthUtils.getCurrentUser();
        // 将当前登录用户的token加入redis黑名单，过期时间与token过期时间一致
        redisService.setCacheObject(AuthConstants.JTI_KEY_PREFIX+currentUser.getJti(),"",currentUser.getExpireIn(), TimeUnit.SECONDS);
        return AjaxResult.success("退出成功");
    }
}
