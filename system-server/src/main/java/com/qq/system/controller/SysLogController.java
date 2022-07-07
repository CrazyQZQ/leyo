package com.qq.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qq.common.core.constant.AuthConstants;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.redis.service.RedisService;
import com.qq.common.system.pojo.SysUser;
import com.qq.common.system.utils.OauthUtils;
import com.qq.system.domain.LoginVO;
import com.qq.system.service.AuthService;
import com.qq.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

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
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SysLogController {

    private final RedisService redisService;
    private final AuthService authService;
    private final SysUserService sysUserService;

    @RequestMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("logout")
    @ResponseBody
    public AjaxResult logout() {
        SysUser currentUser = OauthUtils.getCurrentUser();
        // 将当前登录用户的token加入redis黑名单，过期时间与token过期时间一致
        redisService.setCacheObject(AuthConstants.JTI_KEY_PREFIX + currentUser.getJti(), "", currentUser.getExpireIn(), TimeUnit.SECONDS);
        return AjaxResult.success("退出成功");
    }

    @PostMapping("client_login")
    @ResponseBody
    public AjaxResult clientLogin(@RequestBody LoginVO loginVO) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("username", loginVO.getUsername());
        body.add("password", loginVO.getPassword());
        body.add("grant_type", "password");
        body.add("client_id", "admin");
        body.add("client_secret", "123");
        Object token = authService.postAccessToken(body);
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("user", sysUserService.getOne(new QueryWrapper<SysUser>().eq("user_name", loginVO.getUsername())));
        return AjaxResult.success(result);
    }
}
