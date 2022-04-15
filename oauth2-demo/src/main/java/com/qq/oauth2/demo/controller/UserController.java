package com.qq.oauth2.demo.controller;

import com.qq.common.core.web.domain.AjaxResult;
import com.qq.common.system.utils.OauthUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/14
 **/
@RestController
@RequestMapping("oauth/user")
@PreAuthorize("hasAnyRole('admin','user')")
public class UserController {
    @GetMapping("loginUser")
    public AjaxResult get() {
        return AjaxResult.success("", OauthUtils.getCurrentUser());
    }
}
