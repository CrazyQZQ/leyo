package com.qq.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.qq.common.core.constant.AuthConstants;
import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.log.annotation.Log;
import com.qq.common.redis.service.RedisService;
import com.qq.common.system.pojo.SysUser;
import com.qq.common.system.pojo.SysUserRole;
import com.qq.common.system.utils.OauthUtils;
import com.qq.system.domain.LoginVO;
import com.qq.system.service.AuthService;
import com.qq.system.service.SysUserRoleService;
import com.qq.system.service.SysUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

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
    private final SysUserRoleService sysUserRoleService;

    @RequestMapping("login")
    @Log(title = "system", funcDesc = "跳转授权码登录页面")
    public String login() {
        return "login";
    }

    @Log(title = "system", funcDesc = "登录")
    @GetMapping("logout")
    @ResponseBody
    public AjaxResult logout() {
        SysUser currentUser = OauthUtils.getCurrentUser();
        // 将当前登录用户的token加入redis黑名单，过期时间与token过期时间一致
        redisService.setCacheObject(AuthConstants.JTI_KEY_PREFIX + currentUser.getJti(), "", currentUser.getExpireIn(), TimeUnit.SECONDS);
        return AjaxResult.success("退出成功");
    }

    @Log(title = "system", funcDesc = "登录")
    @PostMapping("client_login")
    @ResponseBody
    public AjaxResult clientLogin(@Valid @RequestBody LoginVO loginVO, BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if (allErrors.size() > 0) {
            String errorMsg = allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));
            return AjaxResult.error(errorMsg);
        }
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

    @Log(title = "system", funcDesc = "注册")
    @PostMapping("register")
    @ResponseBody
    public AjaxResult register(@Valid @RequestBody SysUser user, BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        if (allErrors.size() > 0) {
            String errorMsg = allErrors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(","));
            return AjaxResult.error(errorMsg);
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        user.setCreateBy(user.getUserName());
        user.setCreateTime(new Date());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        sysUserService.insert(user);
        SysUserRole userRole = new SysUserRole();
        userRole.setRoleId(2l);
        userRole.setUserId(user.getUserId());
        sysUserRoleService.save(userRole);
        return AjaxResult.success();
    }
}
