package com.qq.oauth2.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/12
 **/
@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("get")
    public String get(){
        return "oauth test";
    }
}
