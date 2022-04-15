package com.qq.oauth2.resouce.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/14
 **/
@RestController
public class HelloController {

    @GetMapping("hello")
    public String hello(){
        return "hello";
    }

    @GetMapping("admin")
    @PreAuthorize("hasRole('ROLE_admin')")
    public String admin(){
        return "admin";
    }
}
