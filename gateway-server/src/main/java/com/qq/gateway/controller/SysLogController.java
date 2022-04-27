package com.qq.gateway.controller;

import com.qq.common.core.constant.AuthConstants;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.system.pojo.SysUser;
import com.qq.common.system.utils.OauthUtils;
import com.qq.common_redis.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/20
 **/
@Controller
public class SysLogController {

    @Autowired
    private RedisService redisService;

    @RequestMapping("login")
    public String login() {
        return "login";
    }

    @PostMapping("logout")
    @ResponseBody
    public AjaxResult logout() {
        SysUser currentUser = OauthUtils.getCurrentUser();
        // 将当前登录用户的token加入redis黑名单，过期时间与token过期时间一致
        redisService.setCacheObject(AuthConstants.JTI_KEY_PREFIX+currentUser.getJti(),"",currentUser.getExpireIn(), TimeUnit.SECONDS);
        return AjaxResult.success("退出成功");
    }
}
