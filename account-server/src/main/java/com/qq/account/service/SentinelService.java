package com.qq.account.service;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Author QinQiang
 * @Date 2022/4/2
 **/
@Service
public class SentinelService {

    @SentinelResource(value = "sayHello")
    public String sayHello(String name) {
        return "Hello, " + name;
    }
}
