package com.qq.nacos_provider.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/2
 **/
@RestController
@RequestMapping("provide")
public class TestController {

    @GetMapping("/echo/{string}")
    public String echo(@PathVariable String string) {
        return "Hello Nacos provide :" + string;
    }
}
