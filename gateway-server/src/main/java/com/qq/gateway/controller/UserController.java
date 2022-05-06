package com.qq.gateway.controller;

import com.qq.common.core.web.domain.AjaxResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/14
 **/
@RestController
@RequestMapping("user")
@Slf4j
public class UserController {

    @GetMapping("userInfo")
    public AjaxResult userInfo(Authentication authentication){
        String principal = null;
        if (authentication != null) {
            principal = (String)authentication.getPrincipal();
        }
        return AjaxResult.success(principal);
    }

    @GetMapping("adminInfo")
    public AjaxResult adminInfo(){
        return AjaxResult.success("admin info");
    }
}