package com.qq.system.controller;

import com.qq.common.core.constant.AuthConstants;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.redis.service.RedisService;
import com.qq.common.system.pojo.SysUser;
import com.qq.common.system.utils.OauthUtils;
import com.qq.system.domain.LoginVO;
import com.qq.system.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/20
 **/
@Controller
@RequestMapping("system")
@AllArgsConstructor
public class SysLogController {

    private final RedisService redisService;
    private final AuthService authService;

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

    @PostMapping("client_login")
    @ResponseBody
    public AjaxResult clientLogin(@RequestBody LoginVO loginVO) {
        MultiValueMap<String,String> body = new LinkedMultiValueMap<>();
        body.add("username",loginVO.getUsername());
        body.add("password",loginVO.getPassword());
        body.add("grant_type","password");
        body.add("client_id","admin");
        body.add("client_secret","123");
        return AjaxResult.success(authService.postAccessToken(body));
    }
}
