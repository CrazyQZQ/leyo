package com.qq.gateway_demo.controller;

import com.qq.common.core.utils.SpringUtils;
import com.qq.common.core.web.domain.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
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
        Authentication bean = SpringUtils.getBean(Authentication.class);
        String principal = null;
        if (bean != null) {
            principal = (String)bean.getPrincipal();
        }
        return AjaxResult.success(principal);
    }
}
